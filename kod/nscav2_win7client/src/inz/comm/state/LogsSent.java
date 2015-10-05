package inz.comm.state;

import java.net.Socket;

import inz.comm.socket.SocketConnectionContext;
import inz.comm.socket.SocketConnectionState;

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
