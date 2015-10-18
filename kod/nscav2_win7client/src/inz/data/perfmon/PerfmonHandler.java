package inz.data.perfmon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import inz.data.collectors.CriticalEvent;

public class PerfmonHandler
{
	private static final String PS_SCRIPT = "\\extensions\\runPerfmon.ps1";
	private ReportHandler reportHandler;

	public PerfmonHandler()
	{
		reportHandler = new ReportHandler();
	}
	
	public PerfmonResult getReport(CriticalEvent event)
	{
		long timestamp = new Date().getTime();
		String reportLocation = runPerfmon();
		String reportName = getReportName(reportLocation);
		
		reportHandler.setReportExecutionDetails(reportLocation, event, timestamp);
		return new PerfmonResult(reportLocation, timestamp, event, reportName);
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
			// TODO zaloguj wyst¹pienie b³êdu
			System.out.println(e.getMessage());
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
