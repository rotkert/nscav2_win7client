package inz.comm.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import inz.data.collectors.DataCollectorWrapper;
import mkaminski.inz.errorHandling.ErrorMessages;

public class DataPackProvider
{
	private String value;
	private Long timestamp;
	
	public void setValue(String value)
	{
		this.value = value;
	}
	
	public void setTimestamp(Long timestamp)
	{
		this.timestamp = timestamp;
	}
	
	public ArrayList<IcingaLog> getDataToSend()
	{
		String id = "usage";
		//String value = ((Double) dataCollectorWrapper.getPhysMemUsage()).toString();
//		String value = setReportExecutionDetails("C:\\Users\\Miko\\AppData\\Roaming\\Nscav2_client\\MIKO-KOMPUTER_20150908-000036", CriticalEvent.MEMORY, new Date().getTime());
	
		value = value.replaceAll("\\[", "&#91;");
//		value.replaceAll(";", "&#91");

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
}
