package mkaminski.inz.data.warehouse;

import java.util.LinkedList;

import mkaminski.inz.data.CriticalEvent;
import mkaminski.inz.dataCollector.DataCollectorWrapper;

public class MemoryUsageWarehouse implements IWarehouse
{
	private static final int queueSize = 10;
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
		
		if(valuesQueue.isEmpty() == false)
		{
			valuesAvg = valuesSum / valuesQueue.size();
		}
		
		if(valuesAvg >= critivalValue)
		{
			return CriticalEvent.MEMORY;
		}
		else
		{
			return CriticalEvent.OK;
		}
	}
}
