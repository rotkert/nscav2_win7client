package inz.data.collectors;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;

import inz.data.perfmon.PerfmonHandler;
import inz.data.perfmon.PerfmonResult;
import inz.data.warehouse.MemoryUsageWarehouse;
import inz.data.warehouse.Warehouse;

public class DataValidator implements Runnable
{
	private BlockingQueue<PerfmonResult> blockingQueue;
	private PerfmonHandler perfmonHandler;
	private LinkedList<Warehouse> warehouses;
	
	public DataValidator(BlockingQueue<PerfmonResult> blockingQueue)
	{
		super();
		this.blockingQueue = blockingQueue;
		perfmonHandler = new PerfmonHandler();
		warehouses = new LinkedList<>();
		warehouses.add(new MemoryUsageWarehouse());
	}
	
	@Override
	public void run()
	{
		for (Warehouse warehouse : warehouses)
		{
			CriticalEvent event = warehouse.processMeasure();
			
			if(event.equals(CriticalEvent.OK) == false)
			{
				PerfmonResult pr = perfmonHandler.getReport(event);
				
				try
				{
					blockingQueue.put(pr);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
	}	
}
