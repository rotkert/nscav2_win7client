package inz.data.perfmon;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import mkaminski.inz.dataCollector.CriticalEvent;

public class PerfmonHandler
{
	private static final String PS_SCRIPT = "\\extensions\\runPerfmon.ps1";

	public PerfmonResult getReport(CriticalEvent event)
	{
		long timestamp = new Date().getTime();
		String reportLocation = runPerfmon();
		setReportExecutionDetails(reportLocation, event, timestamp);
		
		return new PerfmonResult(reportLocation, timestamp, event);
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
			// TODO zaloguj wyst¹pienie b³êdu
			System.out.println(e.getMessage());
			reportLocation = null;
		}
		
		return reportLocation;
	}
	
	private void setReportExecutionDetails(String reportDir, CriticalEvent event, long timestamp) 
	{
		File input = new File(reportDir + "\\report.html");
		Document doc = null;
		try
		{
			doc = Jsoup.parse(input, "UTF-16");
			Element table = (Element) doc.getElementById("c_1").getElementsByClass("info").get(0).parentNode().parentNode();
			table.append("<tr><td class='h4'>Przyczyna: </td><td class='info' id='CriticalSituationType'>" + event + "</td></tr><tr><td class='h4'>Stempel czasu: </td><td class='info' id='timestamp'>" + timestamp + "</td></tr>");
			PrintWriter writer = new PrintWriter(reportDir + "\\new_report.html", "UTF-16");
			writer.print(doc.toString());
			writer.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
