package inz.mkamins.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import inz.mkamins.commons.ConfigProvider;
import inz.mkamins.commons.Logger;
import inz.mkamins.commons.Severity;
import inz.mkamins.data.DataPackProvider;
import inz.mkamins.perfmon.PerfmonResult;
import inz.mkamins.perfmon.ReportHandler;

public class CommunicationThread implements Runnable
{
	private BlockingQueue<PerfmonResult> blockingQueue;
	private ReportHandler reportHandler;
	private PerfmonResult perfmonResult;
	private int icingaServerTimeout;
	private int waitForConnectionTime;
	
	public CommunicationThread(BlockingQueue<PerfmonResult> blockingQueue)
	{
		this.blockingQueue = blockingQueue;
		this.reportHandler = new ReportHandler();
		this.perfmonResult = null;
		this.icingaServerTimeout = ConfigProvider.getInstance().getIcingaServerTimeout();
		this.waitForConnectionTime = ConfigProvider.getInstance().getWaitForConnection();
	}
	
	public void run()
	{
		Socket socket;
		SocketRunner socketRunner;
		SocketConnectionContextCst socketConnectionContext;
		while(true)
		{
			try
			{
				if(perfmonResult == null)
				{
					perfmonResult = blockingQueue.take();
				}
				
				String reportName = perfmonResult.getReportName();
				String reportLocation = perfmonResult.getReportLocation();
				String counterName = reportHandler.removeDiacritics(perfmonResult.getCounterCategory());
				long timestamp = perfmonResult.getTimestamp();
				String reportText = reportHandler.getReportText(reportLocation);
				
				if (reportText == null)
				{
					perfmonResult = null;
					continue;
				}
				
				socket = new Socket();
				socket.connect(new InetSocketAddress(ConfigProvider.getInstance().getIp(), ConfigProvider.getInstance().getPort()), icingaServerTimeout);			
				DataPackProvider dataPackProvider = new DataPackProvider(reportText, reportName, timestamp, counterName);
				socketConnectionContext = new SocketConnectionContextCst(dataPackProvider);
				socketRunner = new SocketRunner(socket, socketConnectionContext);
				socketRunner.start();
				socket.close();
				
				Logger.getInstatnce().log(Severity.INFO, "Report " + reportName + " was successfully sent to Icinga2 server.");
				reportHandler.removeReport(perfmonResult.getReportLocation());
				perfmonResult = null;
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			} 
			catch (IOException e)
			{
				Logger.getInstatnce().log(Severity.ERROR, "Error while sending report " + perfmonResult.getReportName() + ". Error message: " + e.getMessage());
				
				try
				{
					Thread.sleep(waitForConnectionTime);
				} 
				catch (InterruptedException e1)
				{
					e1.printStackTrace();
				}
			}
		}
	}
}
