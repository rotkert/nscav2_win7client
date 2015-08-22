package mkaminski.inz.cache;

import java.awt.Cursor;

/**
 * Class used as a task, that has to fill one of the caches with data from
 * tables in database. Uses object of class {@link FillCacheTaskParams} to get
 * info about what should be done.
 * 
 * @see FillCacheTaskParams
 * @author Marcin Kubik <markubik@gmail.com>
 * 
 */
public class FillCacheTask
{

	/**
	 * Method used to fill <code>firstCache</code> with data from database
	 * 
	 * @param myTaskParams
	 *            object with information used to query data
	 */
	public void proceedFillingCache(DatabaseCache cache, FillCacheTaskParams myTaskParams)
	{
		String bestName = myTaskParams.getTableName();
		SQLiteDatabase database = myTaskParams.getSqliteDatabase();
		int NTPTimeDifference = myTaskParams.getNTPTimeDifference();
		int timeZoneDifference = myTaskParams.getTimeZoneDifference();

		cache.setMostFilledTableName(bestName);

		Cursor bestCursor = database.query(bestName, TableProxy.getInstance().getAllColumns(), null, null, null, null,
				null);

		bestCursor.moveToFirst();

		while (!bestCursor.isAfterLast())
		{
			String id = bestCursor.getString(0);
			String value = bestCursor.getString(1);
			String timestamp = bestCursor.getString(2);
			timestamp = Utils.switchDateToTimestamp(timestamp, NTPTimeDifference, timeZoneDifference);
			String icingaLevel = bestCursor.getString(3);
			IcingaLog log = new IcingaLog(id, value, timestamp, icingaLevel);
			cache.putToCache(log);
			bestCursor.moveToNext();
		}
	}

}
