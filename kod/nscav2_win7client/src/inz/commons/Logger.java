package inz.commons;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger
{
	private final File logFile; 
	private static class Holder
	{
		static final Logger INSTANCE = new Logger();
	}
	
	public static Logger getInstatnce()
	{
		return Holder.INSTANCE;
	}
	
	
	private Logger()
	{
		String dateString = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
		logFile = new File(ConfigProvider.getInstance().getLogDirecotry() + "\\logs_" + dateString + ".txt");
		
		try
		{
			logFile.createNewFile();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized void log(Severity severity, String message)
	{
		try
		{
			PrintWriter writer = new PrintWriter(new FileWriter(logFile, true));
			String dateString = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
			writer.println("[" + dateString + "] " + severity.toString() + ": " + message);
			writer.close();
		} 
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
