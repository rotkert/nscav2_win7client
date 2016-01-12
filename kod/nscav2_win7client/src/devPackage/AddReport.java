package devPackage;

import java.util.Date;
import java.util.concurrent.BlockingQueue;

import inz.data.perfmon.PerfmonResult;

public class AddReport
{
	public AddReport(BlockingQueue<PerfmonResult> blockingQueue)
	{
		try
		{
			blockingQueue.put(new PerfmonResult("C:\\Users\\Miko\\AppData\\Roaming\\Nscav2_client\\reports\\MIKO-KOMPUTER_20160111-000287", new Date().getTime() / 1000, "User", "MIKO-KOMPUTER_20160111-000287"));
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
