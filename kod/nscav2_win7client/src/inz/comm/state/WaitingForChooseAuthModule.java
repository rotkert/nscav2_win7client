package inz.comm.state;

import java.net.Socket;
import java.net.SocketException;

import inz.comm.errorHandling.InfoMessages;
import inz.comm.socket.SocketConnectionContext;
import inz.comm.socket.SocketConnectionState;

public class WaitingForChooseAuthModule implements SocketConnectionState
{

	/*
	 * Value used to set timeout while waiting on particular data
	 */
	private static final int SOCKET_TIMEOUT = 2000;

	public byte[] getDataToSend(SocketConnectionContext socketConnectionContext)
	{
		return socketConnectionContext.getMessageFormer().formChosenAuthModule();
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
		return socketConnectionContext.getMessageDecrypter().checkChooseAuthModule(sizeOfMessage, text);
	}

	public SocketConnectionState setNewState()
	{
		System.out.println(InfoMessages.NEW_STATE + "WaitingForRequestLogin");
		return new WaitingForRequestLogin();
	}

}
