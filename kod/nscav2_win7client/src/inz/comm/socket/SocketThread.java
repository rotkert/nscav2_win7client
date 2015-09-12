package inz.comm.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

import inz.comm.crypto.CryptoManager;
import mkaminski.inz.data.DatabaseProvider;

/**
 * Class that works in separate thread and is used to communicate with server
 * 
 * @author Marcin Kubik <markubik@gmail.com>
 */
public class SocketThread extends Thread
{
	/**
	 * Timeout after which application should realize that socket is not
	 * responding
	 */
	private static final int TIMEOUT = 2000;

	/**
	 * Object of <code>BufferedReader</code> class used to read info from stream
	 */
	private InputStream is;

	/** Used to write data to stream */
	private OutputStream os;

	/** Socket that connects to remote server */
	private Socket socket;

	/** Database Provider */
	private DatabaseProvider databaseProvider;

	/** Context of communication, used in <code>State Machine </code> pattern */
	private SocketConnectionContext socketConnectionContext;

	/** Flag used to stop and start thread */
	private static volatile boolean isThreadRunning = true;

	/** data */
	private byte[] data = new byte[16384];

	/**
	 * Public constructor that sets socket (and timeout) Sets state of
	 * <code>socketConnectionContext</code> to <code>Connected</code>
	 * 
	 * @param s
	 * @throws IOException
	 */
	public SocketThread(Socket s, DatabaseProvider provider) throws IOException
	{
		this.socket = s;
		this.databaseProvider = provider;
		this.socket.setSoTimeout(TIMEOUT);
		this.socketConnectionContext = new SocketConnectionContext(databaseProvider);
		this.os = socket.getOutputStream();
		this.is = new DataInputStream(socket.getInputStream());
		os.flush();
		CryptoManager.INSTANCE.resetIV();
		isThreadRunning = true;
	}

	/**
	 * In run, there is while, that tries to send data or waits for receiving
	 */
	public void run()
	{
		while (isThreadRunning)
		{
			try
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
				} else
				{
					isThreadRunning = false;
				}

			} catch (SocketTimeoutException e)
			{
				isThreadRunning = false;
			} catch (IOException e)
			{
				// TODO
			} finally
			{
				// TODO
			}
		}
	}

	/** Sets <code>isThreadRunning</code> variable to stop thread */
	public static void stopThread()
	{
		isThreadRunning = false;
	}
}
