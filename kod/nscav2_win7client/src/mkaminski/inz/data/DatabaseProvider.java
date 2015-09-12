package mkaminski.inz.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import mkaminski.inz.dataCollector.CriticalEvent;
import mkaminski.inz.dataCollector.DataCollectorWrapper;
import mkaminski.inz.errorHandling.ErrorMessages;

public class DatabaseProvider
{
	private final DataCollectorWrapper dataCollectorWrapper;

	public DatabaseProvider()
	{
		dataCollectorWrapper = new DataCollectorWrapper();
	}

	public ArrayList<IcingaLog> getDataToSend()
	{
		String id = "usage";
		//String value = ((Double) dataCollectorWrapper.getPhysMemUsage()).toString();
//		String value = setReportExecutionDetails("C:\\Users\\Miko\\AppData\\Roaming\\Nscav2_client\\MIKO-KOMPUTER_20150908-000036", CriticalEvent.MEMORY, new Date().getTime());
		String value = "A";
		value = value.replaceAll("\\[", "&#91;");
//		value.replaceAll(";", "&#91");
		Long timestamp = new Date().getTime();
		String icingaLevel = "1";
		ArrayList<IcingaLog> logs = new ArrayList<>();
		IcingaLog log = new IcingaLog(id, value, timestamp, icingaLevel);
		logs.add(log);

		return logs;
	}

	public String getMostFilledTableName()
	{
		String mostFilledTableName = "memoryUsage";
		return mostFilledTableName;
	}

	public String getReportText()
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
}
