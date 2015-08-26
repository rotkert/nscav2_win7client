package mkaminski.inz.main;

import mkaminski.inz.data.DatabaseProvider;
import mkaminski.inz.socket.ClientSocket;

public class Main
{

	public static void main(String[] args)
	{
		DatabaseProvider databaseProvider = new DatabaseProvider();
		ClientSocket.INSTANCE.connect(databaseProvider);
	}	

}
