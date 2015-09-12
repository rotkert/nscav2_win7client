package inz.data.collectors;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import inz.data.perfmon.PerfmonResult;

public class ValidationScheduler
{
	private ScheduledExecutorService scheduleService;
	
	public ValidationScheduler(BlockingQueue<PerfmonResult> blockingQueue) 
	{
		DataValidator dataValidator = new DataValidator(blockingQueue);
		scheduleService = Executors.newScheduledThreadPool(1);
		scheduleService.scheduleWithFixedDelay(dataValidator, 0, 10, TimeUnit.SECONDS);
	}
	
	public void stopValidation()
	{
		scheduleService.shutdownNow();
	}
}
