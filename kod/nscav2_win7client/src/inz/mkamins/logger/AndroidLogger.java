package inz.mkamins.logger;

import inz.mkamins.commons.Logger;
import inz.mkamins.commons.Severity;

/**
 * Enum class used to log and store each important event that was proceeded in
 * application Is used by static method due to synchronization
 * 
 */
public enum AndroidLogger 
{
	INSTANCE;

	/**
	 * Function for writing to log file
	 * 
	 * @param c
	 *            Class, from which the event came
	 * @param level
	 *            Name of level of message (INFO, DEBUG, ERROR etc.)
	 * @param text
	 *            text to be written to log
	 */
	@SuppressWarnings("rawtypes")
	public void writeToLog(Class c, Level level, String text)
	{
		Logger.getInstatnce().log(Severity.valueOf(level.toString()), c.getName() + ": " + text);
	}
}
