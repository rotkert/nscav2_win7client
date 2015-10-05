package inz.comm.state;

import java.net.Socket;
import java.net.SocketException;

import inz.comm.socket.SocketConnectionContext;
import inz.comm.socket.SocketConnectionState;
import mkaminski.inz.errorHandling.InfoMessages;

public class WaitingForChooseAlgorithmAndIDHash implements SocketConnectionState
{
	/*
	 * Value used to set timeout while waiting on particular data
	 */
	private static final int SOCKET_TIMEOUT = 2000;

	public byte[] getDataToSend(SocketConnectionContext socketConnectionContext)
	{
		return socketConnectionContext.getMessageFormer().formChosenAlgorithm(socketConnectionContext.getClientId());
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
		return socketConnectionContext.getMessageDecrypter().checkChooseAlgorithmAndIDHash(sizeOfMessage, text);
	}

	public SocketConnectionState setNewState()
	{
		System.out.println(InfoMessages.NEW_STATE + "WaitingForACKAfterChooseAlgorithm");
		return new WaitingForACKAfterChooseAlgorithm();
	}
}
