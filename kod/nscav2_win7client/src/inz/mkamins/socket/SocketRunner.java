package inz.mkamins.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import inz.crypto.CryptoManager;

public class SocketRunner
{
	private static final int TIMEOUT = 2000;

	private InputStream is;

	private OutputStream os;

	private Socket socket;

	private SocketConnectionContextCst socketConnectionContext;

	private static volatile boolean isThreadRunning = true;

	private byte[] data = new byte[16384];

	public SocketRunner(Socket s, SocketConnectionContextCst socketConnectionContext) throws IOException
	{
		this.socket = s;
		this.socket.setSoTimeout(TIMEOUT);
		this.socketConnectionContext = socketConnectionContext;
		this.os = socket.getOutputStream();
		this.is = new DataInputStream(socket.getInputStream());
		os.flush();
		CryptoManager.INSTANCE.resetIV();
		isThreadRunning = true;
	}

	public void start() throws IOException
	{
		while (isThreadRunning)
		{
			data = new byte[16384];
			is.read(data);
			byte[] sizeOfMessageInBytes = new byte[4];
			System.arraycopy(data, 0, sizeOfMessageInBytes, 0, 4);

			ByteBuffer wrapped = ByteBuffer.wrap(sizeOfMessageInBytes);
			int size = wrapped.getInt();
			
			if (socketConnectionContext.proceedText(size, data))
			{
				byte[] dataToSend = socketConnectionContext.getDataToSend();
				os.write(dataToSend, 0, dataToSend.length);
				socketConnectionContext.setSoTimeout(this.socket);
				socketConnectionContext.setNextState();
				if (socketConnectionContext.getState().getClass().equals("LogsSent"))
				{
					break;
				}
			} else
			{
				isThreadRunning = false;
			}
		}
	}

}
