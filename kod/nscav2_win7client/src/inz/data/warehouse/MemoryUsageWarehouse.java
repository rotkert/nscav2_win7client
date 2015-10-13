package inz.data.warehouse;

import java.util.LinkedList;

import inz.commons.Logger;
import inz.commons.Severity;
import inz.data.collectors.CriticalEvent;
import inz.data.collectors.DataCollectorWrapper;

public class MemoryUsageWarehouse extends Warehouse
{
	private static final int queueSize = 1;
	private static final double critivalValue = 50;
	private LinkedList<Double> valuesQueue;
	private DataCollectorWrapper dataCollecor;
	
	public MemoryUsageWarehouse()
	{
		valuesQueue = new LinkedList<>();
		dataCollecor = new DataCollectorWrapper();
	}
	
	@Override
	public CriticalEvent processMeasure()
	{
		double measure = dataCollecor.getPhysMemUsage();
		addValue(measure);
		return validate();
	}
	
	private void addValue(double measure)
	{
		valuesQueue.addLast(measure);
		Logger.getInstatnce().log(Severity.INFO, "Physical memory usage = " + measure);
		
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
		
		System.out.println(valuesAvg);
		Logger.getInstatnce().log(Severity.INFO, "Average physical memory usage = " + valuesAvg);
		
		if(valuesAvg >= critivalValue)
		{
			valuesQueue.clear();
			return CriticalEvent.MEMORY;
		}
		else
		{
			return CriticalEvent.OK;
		}
	}
}
