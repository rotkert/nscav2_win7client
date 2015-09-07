package mkaminski.inz.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

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
		String value = ((Double) dataCollectorWrapper.getPhysMemUsage()).toString();
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

	public String runPerfmon()
	{
		String projectDirectory = System.getProperty("user.dir");
		String command = "powershell.exe " + projectDirectory + "\\extensions\\runPerfmon.ps1";
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
}
