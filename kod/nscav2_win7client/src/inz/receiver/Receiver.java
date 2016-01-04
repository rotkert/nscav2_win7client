package inz.receiver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Receiver
{
	public void work() throws IOException
	{
		ServerSocket serverSocket = new ServerSocket(4545);
		while(true)
		{	
			Socket socket = serverSocket.accept();
			InputStream is = socket.getInputStream();
		
			byte[] message = new byte[1024];
			int bytesRead = is.read(message);
			String meassageText = new String(message, 0, bytesRead);
			System.out.println("Received " + meassageText);
		}
			
	}
	
}
