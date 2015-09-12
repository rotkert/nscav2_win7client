package inz.main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import inz.comm.config.Config;
import inz.comm.crypto.CryptoManager;
import inz.comm.data.DatabaseProvider;
import inz.comm.socket.ClientSocket;
import inz.data.collectors.DataValidator;
import inz.data.collectors.ValidationScheduler;
import inz.data.perfmon.PerfmonResult;

public class Main
{

	public static void main(String[] args)
	{
//		Config.init();
//		DatabaseProvider databaseProvider = new DatabaseProvider();
//		CryptoManager.INSTANCE.readKeys();
//		ClientSocket.INSTANCE.connect(databaseProvider);
//		databaseProvider.getReportText();
//		System.out.println(System.getenv("APPDATA"));
		BlockingQueue<PerfmonResult> blockingQueue = new LinkedBlockingQueue<>();
		ValidationScheduler validationScheduler = new ValidationScheduler(blockingQueue);
	}	

}
