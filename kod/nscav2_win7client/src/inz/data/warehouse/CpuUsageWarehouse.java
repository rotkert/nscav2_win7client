package inz.data.warehouse;

import java.util.LinkedList;

import devPackage.Writer;
import inz.commons.Logger;
import inz.commons.Severity;
import inz.data.collectors.CpuUsageCollectorWrapper;
import inz.data.collectors.CriticalEvent;

public class CpuUsageWarehouse extends Warehouse
{
	private static final int queueSize = 1;
	private static final double critivalValue = 10;
	private LinkedList<Double> valuesQueue;
	private CpuUsageCollectorWrapper cpuUsageCollector;
	
	public CpuUsageWarehouse()
	{
		valuesQueue = new LinkedList<>();
		cpuUsageCollector = new CpuUsageCollectorWrapper();
	}
	
	@Override
	public CriticalEvent processMeasure()
	{
		double measure = cpuUsageCollector.getCpuUsage();
		addValue(measure);
		return validate();
	}
	
	@Override
	public void reset()
	{
		valuesQueue.clear();
	}
	
	private void addValue(double measure)
	{
		valuesQueue.addLast(measure);
		Logger.getInstatnce().log(Severity.INFO, "Cpu usage = " + measure);
		
		if(valuesQueue.size() > queueSize)
		{
			valuesQueue.remove();
		}
	}
	
	private CriticalEvent validate()
	{
		double valuesSum = 0;
		double valuesAvg = 0;
		for (Double value : valuesQueue)
		{
			valuesSum += value;
		}
		
		if(valuesQueue.size() == queueSize)
		{
			valuesAvg = valuesSum / valuesQueue.size();
		}
		
		//dev
		Writer.write("Cpu usage:" + valuesAvg);
		
		Logger.getInstatnce().log(Severity.INFO, "Average cpu usage = " + valuesAvg);
		
		if(valuesAvg >= critivalValue)
		{
			valuesQueue.clear();
			return CriticalEvent.CPU;
		}
		else
		{
			return CriticalEvent.OK;
		}
	}
}
