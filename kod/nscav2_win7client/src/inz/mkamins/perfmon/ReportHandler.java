package inz.mkamins.perfmon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import inz.mkamins.commons.ConfigProvider;
import inz.mkamins.commons.Logger;
import inz.mkamins.commons.Severity;

public class ReportHandler
{
	private final HashMap<String, String> polishDiacriticsDict;
	
	public ReportHandler()
	{
		polishDiacriticsDict = new HashMap<>();
		polishDiacriticsDict.put("\u0105", "a");
		polishDiacriticsDict.put("\u0107", "c");
		polishDiacriticsDict.put("\u0119", "e");
		polishDiacriticsDict.put("\u0142", "l");
		polishDiacriticsDict.put("\u0144", "n");
		polishDiacriticsDict.put("\u00F3", "o");
		polishDiacriticsDict.put("\u015B", "s");
		polishDiacriticsDict.put("\u017A", "z");
		polishDiacriticsDict.put("\u017C", "z");
		polishDiacriticsDict.put("\u0104", "A");
		polishDiacriticsDict.put("\u0106", "C");
		polishDiacriticsDict.put("\u0118", "E");
		polishDiacriticsDict.put("\u0141", "L");
		polishDiacriticsDict.put("\u0143", "N");
		polishDiacriticsDict.put("\u00D3", "O");
		polishDiacriticsDict.put("\u015A", "S");
		polishDiacriticsDict.put("\u0179", "Z");
		polishDiacriticsDict.put("\u017B", "Z");
	}
	
	public void getNotSentReports(BlockingQueue<PerfmonResult> blockingQueue)
	{
		File appDir = new File(ConfigProvider.getInstance().getReportDirectory());
		for (String reportDirName : appDir.list())
		{
			String reportDir = appDir + "\\" + reportDirName;
			File report = new File(reportDir);
			
			if(report.isDirectory() == false)
			{
				continue;
			}
			
			PerfmonResult perfmonResult = getReportExecutionDetails(reportDir, reportDirName);
			
			if(perfmonResult != null)
			{
				try
				{
					blockingQueue.put(perfmonResult);
				} 
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				Logger.getInstatnce().log(Severity.DEBUG, "Couldnt find report:" + reportDir + " " + reportDirName);
			}
		}
	}
	
	public String getReportText(String reportLocation)
	{
		BufferedReader br = null;
		InputStream is = null;
		StringBuilder sb = new StringBuilder();

		reportLocation += ConfigProvider.getInstance().getNewReportFileName();
		
		try
		{
			is = new FileInputStream(reportLocation);
			br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-16")));
			
			String line = "";
			while ((line = br.readLine()) != null)
			{
				sb.append(line);
			}

			br.close();
			is.close();
		} 
		catch (FileNotFoundException e)
		{
			Logger.getInstatnce().log(Severity.ERROR, "Couldnt find report: " + reportLocation);
		} 
		catch (IOException e)
		{
			Logger.getInstatnce().log(Severity.ERROR, "Error while reading report text");
		}
		
		String reportText = sb.toString();
		
		if (reportText != null)
		{
			reportText = removeDiacritics(reportText);
			reportText = reportText.replaceAll("\\P{Print}", "");		
		}
		
		return reportText;
	}
	
	public void removeReport(String reportLocation)
	{
		File reportDir = new File(reportLocation);
		
		for (File file : reportDir.listFiles())
		{
			file.delete();
		}
		
		reportDir.delete();
	}
	
	void setReportExecutionDetails(String reportLocation, String counterName, long timestamp) throws IOException 
	{
		File input = new File(reportLocation + ConfigProvider.getInstance().getReportFileName());
		Document doc = null;
		
		doc = Jsoup.parse(input, "UTF-16");
		Element table = (Element) doc.getElementById("c_1").getElementsByClass("info").get(0).parentNode().parentNode();
		table.append("<tr><td class='h4'>Przyczyna: </td><td class='info' id='CriticalSituationType'>" + counterName + "</td></tr><tr><td class='h4'>Stempel czasu: </td><td class='info' id='timestamp'>" + timestamp + "</td></tr>");
		PrintWriter writer = new PrintWriter(reportLocation + ConfigProvider.getInstance().getNewReportFileName(), "UTF-16");
		writer.print(doc.toString());
		writer.close();
		
	}
	
	private PerfmonResult getReportExecutionDetails(String reportDir, String reportName)
	{
		File reportFile = new File(reportDir + ConfigProvider.getInstance().getNewReportFileName());
		Document doc = null;
		PerfmonResult perfmonResult = null;
		
		try
		{
			doc = Jsoup.parse(reportFile, "UTF-16");
			String counterName = doc.getElementById("CriticalSituationType").html();
			String timestampString = doc.getElementById("timestamp").html();
			
			Long timestamp = Long.parseLong(timestampString);
			
			perfmonResult = new PerfmonResult(reportDir, timestamp, counterName, reportName);
		} 
		catch (IOException e)
		{
			Logger.getInstatnce().log(Severity.ERROR, "Couldnt  load execution details");
		}
		
		return perfmonResult;
	}
	
	public String removeDiacritics(String reportText) 
	{
		// zamienia polskie znaki diakretyczne na ich odpowiedniki bez "ogonkow"
		for (Map.Entry<String, String> entry : polishDiacriticsDict.entrySet())
		{
			reportText = reportText.replaceAll(entry.getKey(), entry.getValue());
		}
		
		// koduje lewy nawias kwadratowy
		reportText = reportText.replaceAll("\\[", "&#91;");
		//zamienia dlugi myslnik na krotki :)
		reportText = reportText.replaceAll("\u2014", "-");
		
		return reportText;
	}
}
