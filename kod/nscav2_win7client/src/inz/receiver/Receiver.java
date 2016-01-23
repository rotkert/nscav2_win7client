package inz.receiver;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
		Lock lock = new ReentrantLock();
		Logger.getInstatnce().log(Severity.DEBUG, "Rozpoczêto nas³uvhiwanie");
		while(true)
		{	
			Socket socket = serverSocket.accept();
			InputStream is = socket.getInputStream();
		
			byte[] message = new byte[1024];
			int bytesRead = is.read(message);
			String counterCategory = new String(message, 0, bytesRead, "UTF-8");
			Logger.getInstatnce().log(Severity.INFO, "Odebrano porcjê danych: " + counterCategory);
			new Thread(new PerfmonHandler(blockingQueue,lock, counterCategory)).start();
		}
			
	}
	
}
