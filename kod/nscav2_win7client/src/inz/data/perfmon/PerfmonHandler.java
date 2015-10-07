package inz.data.perfmon;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;

import javax.swing.text.AbstractDocument.LeafElement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
		
		BufferedReader stdout = null;
		BufferedReader stderr = null;
		
		try
		{
			powerShellProcess = Runtime.getRuntime().exec(command);
			powerShellProcess.getOutputStream().close();
	
			stdout = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));
			reportLocation = stdout.readLine();
			stdout.close();
			
			String errorText = null;
			stderr = new BufferedReader(new InputStreamReader(powerShellProcess.getErrorStream()));
			errorText = stderr.readLine();
			
			if (errorText != null)
			{
				IOException e = new IOException(errorText);
				throw e;
			}
			
			stderr.close();
		} 
		catch (IOException e)
		{
			// TODO zaloguj wyst�pienie b��du
			System.out.println(e.getMessage());
			reportLocation = null;
		}
		
		return reportLocation;
	}
	
	private String getReportName(String reportLocation)
	{
		String[] locationArray = reportLocation.split("\\");
		String reportName = locationArray[locationArray.length - 1];
		return reportName;
	}
}
