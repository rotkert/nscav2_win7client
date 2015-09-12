package mkaminski.inz.data.warehouse;

import java.util.LinkedList;

import devPackage.Writer;
import mkaminski.inz.dataCollector.CriticalEvent;
import mkaminski.inz.dataCollector.DataCollectorWrapper;

public class MemoryUsageWarehouse extends Warehouse
{
	private static final int queueSize = 10;
	private static final double critivalValue = 80;
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
		
		if(valuesQueue.size() == queueSize)
		{
			valuesAvg = valuesSum / valuesQueue.size();
		}
		
		// dev
		Writer.write(((Double)valuesAvg).toString());
		
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
