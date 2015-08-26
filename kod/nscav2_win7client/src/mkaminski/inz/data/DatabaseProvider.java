package mkaminski.inz.data;

import java.util.ArrayList;

public class DatabaseProvider
{

	public ArrayList<IcingaLog> getDataToSend()
	{
		String id = "disc";
		String value = "full";
		String timestamp = "2015-08-26";
		String icingaLevel = "0";
		ArrayList<IcingaLog> logs = new ArrayList<>();
		IcingaLog log = new IcingaLog(id, value, timestamp, icingaLevel);
		logs.add(log);
		
		return logs;
	}

	public String getMostFilledTableName()
	{
		String mostFilledTableName = "icinga";
		return mostFilledTableName;
	}

}
