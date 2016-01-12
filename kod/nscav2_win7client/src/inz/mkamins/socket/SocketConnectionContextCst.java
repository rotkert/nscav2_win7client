package inz.mkamins.socket;

import inz.message.MessageDecrypter;
import inz.mkamins.data.DataPackProvider;
import inz.mkamins.message.DatabaseProvider;
import inz.mkamins.message.MessageFormerCst;
import inz.socket.SocketConnectionContext;
import inz.socket.SocketConnectionState;
import inz.state.WaitingForHello;

public class SocketConnectionContextCst extends SocketConnectionContext
{
	private final MessageFormerCst messageFormer;
	private final DataPackProvider dataPackProvider;
	
	public SocketConnectionContextCst(DataPackProvider dataPackProvider)
	{
		super(new DatabaseProvider());
		this.dataPackProvider = dataPackProvider;
		messageFormer = new MessageFormerCst();
		messageDecrypter = new MessageDecrypter();
		this.socketConnectionState = new WaitingForHello();
	}
	
	public MessageFormerCst getMessageFormer()
	{
		return this.messageFormer;
	}

	public DataPackProvider getDataPackProvider()
	{
		return dataPackProvider;
	}
	
	public SocketConnectionState getState()
	{
		return socketConnectionState;
	}
}
