package mkaminski.inz.dataCollector;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class DataCollectorProxy
{
	public interface CTest extends Library 
	{
		long getTotalPhysMem();
		long getPhysMemUsed();
	}
	
	public void get()
	{
		CTest cTest = (CTest) Native.loadLibrary("DataCollector", CTest.class);
		long totalPhysMem = cTest.getTotalPhysMem();
		long physMemUsed = cTest.getPhysMemUsed();
		System.out.println(totalPhysMem + " " + physMemUsed);
		double usage = (double) physMemUsed / totalPhysMem;
		System.out.println(usage * 100);
	}
}
