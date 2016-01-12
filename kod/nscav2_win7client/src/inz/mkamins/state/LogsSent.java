package inz.mkamins.state;

import java.net.Socket;

import inz.socket.SocketConnectionContext;
import inz.socket.SocketConnectionState;

public class LogsSent implements SocketConnectionState
{

	@Override
	public byte[] getDataToSend(SocketConnectionContext socketConnectionContext)
	{
		return null;
	}

	@Override
	public void onTimeout()
	{
		// Nothing to do
		
	}

	@Override
	public void setTimeout(Socket socket)
	{
		// Nothing to do
		
	}

	@Override
	public boolean proceedText(SocketConnectionContext socketConnectionContext, int sizeOfMessage, byte[] text)
	{
	
		return false;
	}

	@Override
	public SocketConnectionState setNewState()
	{
		return null;
	}

}
