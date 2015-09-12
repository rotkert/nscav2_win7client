package inz.comm.state;

import java.net.Socket;
import java.net.SocketException;

import inz.comm.socket.SocketConnectionContext;
import inz.comm.socket.SocketConnectionState;
import mkaminski.inz.errorHandling.InfoMessages;

public class WaitingForRequestLogin implements SocketConnectionState
{

	public byte[] getDataToSend(SocketConnectionContext socketConnectionContext)
	{
		return socketConnectionContext.getMessageFormer().formLogin();
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
		return socketConnectionContext.getMessageDecrypter().checkRequestLogin(sizeOfMessage, text);
	}

	public SocketConnectionState setNewState()
	{
		System.out.println(InfoMessages.NEW_STATE + "WaitingForRequestPassword");
		return new WaitingForRequestPassword();
	}

}
