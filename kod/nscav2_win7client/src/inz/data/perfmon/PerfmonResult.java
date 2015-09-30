package inz.data.perfmon;

import inz.data.collectors.CriticalEvent;

public class PerfmonResult
{
	private final String reportLocation;
	private final long timestamp;
	private final CriticalEvent event;
	
	public PerfmonResult(String reportLocation, long timestamp, CriticalEvent event)
	{
		this.reportLocation = reportLocation;
		this.timestamp = timestamp;
		this.event = event;
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
	
}
