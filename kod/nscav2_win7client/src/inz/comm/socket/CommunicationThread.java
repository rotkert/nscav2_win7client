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
	private PerfmonResult perfmonResult;
	
	public CommunicationThread(BlockingQueue<PerfmonResult> blockingQueue)
	{
		this.blockingQueue = blockingQueue;
		this.dataPackProvider = new DataPackProvider();
		this.reportHandler = new ReportHandler();
		this.perfmonResult = null;
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
				if(perfmonResult == null)
				{
					perfmonResult = blockingQueue.take();
				}
				
				// wyslanie eventu o raporcie
				socket = new Socket();
				socket.connect(new InetSocketAddress(Config.getIp(), Config.getPort()), 2000);				
				socketConnectionContext = new SocketConnectionContext(dataPackProvider, Config.getEventHostname(), Config.getEventClientId(), false);
				socketRunner = new SocketRunner(socket, dataPackProvider, socketConnectionContext);
				dataPackProvider.setValue(perfmonResult.getReportName());
				dataPackProvider.setTimestamp(perfmonResult.getTimestamp());
				socketRunner.run();
				socketRunner.stopThread();
				socket.close();
				
				// wyslanie raportu
				socket = new Socket();
				socket.connect(new InetSocketAddress(Config.getIp(), Config.getPort()), 2000);
				socketConnectionContext = new SocketConnectionContext(dataPackProvider, Config.getReportHostName(), Config.getReportClientId(), true);
				socketRunner = new SocketRunner(socket, dataPackProvider, socketConnectionContext);
				String reportText = reportHandler.getReportText(perfmonResult.getReportLocation());
				dataPackProvider.setValue(reportText);
				dataPackProvider.setTimestamp(perfmonResult.getTimestamp());
				dataPackProvider.setReportName(perfmonResult.getReportName());
				socketRunner.run();
				socketRunner.stopThread();
				socket.close();
				
				reportHandler.removeReport(perfmonResult.getReportLocation());
				perfmonResult = null;
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				System.out.println(e.getMessage());
				try
				{
					Thread.sleep(1000);
				} catch (InterruptedException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	void stopSocketRunner()
	{
		// TODO
	}
}
