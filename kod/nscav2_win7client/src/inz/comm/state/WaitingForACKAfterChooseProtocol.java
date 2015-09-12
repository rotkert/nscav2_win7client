package inz.comm.state;

import java.net.Socket;
import java.net.SocketException;

import inz.comm.socket.SocketConnectionContext;
import inz.comm.socket.SocketConnectionState;
import mkaminski.inz.errorHandling.InfoMessages;

public class WaitingForACKAfterChooseProtocol implements SocketConnectionState
{

	public static final int SOCKET_TIMEOUT = 4000;

	public byte[] getDataToSend(SocketConnectionContext socketConnectionContext)
	{
		return socketConnectionContext.getMessageFormer().formClientID();
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
		return socketConnectionContext.getMessageDecrypter().checkACKAfterChooseProtocol(sizeOfMessage, text);
	}

	public SocketConnectionState setNewState()
	{
		System.out.println(InfoMessages.NEW_STATE + "WaitingForChooseAlgorithmAndIDHash");
		return new WaitingForChooseAlgorithmAndIDHash();
	}

}
