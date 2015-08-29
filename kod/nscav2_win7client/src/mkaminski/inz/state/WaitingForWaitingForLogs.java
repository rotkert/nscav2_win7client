package mkaminski.inz.state;

import java.net.Socket;
import java.net.SocketException;

import mkaminski.inz.errorHandling.InfoMessages;
import mkaminski.inz.socket.SocketConnectionContext;
import mkaminski.inz.socket.SocketConnectionState;

public class WaitingForWaitingForLogs implements SocketConnectionState
{

	public byte[] getDataToSend(SocketConnectionContext socketConnectionContext)
	{
		return socketConnectionContext.getMessageFormer().formLog(socketConnectionContext.getDatabaseProvider());
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
		return socketConnectionContext.getMessageDecrypter().checkWaitingForLogs(sizeOfMessage, text);
	}

	public SocketConnectionState setNewState()
	{
		System.out.println(InfoMessages.NEW_STATE + "WaitingForACKAndHashOfLogs");
		return new WaitingForACKAndHashOfLogs();
	}

}
