package mkaminski.inz.main;

import mkaminski.inz.crypto.CryptoManager;
import mkaminski.inz.data.DatabaseProvider;
import mkaminski.inz.dataCollector.DataCollectorProxy;
import mkaminski.inz.socket.ClientSocket;

public class Main
{

	public static void main(String[] args)
	{
//		DatabaseProvider databaseProvider = new DatabaseProvider();
//		CryptoManager.INSTANCE.readKeys();
//		ClientSocket.INSTANCE.connect(databaseProvider);
		DataCollectorProxy d = new DataCollectorProxy();
		d.get();
	}	

}
