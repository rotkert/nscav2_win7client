package mkaminski.inz.data;

import java.util.ArrayList;
import java.util.Date;

public class DatabaseProvider
{

	public ArrayList<IcingaLog> getDataToSend()
	{
		String id = "disc";
		String value = "5";
		Long timestamp = new Date().getTime();
		String icingaLevel = "1";
		ArrayList<IcingaLog> logs = new ArrayList<>();
		IcingaLog log = new IcingaLog(id, value, timestamp, icingaLevel);
		logs.add(log);
		
		return logs;
	}

	public String getMostFilledTableName()
	{
		String mostFilledTableName = "disc";
		return mostFilledTableName;
	}

}
