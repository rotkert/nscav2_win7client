package inz.mkamins.logger;

import inz.mkamins.commons.Logger;
import inz.mkamins.commons.Severity;

public enum AndroidLogger 
{
	INSTANCE;

	@SuppressWarnings("rawtypes")
	public void writeToLog(Class c, Level level, String text)
	{
		Logger.getInstatnce().log(Severity.valueOf(level.toString()), c.getName() + ": " + text);
	}
}
