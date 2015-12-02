package inz.comm.state;

import java.net.Socket;
import java.net.SocketException;

import inz.comm.errorHandling.InfoMessages;
import inz.comm.socket.SocketConnectionContext;
import inz.comm.socket.SocketConnectionState;

public class WaitingForWaitingForLogs implements SocketConnectionState
{

	public byte[] getDataToSend(SocketConnectionContext scc)
	{
		return scc.getMessageFormer().formLog(scc.getDatabaseProvider(), scc.getHostname());
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
