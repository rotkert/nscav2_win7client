package inz.main;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.xml.bind.JAXBException;

import devPackage.AddReport;
import inz.commons.ConfigProvider;
import inz.commons.Logger;
import inz.commons.Severity;
import inz.crypto.CryptoManager;
import inz.data.perfmon.PerfmonResult;
import inz.mkamins.socket.CommunicationThread;
import inz.receiver.Receiver;

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
			new Thread(new CommunicationThread(blockingQueue)).start();
			new AddReport(blockingQueue);
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
