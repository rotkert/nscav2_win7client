package inz.comm.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import inz.comm.config.Config;
import inz.comm.data.DataPackProvider;
import inz.data.perfmon.PerfmonResult;
import inz.data.perfmon.ReportHandler;

public class CommunicationThread
{
	private BlockingQueue<PerfmonResult> blockingQueue;
	private DataPackProvider dataPackProvider;
	private ReportHandler reportHandler;
	
	public CommunicationThread(BlockingQueue<PerfmonResult> blockingQueue) throws IOException
	{
		this.blockingQueue = blockingQueue;
		this.dataPackProvider = new DataPackProvider();
		this.reportHandler = new ReportHandler();
	}
	
	public void run()
	{
		Socket socket;
		SocketRunner socketRunner;
		SocketConnectionContext socketConnectionContext;
		while(true)
		{
			try
			{
				PerfmonResult pr = blockingQueue.take();
				
				// wyslanie raportu
				socket = new Socket();
				socket.connect(new InetSocketAddress(Config.getIp(), Config.getPort()), 2000);
				socketConnectionContext = new SocketConnectionContext(dataPackProvider, Config.getReportHostName(), Config.getReportClientId());
				socketRunner = new SocketRunner(socket, dataPackProvider, socketConnectionContext);
				String reportText = reportHandler.getReportText(pr.getReportLocation());
				dataPackProvider.setValue(reportText);
				dataPackProvider.setTimestamp(pr.getTimestamp());
				socketRunner.run();
				socketRunner.stopThread();
				socket.close();
				
				// wyslanie eventu o raporcie
				socket = new Socket();
				socket.connect(new InetSocketAddress(Config.getIp(), Config.getPort()), 2000);				
				socketConnectionContext = new SocketConnectionContext(dataPackProvider, Config.getEventHostname(), Config.getEventClientId());
				socketRunner = new SocketRunner(socket, dataPackProvider, socketConnectionContext);
				dataPackProvider.setValue(pr.getEvent().toString());
				dataPackProvider.setTimestamp(pr.getTimestamp());
				socketRunner.run();
				socketRunner.stopThread();
				socket.close();
			} 
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	void stopSocketRunner()
	{
		// TODO
	}
}
