package mkaminski.inz.data;

import java.util.ArrayList;
import java.util.Date;

import mkaminski.inz.dataCollector.DataCollectorWrapper;

public class DatabaseProvider
{
	private final DataCollectorWrapper dataCollectorWrapper;
	
	public DatabaseProvider()
	{
		dataCollectorWrapper= new DataCollectorWrapper();
	}
	
	public ArrayList<IcingaLog> getDataToSend()
	{
		String id = "usage";
		String value = ((Double)dataCollectorWrapper.getPhysMemUsage()).toString();
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

}
