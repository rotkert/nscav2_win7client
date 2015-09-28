package inz.main;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import inz.comm.config.Config;
import inz.comm.crypto.CryptoManager;
import inz.comm.socket.ClientSocket;
import inz.data.collectors.CriticalEvent;
import inz.data.perfmon.PerfmonResult;
import inz.data.perfmon.ReportHandler;

public class Main
{

	public static void main(String[] args)
	{
		Config.init();
		CryptoManager.INSTANCE.readKeys();
		BlockingQueue<PerfmonResult> blockingQueue = new LinkedBlockingQueue<>();
		ReportHandler reportHandler = new ReportHandler();
//		reportHandler.getNotSentReports(blockingQueue);
//		ValidationScheduler validationScheduler = new ValidationScheduler(blockingQueue);
		try
		{
			blockingQueue.put(new PerfmonResult("C:\\Users\\Miko\\Desktop\\tmp.html", new Date().getTime(), CriticalEvent.MEMORY));
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ClientSocket.INSTANCE.connect(blockingQueue);
	}	

}
