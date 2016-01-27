package inz.mkamins.main;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.xml.bind.JAXBException;

import inz.mkamins.commons.ConfigProvider;
import inz.mkamins.commons.Logger;
import inz.mkamins.commons.Severity;
import inz.mkamins.perfmon.PerfmonResult;
import inz.mkamins.perfmon.ReportHandler;
import inz.mkamins.receiver.Receiver;
import inz.mkamins.socket.CommunicationThread;
import inz.mkubik.crypto.CryptoManager;

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
			
			ReportHandler reportHandler = new ReportHandler();
			reportHandler.getNotSentReports(blockingQueue);
			
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
