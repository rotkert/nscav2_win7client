package inz.mkamins.data;

import java.util.ArrayList;

import inz.mkamins.commons.ConfigProvider;


public class DataPackProvider
{
	private String icingaService;
	private String value;
	private Long timestamp;
	private String reportName;
	private String perfdataName;
	
	public DataPackProvider(String value, String reportName, Long timestamp, String perfdataName)
	{
		this.icingaService = ConfigProvider.getInstance().getServiceName();
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
		String icingaLevel = ConfigProvider.getInstance().getPluginOutputLevel();
		ArrayList<IcingaLog> logs = new ArrayList<>();
		IcingaLog log = new IcingaLog(value, timestamp, icingaLevel);
		logs.add(log);

		return logs;
	}
}
