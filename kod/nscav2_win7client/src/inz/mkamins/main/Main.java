package inz.mkamins.main;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.xml.bind.JAXBException;

import devPackage.AddReport;
import inz.crypto.CryptoManager;
import inz.mkamins.commons.ConfigProvider;
import inz.mkamins.commons.Logger;
import inz.mkamins.commons.Severity;
import inz.mkamins.perfmon.PerfmonResult;
import inz.mkamins.receiver.Receiver;
import inz.mkamins.socket.CommunicationThread;

public class Main
{

	public static void main(String args[])
	{
		try
		{
			Logger.getInstatnce().log(Severity.INFO, "Application nscav2_client started.");
			ConfigProvider.getInstance().init();
			CryptoManager.INSTANCE.readKeys();
			BlockingQueue<PerfmonResult> blockingQueue = new LinkedBlockingQueue<>();
			Logger.getInstatnce().log(Severity.DEBUG, "asdfsdf");
			new Thread(new CommunicationThread(blockingQueue)).start();
			Logger.getInstatnce().log(Severity.DEBUG, "mikolaj");
//			new AddReport(blockingQueue);
//			ReportHandler reportHandler = new ReportHandler();
//			reportHandler.getNotSentReports(blockingQueue);
			
			new Receiver().work(blockingQueue);
		} catch (IOException e)
		{
			Logger.getInstatnce().log(Severity.ERROR, e.getMessage());
			e.printStackTrace();
		}
		catch (JAXBException e)
		{
			Logger.getInstatnce().log(Severity.ERROR, "Error while reading configuration: " + e.getMessage());
			e.printStackTrace();
		}

		Logger.getInstatnce().log(Severity.INFO, "Application closed");
	}	
}
