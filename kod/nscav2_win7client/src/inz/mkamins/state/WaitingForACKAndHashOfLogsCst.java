package inz.mkamins.state;

import inz.mkubik.socket.SocketConnectionState;
import inz.mkubik.state.WaitingForACKAndHashOfLogs;

public class WaitingForACKAndHashOfLogsCst extends WaitingForACKAndHashOfLogs
{
	public SocketConnectionState setNewState() 
	{
		return new LogsSent();
	}
}
