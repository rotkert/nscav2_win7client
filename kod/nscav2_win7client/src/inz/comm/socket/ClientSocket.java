package inz.comm.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import inz.comm.config.Config;
import inz.comm.data.DatabaseProvider;
import inz.comm.exception.IcingaException;
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
	private static SocketThread socketThread;

	/**
	 * Stops thread after disconnection with server
	 */
	public static void stopSocketThread()
	{
		SocketThread.stopThread();
	}

	/**
	 * Method that tries to connect to remote server
	 * 
	 * @throws IcingaException
	 *             when connection is not possible (timeout probably)
	 */
	public void connect(DatabaseProvider provider) throws IcingaException
	{
		try
		{
			socket = new Socket();
			socket.connect(new InetSocketAddress(Config.getIp(), Config.getPort()), 2000);
			socketThread = new SocketThread(socket, provider);

			socketThread.start();
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
