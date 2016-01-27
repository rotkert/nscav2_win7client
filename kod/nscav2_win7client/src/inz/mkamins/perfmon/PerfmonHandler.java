package inz.mkamins.perfmon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;

import inz.mkamins.commons.ConfigProvider;
import inz.mkamins.commons.Logger;
import inz.mkamins.commons.Severity;

public class PerfmonHandler implements Runnable
{
	private final String psScript;
	private BlockingQueue<PerfmonResult> blockingQueue;
	private String counterCategory;
	private ReportHandler reportHandler;
	private Lock lock;

	public PerfmonHandler(BlockingQueue<PerfmonResult> blockingQueue, Lock lock, String counterCategory)
	{
		this.psScript = ConfigProvider.getInstance().getPsScriptPath();
		this.blockingQueue = blockingQueue;
		this.counterCategory = counterCategory;
		this.reportHandler = new ReportHandler();
		this.lock = lock;
	}
	
	public void run()
	{
		if(lock.tryLock())
		{
			long timestamp = new Date().getTime() / 1000;
			
			try 
			{
				String reportLocation = runPerfmon();
				
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
			} catch (IOException e)
			{
				Logger.getInstatnce().log(Severity.ERROR, "Error while running diagnostics: " +  e.getMessage());
			}
			finally
			{
				lock.unlock();
			}
		}
		else
		{
			Logger.getInstatnce().log(Severity.DEBUG, "Diagnostics is already running");
		}
	}
	
	private String runPerfmon() throws IOException
	{
		int diagDuration = ConfigProvider.getInstance().getDiagnosticsDuration();
		String diagName = ConfigProvider.getInstance().getDiagnosticsName();
		
		String command = "powershell.exe " + psScript + " -diagDuration " + diagDuration + " -diagName " + diagName;
		Process powerShellProcess = null;
		String reportLocation = null;
		String line = null;
		StringBuilder builder = null;
		
		BufferedReader stdout = null;
		BufferedReader stderr = null;
	
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
		
		stderr.close();
		
		if (errorText != null && !errorText.equals(""))
		{
			throw new IOException(errorText);
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
