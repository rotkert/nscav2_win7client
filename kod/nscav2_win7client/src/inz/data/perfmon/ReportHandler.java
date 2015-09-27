package inz.data.perfmon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import inz.commons.Config;
import inz.data.collectors.CriticalEvent;
import mkaminski.inz.errorHandling.ErrorMessages;

public class ReportHandler
{
	public void getNotSentReports(BlockingQueue<PerfmonResult> blockingQueue)
	{
		File appDir = new File(Config.reportDirectory);
		for (String reportDirName : appDir.list())
		{
			String reportDir = appDir + "\\" + reportDirName;
			File report = new File(reportDir);
			
			if(report.isDirectory() == false)
			{
				continue;
			}
			
			PerfmonResult perfmonResult = getReportExecutionDetails(reportDir + Config.newReportFileName);
			
			if(perfmonResult != null)
			{
				try
				{
					blockingQueue.put(perfmonResult);
				} 
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				//TODO
			}
		}
	}
	
	//TODO
	//sciezka do pobierania raportu jest ustawiona na sztywno
	public String getReportText(String reportLocation)
	{
		System.out.println(System.getenv("APPDATA"));
		String reportPath = "C:\\Users\\Miko\\Desktop\\inz_raporty\\MIKO-KOMPUTER_20150530-000001\\raport.xml";
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		try
		{
			br = new BufferedReader(new FileReader(new File(reportPath)));

			String line = "";
			while ((line = br.readLine()) != null)
			{
				sb.append(line.trim());
			}

			br.close();
		} catch (FileNotFoundException e)
		{
			// TODO
			System.out.println(ErrorMessages.REPORT_NOT_FOUND);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	void setReportExecutionDetails(String reportLocation, CriticalEvent event, long timestamp) 
	{
		File input = new File(reportLocation + Config.reportFileName);
		Document doc = null;
		try
		{
			doc = Jsoup.parse(input, "UTF-16");
			Element table = (Element) doc.getElementById("c_1").getElementsByClass("info").get(0).parentNode().parentNode();
			table.append("<tr><td class='h4'>Przyczyna: </td><td class='info' id='CriticalSituationType'>" + event + "</td></tr><tr><td class='h4'>Stempel czasu: </td><td class='info' id='timestamp'>" + timestamp + "</td></tr>");
			PrintWriter writer = new PrintWriter(reportLocation + "\\new_report.html", "UTF-16");
			writer.print(doc.toString());
			writer.close();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private PerfmonResult getReportExecutionDetails(String reportLocation)
	{
		File reportFile = new File(reportLocation);
		Document doc = null;
		PerfmonResult perfmonResult = null;
		
		try
		{
			doc = Jsoup.parse(reportFile, "UTF-16");
			String eventString = doc.getElementById("CriticalSituationType").html();
			String timestampString = doc.getElementById("timestamp").html();
			
			CriticalEvent event = CriticalEvent.valueOf(eventString);
			Long timestamp = Long.parseLong(timestampString);
			
			perfmonResult = new PerfmonResult(reportLocation, timestamp, event);
		} 
		catch (IOException e)
		{
			//TODO
			System.out.println(e.getMessage());
		}
		
		return perfmonResult;
	}
}
