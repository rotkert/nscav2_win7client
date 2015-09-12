package inz.comm.state;

import java.net.Socket;
import java.net.SocketException;

import inz.comm.socket.SocketConnectionContext;
import inz.comm.socket.SocketConnectionState;

public class WrongMessageState implements SocketConnectionState
{

	public byte[] getDataToSend(SocketConnectionContext socketConnectionContext)
	{
		return null;
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
		return false;
	}

	public SocketConnectionState setNewState()
	{
		return null;
	}

}
