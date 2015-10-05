package inz.comm.data;

import java.util.ArrayList;


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
		String icingaLevel = "2";
		ArrayList<IcingaLog> logs = new ArrayList<>();
		IcingaLog log = new IcingaLog(id, "24", timestamp, icingaLevel);
		logs.add(log);

		return logs;
	}

	public String getMostFilledTableName()
	{
		String mostFilledTableName = "memoryUsage";
		return mostFilledTableName;
	}
}
