package inz.mkamins.state;

import inz.socket.SocketConnectionState;
import inz.state.WaitingForACKAndHashOfLogs;

public class WaitingForACKAndHashOfLogsCst extends WaitingForACKAndHashOfLogs
{
	public SocketConnectionState setNewState() 
	{
		return new LogsSent();
	}
}
