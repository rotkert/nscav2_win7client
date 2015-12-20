package inz.comm.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import devPackage.Writer;
import inz.comm.data.DataPackProvider;
import inz.commons.ConfigProvider;
import inz.commons.Logger;
import inz.commons.Severity;
import inz.data.perfmon.PerfmonResult;
import inz.data.perfmon.ReportHandler;

public class CommunicationThread
{
	private BlockingQueue<PerfmonResult> blockingQueue;
	private ReportHandler reportHandler;
	private PerfmonResult perfmonResult;
	
	public CommunicationThread(BlockingQueue<PerfmonResult> blockingQueue)
	{
		this.blockingQueue = blockingQueue;
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
				
				String reportName = perfmonResult.getReportName();
				String reportLocation = perfmonResult.getReportLocation();
				String counterName = perfmonResult.getCounterName();
				long timestamp = perfmonResult.getTimestamp();
				String reportText = reportHandler.getReportText(reportLocation);
				
				// wyslanie raportu
				socket = new Socket();
				socket.connect(new InetSocketAddress(ConfigProvider.getInstance().getIp(), ConfigProvider.getInstance().getPort()), 2000);			
				DataPackProvider dataPackProvider = new DataPackProvider(reportText, reportName, timestamp, counterName);
				socketConnectionContext = new SocketConnectionContext(dataPackProvider, ConfigProvider.getInstance().getReportHostName(), ConfigProvider.getInstance().getReportClientId());
				socketRunner = new SocketRunner(socket, dataPackProvider, socketConnectionContext);
				socketRunner.run();
				socketRunner.stopThread();
				socket.close();
				
				Logger.getInstatnce().log(Severity.INFO, "Report " + reportName + " was successfully sent to Icinga2 server.");
//				reportHandler.removeReport(perfmonResult.getReportLocation());
				perfmonResult = null;
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				//dev
				Writer.write(e.getMessage());
				
				Logger.getInstatnce().log(Severity.ERROR, "Error while sending report " + perfmonResult.getReportName() + ". Error message: " + e.getMessage());
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
