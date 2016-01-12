package inz.receiver;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import inz.commons.ConfigProvider;
import inz.commons.Logger;
import inz.commons.Severity;
import inz.data.perfmon.PerfmonHandler;
import inz.data.perfmon.PerfmonResult;

public class Receiver
{
	public void work(BlockingQueue<PerfmonResult> blockingQueue) throws IOException
	{
		int port = ConfigProvider.getInstance().getReceivingPort();
		ServerSocket serverSocket = new ServerSocket(port);
		while(true)
		{	
			Socket socket = serverSocket.accept();
			InputStream is = socket.getInputStream();
		
			byte[] message = new byte[1024];
			int bytesRead = is.read(message);
			String counterCategory = new String(message, 0, bytesRead);
			Logger.getInstatnce().log(Severity.INFO, "Odebrano porcjê danych: " + counterCategory);
			new Thread(new PerfmonHandler(blockingQueue, counterCategory)).start();
		}
			
	}
	
}
