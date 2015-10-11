package inz.main;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import inz.comm.config.Config;
import inz.comm.crypto.CryptoManager;
import inz.comm.socket.CommunicationThread;
import inz.commons.Logger;
import inz.data.collectors.CriticalEvent;
import inz.data.collectors.ValidationScheduler;
import inz.data.perfmon.PerfmonResult;
import inz.data.perfmon.ReportHandler;

public class Main
{

	public static void main(String[] args)
	{
		Logger.getInstatnce();
		Config.init();
		CryptoManager.INSTANCE.readKeys();
		BlockingQueue<PerfmonResult> blockingQueue = new LinkedBlockingQueue<>();
		ReportHandler reportHandler = new ReportHandler();
//		reportHandler.getNotSentReports(blockingQueue);
//		ValidationScheduler validationScheduler = new ValidationScheduler(blockingQueue);
		try
		{
			blockingQueue.put(new PerfmonResult("C:\\Users\\Miko\\AppData\\Roaming\\Nscav2_client\\MIKO-KOMPUTER_20150912-000036\\new_report.html", new Date().getTime(), CriticalEvent.MEMORY, "MIKO-KOMPUTER_20150912-000036"));
//			blockingQueue.put(new PerfmonResult("C:\\Users\\Miko\\AppData\\Roaming\\Nscav2_client\\MIKO-KOMPUTER_20150912-000040\\new_report.html", new Date().getTime(), CriticalEvent.MEMORY, "MIKO-KOMPUTER_20150912-000040"));
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
		{
			new CommunicationThread(blockingQueue).run();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	
}
