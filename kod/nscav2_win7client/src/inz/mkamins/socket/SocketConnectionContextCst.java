package inz.mkamins.socket;

import inz.mkamins.data.DataPackProvider;
import inz.mkamins.message.DatabaseProvider;
import inz.mkamins.message.MessageFormerCst;
import inz.mkubik.message.MessageDecrypter;
import inz.mkubik.socket.SocketConnectionContext;
import inz.mkubik.socket.SocketConnectionState;
import inz.mkubik.state.WaitingForHello;

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
