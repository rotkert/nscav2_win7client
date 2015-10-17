package devPackage;

import java.util.Date;
import java.util.concurrent.BlockingQueue;

import inz.data.collectors.CriticalEvent;
import inz.data.perfmon.PerfmonResult;

public class AddReport
{
	public AddReport(BlockingQueue<PerfmonResult> blockingQueue)
	{
		try
		{
			blockingQueue.put(new PerfmonResult("C:\\Users\\Miko\\AppData\\Roaming\\Nscav2_client\\MIKO-KOMPUTER_20151013-000069", new Date().getTime(), CriticalEvent.MEMORY, "MIKO-KOMPUTER_20151013-000069"));
//			blockingQueue.put(new PerfmonResult("C:\\Users\\Miko\\AppData\\Roaming\\Nscav2_client\\MIKO-KOMPUTER_20150912-000040\\new_report.html", new Date().getTime(), CriticalEvent.MEMORY, "MIKO-KOMPUTER_20150912-000040"));
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}