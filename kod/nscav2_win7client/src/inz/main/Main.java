package inz.main;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import devPackage.AddReport;
import inz.comm.config.Config;
import inz.comm.crypto.CryptoManager;
import inz.comm.socket.CommunicationThread;
import inz.commons.Logger;
import inz.commons.Severity;
import inz.data.collectors.ValidationScheduler;
import inz.data.perfmon.PerfmonResult;
import inz.data.perfmon.ReportHandler;

public class Main
{

	public static void main(String[] args)
	{
		Logger.getInstatnce().log(Severity.INFO, "Application nscav2_client started.");
		Config.init();
		CryptoManager.INSTANCE.readKeys();
		BlockingQueue<PerfmonResult> blockingQueue = new LinkedBlockingQueue<>();
//		ReportHandler reportHandler = new ReportHandler();
//		reportHandler.getNotSentReports(blockingQueue);
		ValidationScheduler validationScheduler = new ValidationScheduler(blockingQueue);
		
		// dev
//		new AddReport(blockingQueue);
		
		new CommunicationThread(blockingQueue).run();
		

	}	
}
