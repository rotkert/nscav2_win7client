package inz.mkamins.state;

import inz.mkamins.logger.AndroidLogger;
import inz.mkamins.logger.Level;
import inz.mkamins.socket.SocketConnectionContextCst;
import inz.socket.SocketConnectionContext;
import inz.socket.SocketConnectionState;
import inz.state.WaitingForWaitingForLogs;

public class WaitingForWaitingForLogsCst extends WaitingForWaitingForLogs
{
	public byte[] getDataToSend(SocketConnectionContext socketConnectionContext)
	{
		AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.INFO, "Getting data to send");
		SocketConnectionContextCst socketConnectionContextCst = (SocketConnectionContextCst) socketConnectionContext;
		return socketConnectionContextCst.getMessageFormer().formLog(socketConnectionContextCst.getDataPackProvider());
	}
	
	public SocketConnectionState setNewState() {
		return new WaitingForACKAndHashOfLogsCst();
	}
}
