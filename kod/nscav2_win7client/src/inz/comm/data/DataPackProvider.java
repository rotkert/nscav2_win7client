package inz.comm.data;

import java.util.ArrayList;


public class DataPackProvider
{
	private String icingaService;
	private String value;
	private Long timestamp;
	private String reportName;
	private String perfdataName;
	
	public DataPackProvider(String value, String reportName, Long timestamp, String perfdataName)
	{
		this.icingaService = "diagnostics";
		this.value = value;
		this.timestamp = timestamp;
		this.reportName = reportName;
		this.perfdataName = perfdataName;
	}
	
	public String getReportName()
	{
		return reportName;
	}
	
	public String getIcingaService()
	{
		return icingaService;
	}
	
	public String getPerfdataName()
	{
		return perfdataName;
	}
	
	public ArrayList<IcingaLog> getDataToSend()
	{
		String id = "usage";
		String icingaLevel = "2";
		ArrayList<IcingaLog> logs = new ArrayList<>();
		IcingaLog log = new IcingaLog(id, value, timestamp, icingaLevel);
		logs.add(log);

		return logs;
	}
}
