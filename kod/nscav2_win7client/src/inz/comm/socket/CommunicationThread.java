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
import mkaminski.inz.errorHandling.ErrorMessages;

public class CommunicationThread extends Thread
{
	private BlockingQueue<PerfmonResult> blockingQueue;
	private SocketRunner socketRunner;
	private DataPackProvider dataPackProvider;
	
	public CommunicationThread(BlockingQueue<PerfmonResult> blockingQueue, Socket socket) throws IOException
	{
		this.blockingQueue = blockingQueue;
		this.dataPackProvider = new DataPackProvider();
		this.socketRunner = new SocketRunner(socket, dataPackProvider);
	}
	
	public void run()
	{
		while(true)
		{
			try
			{
				PerfmonResult pr = blockingQueue.take();
				dataPackProvider.setValue(getReportText(pr.getReportLocation()));
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
	
	private String getReportText(String reportLocation)
	{
		System.out.println(System.getenv("APPDATA"));
		String reportPath = "C:\\Users\\Miko\\Desktop\\inz_raporty\\MIKO-KOMPUTER_20150530-000001\\raport.xml";
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		try
		{
			br = new BufferedReader(new FileReader(new File(reportPath)));

			String line = "";
			while ((line = br.readLine()) != null)
			{
				sb.append(line.trim());
			}

			br.close();
		} catch (FileNotFoundException e)
		{
			// TODO
			System.out.println(ErrorMessages.REPORT_NOT_FOUND);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
}
