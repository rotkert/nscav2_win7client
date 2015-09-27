package inz.comm.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.BlockingQueue;

import inz.comm.config.Config;
import inz.comm.data.DataPackProvider;
import inz.comm.exception.IcingaException;
import inz.data.perfmon.PerfmonResult;
import mkaminski.inz.errorHandling.ErrorMessages;

/**
 * Class used to start connection with socket on remote server. Starts thread
 * that is responsible for communication. Created in Singleton pattern
 * 
 * @author Marcin Kubik <markubik@gmail.com>
 * 
 */
public enum ClientSocket
{
	/** Instance of ClientSocket class (in Singleton pattern) */
	INSTANCE;

	/** Object used for creation connection */
	private Socket socket;

	/** Thread that talks with server */
	private static CommunicationThread communicationThread;

	/**
	 * Stops thread after disconnection with server
	 */
	public static void stopSocketThread()
	{
		communicationThread.stopSocketRunner();;
	}

	/**
	 * Method that tries to connect to remote server
	 * 
	 * @throws IcingaException
	 *             when connection is not possible (timeout probably)
	 */
	public void connect(BlockingQueue<PerfmonResult> blockingQueue) throws IcingaException
	{
		try
		{
			socket = new Socket();
			socket.connect(new InetSocketAddress(Config.getIp(), Config.getPort()), 2000);
			communicationThread = new CommunicationThread(blockingQueue, socket);

			communicationThread.start();
		} catch (SocketTimeoutException e)
		{
			System.out.println(ErrorMessages.NOT_CONNECTED);
		} catch (IOException e)
		{
			// TODO
		} catch (IllegalArgumentException e)
		{
			// TODO
		}
	}
}
