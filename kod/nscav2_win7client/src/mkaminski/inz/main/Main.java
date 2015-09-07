package mkaminski.inz.main;

import mkaminski.inz.crypto.CryptoManager;
import mkaminski.inz.data.DatabaseProvider;
import mkaminski.inz.socket.ClientSocket;

public class Main
{

	public static void main(String[] args)
	{
		DatabaseProvider databaseProvider = new DatabaseProvider();
//		CryptoManager.INSTANCE.readKeys();
//		ClientSocket.INSTANCE.connect(databaseProvider);
//		databaseProvider.getReportText();
		System.out.println(databaseProvider.runPerfmon());
	}	

}
