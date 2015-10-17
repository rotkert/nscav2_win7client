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
		DataCollectorNat memoryUsageCollector = (DataCollectorNat) Native.loadLibrary("DataCollector", DataCollectorNat.class);
		long totalPhysMem = memoryUsageCollector.getTotalPhysMem();
		long physMemUsed = memoryUsageCollector.getPhysMemUsed();
		double usage = (double) physMemUsed / totalPhysMem;
		return usage * 100;
	}
}
