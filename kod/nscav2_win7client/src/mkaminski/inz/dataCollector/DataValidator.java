package mkaminski.inz.dataCollector;

import java.util.LinkedList;
import java.util.TimerTask;

public class DataValidator extends TimerTask
{
	private DataCollectorWrapper dataCollector;
	private LinkedList<Double> measures;
	
	public DataValidator()
	{
		super();
		measures = new LinkedList<>();
		dataCollector = new DataCollectorWrapper();
	}
	
	@Override
	public void run()
	{
		
	}
	
	private add

}
