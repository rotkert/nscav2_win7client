package mkaminski.inz.main;

import java.util.Date;

import mkaminski.inz.crypto.CryptoManager;
import mkaminski.inz.data.CriticalSituationType;
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
//		System.out.println(System.getenv("APPDATA"));
//		databaseProvider.runPerfmon();
		databaseProvider.setReportExecutionDetails("C:\\Users\\Miko\\AppData\\Roaming\\Nscav2_client\\MIKO-KOMPUTER_20150908-000036", CriticalSituationType.TEST, new Date().getTime());
	}	

}
