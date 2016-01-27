package inz.mkamins.perfmon;

public class PerfmonResult
{
	private final String reportLocation;
	private final long timestamp;
	private final String counterCategory;
	private final String reportName;
	
	public PerfmonResult(String reportLocation, long timestamp, String counterCategory, String reportName)
	{
		this.reportLocation = reportLocation;
		this.timestamp = timestamp;
		this.counterCategory = counterCategory;
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
	
	public String getCounterCategory()
	{
		return counterCategory;
	}
	
	public String getReportName()
	{
		return reportName;
	}
	
}
