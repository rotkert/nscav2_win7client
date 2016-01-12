package inz.mkamins.state;

import inz.comm.errorHandling.InfoMessages;
import inz.socket.SocketConnectionState;
import inz.state.WaitingForACKAndHashOfLogs;

public class WaitingForACKAndHashOfLogsCst extends WaitingForACKAndHashOfLogs
{
	public SocketConnectionState setNewState() 
	{
		System.out.println(InfoMessages.NEW_STATE + "LogsSent");
		return new LogsSent();
	}
}
