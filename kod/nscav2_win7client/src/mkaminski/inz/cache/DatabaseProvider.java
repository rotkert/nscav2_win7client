package mkaminski.inz.cache;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Class used as a provider for data that is stored in cache. Is responsible for
 * managing, adding and removing rows (in Icinga format) and lets other modules
 * get data immediately.
 * 
 * @author Marcin Kubik <markubik@gmail.com>
 * 
 */
public class DatabaseProvider
{

	/**
	 * Cache of data that will be sent to remote server
	 */
	private DatabaseCache cache;

	/**
	 * Asynchronous task used to fill <code>inz.DatabaseCache.firstCache</code>
	 * 
	 * @see DatabaseCache#getCache()
	 */
	private FillCacheTask firstCacheTask;

	/**
	 * Difference between time that is set on the mobile device and time
	 * received from NTP Server
	 */
	private int NTPTimeDifference;

	/**
	 * Difference between time zone on the device and Greenwich
	 */
	private int timeZoneDifference;

	/**
	 * Constructor. Sets references to <code>dbHelper</code> and
	 * <code>database</code>,
	 * 
	 */
	public DatabaseProvider()
	{
		cache = new DatabaseCache();
	}

	/**
	 * Function used to invoke proceeding loading data to caches. Is responsible
	 * for filling cache with data from database and setting all necessary
	 * variables.
	 * 
	 * @warning some tasks are running asynchronously
	 */
	public void putDataToCache()
	{

		cache = new DatabaseCache();
		SQLiteDatabase database = MySQLLiteDataHelper.getInstance(StaticContext.INSTANCE.getContext())
				.getWritableDatabase();
		String bestName = MySQLLiteDataHelper.getMostFilledTable();

		cache.setMostFilledTableName(bestName);

		FillCacheTaskParams firstCacheTaskParams = new FillCacheTaskParams(database, bestName, NTPTimeDifference,
				timeZoneDifference);

		firstCacheTask = new FillCacheTask();
		firstCacheTask.proceedFillingCache(cache, firstCacheTaskParams);

	}

	/**
	 * Returns data (portion) that should be send to server
	 * 
	 * @return list of logs
	 */
	public ArrayList<IcingaLog> getDataToSend()
	{
		if (cache.getNumberOfLogsPortions() == 0)
		{
			MySQLLiteDataHelper.deleteFromDatabase(cache);
			cache = new DatabaseCache();
			putDataToCache();
		} else
			cache.remove();

		if (cache.getNumberOfLogsPortions() == 0)
		{
			cache = new DatabaseCache();
			putDataToCache();
		}
		return cache.getDataPortion();

	}

	/**
	 * Setter for difference between NTP time and current device time
	 * 
	 * @param ntp
	 *            difference
	 */
	public void setNTPDifference(int ntp)
	{
		this.NTPTimeDifference = ntp;
	}

	/**
	 * Setter for difference between time zones
	 * 
	 * @param timezone
	 *            difference
	 */
	public void setTimeZoneDifference(int timezone)
	{
		this.timeZoneDifference = timezone;
	}

	/**
	 * Getter for <code>DatabaseCache.mostFilledTableName</code> variable
	 * 
	 * @return <code>DatabaseCache.mostFilledTableName</code> variable
	 */
	public String getMostFilledTableName()
	{
		return this.cache.getMostFilledTableName();
	}
}
