package mkaminski.inz.state;

import java.net.Socket;
import java.net.SocketException;

import mkaminski.inz.socket.SocketConnectionContext;
import mkaminski.inz.socket.SocketConnectionState;

/**
 * Class that implements SocketConnectionState interface. State of communication
 * after successful connection before sending any data
 * 
 * @author Marcin Kubik <markubik@gmail.com>
 * 
 */
public class WaitingForHello implements SocketConnectionState
{
	/*
	 * Value used to set timeout while waiting on particular data
	 */
	private static final int SOCKET_TIMEOUT = 5000;

	public byte[] getDataToSend(SocketConnectionContext socketConnectionContext)
	{
		return socketConnectionContext.getMessageFormer().formProtocolName();
	}

	public void onTimeout()
	{

	}

	public void setTimeout(Socket socket)
	{
		try
		{
			socket.setSoTimeout(SOCKET_TIMEOUT);
		} catch (SocketException e)
		{
			// TODO
		}
	}

	public boolean proceedText(SocketConnectionContext socketConnectionContext, int sizeOfMessage, byte[] text)
	{
		return socketConnectionContext.getMessageDecrypter().checkHello(sizeOfMessage, text);
	}

	public SocketConnectionState setNewState()
	{
		return new WaitingForACKAfterChooseProtocol();
	}

}
