package inz.mkamins.state;

import inz.socket.SocketConnectionState;
import inz.state.WaitingForACKAndHashOfLogs;

public class WaitingForACKAndHashOfLogsCst extends WaitingForACKAndHashOfLogs
{
	public SocketConnectionState setNewState() 
	{
		System.out.println("LogsSent");
		return new LogsSent();
	}
}
