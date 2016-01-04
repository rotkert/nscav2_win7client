package inz.data.perfmon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

import inz.commons.Logger;
import inz.commons.Severity;

public class PerfmonHandler implements Runnable
{
	private static final String PS_SCRIPT = "\\extensions\\runPerfmon.ps1";
	private BlockingQueue<PerfmonResult> blockingQueue;
	private String counterCategory;
	private ReportHandler reportHandler;

	public PerfmonHandler(BlockingQueue<PerfmonResult> blockingQueue, String counterCategory)
	{
		this.blockingQueue = blockingQueue;
		this.counterCategory = counterCategory;
		this.reportHandler = new ReportHandler();
	}
	
	public void run()
	{
		long timestamp = new Date().getTime() / 1000;
		String reportLocation = runPerfmon();
		
		try 
		{
			if (reportLocation != null)
			{
				String reportName = getReportName(reportLocation);
				reportHandler.setReportExecutionDetails(reportLocation, counterCategory, timestamp);
				blockingQueue.put(new PerfmonResult(reportLocation, timestamp, counterCategory, reportName));
			}
		}
		catch (InterruptedException e)
		{
			Logger.getInstatnce().log(Severity.ERROR, e.getMessage());
		}	
	}
	
	private String runPerfmon()
	{
		String projectDirectory = System.getProperty("user.dir");
		String command = "powershell.exe " + projectDirectory + PS_SCRIPT;
		Process powerShellProcess = null;
		String reportLocation = null;
		String line = null;
		StringBuilder builder = null;
		
		BufferedReader stdout = null;
		BufferedReader stderr = null;
		
		try
		{
			powerShellProcess = Runtime.getRuntime().exec(command);
			powerShellProcess.getOutputStream().close();
	
			stdout = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));
			builder = new StringBuilder();
			while((line = stdout.readLine()) != null)
			{
				builder.append(line);
			}
			reportLocation = builder.toString();
			stdout.close();
			
			String errorText = null;
			stderr = new BufferedReader(new InputStreamReader(powerShellProcess.getErrorStream()));
			builder = new StringBuilder();
			while((line = stderr.readLine()) != null)
			{
				builder.append(line);
			}
			errorText = builder.toString();
			
			if (errorText != null && !errorText.equals(""))
			{
				IOException e = new IOException(errorText);
				throw e;
			}
			
			stderr.close();
		} 
		catch (IOException e)
		{
			Logger.getInstatnce().log(Severity.ERROR, e.getMessage());
			reportLocation = null;
		}
		
		return reportLocation;
	}
	
	private String getReportName(String reportLocation)
	{
		String reportName = "";
		int lastSlash = 0;
		for (int i = 0; i < reportLocation.length(); i++)
		{
			char c = reportLocation.charAt(i);
			if (c == '\\')
			{
				lastSlash = i;
			}
		}
		
		for (int i = lastSlash + 1; i < reportLocation.length(); i++)
		{
			char c = reportLocation.charAt(i);
			reportName += c;
		}
		
		return reportName;
	}
}
