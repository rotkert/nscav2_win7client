package inz.data.perfmon;

import inz.data.collectors.CriticalEvent;

public class PerfmonResult
{
	private final String reportLocation;
	private final long timestamp;
	private final CriticalEvent event;
	private final String reportName;
	
	public PerfmonResult(String reportLocation, long timestamp, CriticalEvent event, String reportName)
	{
		this.reportLocation = reportLocation;
		this.timestamp = timestamp;
		this.event = event;
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
	
	public CriticalEvent getEvent()
	{
		return event;
	}
	
	public String getReportName()
	{
		return reportName;
	}
	
}
