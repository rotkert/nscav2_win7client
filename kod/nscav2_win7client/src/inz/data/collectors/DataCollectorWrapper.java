package inz.data.collectors;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class DataCollectorWrapper
{
	public interface DataCollectorNat extends Library 
	{
		long getTotalPhysMem();
		long getPhysMemUsed();
	}
	
	public double getPhysMemUsage()
	{
		DataCollectorNat cTest = (DataCollectorNat) Native.loadLibrary("DataCollector", DataCollectorNat.class);
		long totalPhysMem = cTest.getTotalPhysMem();
		long physMemUsed = cTest.getPhysMemUsed();
		double usage = (double) physMemUsed / totalPhysMem;
		return usage * 100;
	}
}
