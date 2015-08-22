package mkaminski.inz.state;

import java.net.Socket;

import mkaminski.inz.socket.SocketConnectionContext;
import mkaminski.inz.socket.SocketConnectionState;

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
		return new WaitingForWaitingForLogs();
	}

}
