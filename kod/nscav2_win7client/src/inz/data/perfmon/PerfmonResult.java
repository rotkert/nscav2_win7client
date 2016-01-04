package inz.data.perfmon;

public class PerfmonResult
{
	private final String reportLocation;
	private final long timestamp;
	private final String counterName;
	private final String reportName;
	
	public PerfmonResult(String reportLocation, long timestamp, String counterName, String reportName)
	{
		this.reportLocation = reportLocation;
		this.timestamp = timestamp;
		this.counterName = counterName;
		this.reportName = reportName;
	}
	
	public String getReportLocation()
	{
		return reportLocation;
	}
	
	public long getTimestamp()
	{
		return timestamp;
	}
	
	public String getCounterName()
	{
		return counterName;
	}
	
	public String getReportName()
	{
		return reportName;
	}
	
}
