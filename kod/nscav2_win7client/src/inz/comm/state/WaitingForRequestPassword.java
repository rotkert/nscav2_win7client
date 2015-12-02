package inz.comm.state;

import java.net.Socket;

import inz.comm.errorHandling.InfoMessages;
import inz.comm.socket.SocketConnectionContext;
import inz.comm.socket.SocketConnectionState;

public class WaitingForRequestPassword implements SocketConnectionState
{

	public byte[] getDataToSend(SocketConnectionContext socketConnectionContext)
	{
		return socketConnectionContext.getMessageFormer().formPassword();
	}

	public void onTimeout()
	{
		// TODO Auto-generated method stub

	}

	public void setTimeout(Socket socket)
	{
		// TODO Auto-generated method stub

	}

	public boolean proceedText(SocketConnectionContext socketConnectionContext, int sizeOfMessage, byte[] text)
	{
		return socketConnectionContext.getMessageDecrypter().checkRequestPassword(sizeOfMessage, text);
	}

	public SocketConnectionState setNewState()
	{
		System.out.println(InfoMessages.NEW_STATE + "WaitingForWaitingForLogs");
		return new WaitingForWaitingForLogs();
	}

}
