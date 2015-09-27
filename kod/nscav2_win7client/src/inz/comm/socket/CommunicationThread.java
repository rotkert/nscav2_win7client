package inz.comm.socket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import inz.comm.data.DataPackProvider;
import inz.data.perfmon.PerfmonResult;
import inz.data.perfmon.ReportHandler;
import mkaminski.inz.errorHandling.ErrorMessages;

public class CommunicationThread extends Thread
{
	private BlockingQueue<PerfmonResult> blockingQueue;
	private SocketRunner socketRunner;
	private DataPackProvider dataPackProvider;
	private ReportHandler reportHandler;
	
	public CommunicationThread(BlockingQueue<PerfmonResult> blockingQueue, Socket socket) throws IOException
	{
		this.blockingQueue = blockingQueue;
		this.dataPackProvider = new DataPackProvider();
		this.socketRunner = new SocketRunner(socket, dataPackProvider);
		this.reportHandler = new ReportHandler();
	}
	
	public void run()
	{
		while(true)
		{
			try
			{
				PerfmonResult pr = blockingQueue.take();
				String reportText = reportHandler.getReportText(pr.getReportLocation());
				dataPackProvider.setValue(reportText);
				dataPackProvider.setTimestamp(pr.getTimestamp());
				socketRunner.run();
				
			} 
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	void stopSocketRunner()
	{
		socketRunner.stopThread();
	}
}
