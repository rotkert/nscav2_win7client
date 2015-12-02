package inz.comm.state;

import java.net.Socket;
import java.net.SocketException;

import inz.comm.errorHandling.InfoMessages;
import inz.comm.socket.SocketConnectionContext;
import inz.comm.socket.SocketConnectionState;

public class WaitingForACKAndHashOfLogs implements SocketConnectionState
{

	public byte[] getDataToSend(SocketConnectionContext socketConnectionContext)
	{
//		return socketConnectionContext.getMessageFormer().formLog(socketConnectionContext.getDatabaseProvider());
		return socketConnectionContext.getMessageFormer().formEnd();
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
		return socketConnectionContext.getMessageDecrypter().checkACKAndHashOfLogs(sizeOfMessage, text);
	}

	public SocketConnectionState setNewState()
	{
		System.out.println(InfoMessages.NEW_STATE + "LogsSent");
		return new LogsSent();
	}

}
