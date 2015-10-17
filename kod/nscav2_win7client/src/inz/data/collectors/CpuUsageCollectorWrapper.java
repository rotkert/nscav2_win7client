package inz.data.collectors;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class CpuUsageCollectorWrapper
{
	public interface CpuUsageCollectorNat extends Library 
	{
		double getCpuUsage();
	}
	
	public double getCpuUsage()
	{
		CpuUsageCollectorNat cpuUsageCollector = (CpuUsageCollectorNat) Native.loadLibrary("CpuUsageCollector", CpuUsageCollectorNat.class);
		return cpuUsageCollector.getCpuUsage();
	}
}
