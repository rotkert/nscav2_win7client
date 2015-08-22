package mkaminski.inz.cache;

/**
 * Helper cache used to transport several parameters to {@link FillCacheTask}.
 * Is used whenever task is about to be started. Contains only getters, setting
 * variable is done in constructor.
 * 
 * @author Marcin Kubik <markubik@gmail.com>
 * 
 */
public class FillCacheTaskParams
{
	/**
	 * Reference to {@link SQLiteDatabase} object, used in {@link FillCacheTask}
	 * 
	 */
	private SQLiteDatabase sqliteDatabase;

	/**
	 * Name of the table, that rows should be taken from
	 */
	private String tableName;

	/**
	 * Difference between time by mobile device and NTP Server
	 */
	private int NTPTimeDifference;

	/**
	 * Difference in timezones between mobile device and NTP Server
	 */
	private int timeZoneDifference;

	/**
	 * 
	 * @param sqliteDatabase
	 *            reference to object that represents database
	 * @param bestName
	 *            name of the table that should be used
	 * @param NTPTimeDifference
	 *            difference between time on mobile device and NTP Server
	 */
	FillCacheTaskParams(SQLiteDatabase sqliteDatabase, String bestName, int NTPTimeDifference, int timeZoneDifference)
	{
		this.sqliteDatabase = sqliteDatabase;
		this.tableName = bestName;
		this.NTPTimeDifference = NTPTimeDifference;
		this.timeZoneDifference = timeZoneDifference;
	}

	/**
	 * Getter for reference to object that represents database
	 * 
	 * @return reference to database
	 */
	public SQLiteDatabase getSqliteDatabase()
	{
		return sqliteDatabase;
	}

	/**
	 * Getter for table name
	 * 
	 * @return string with name of the table
	 */
	public String getTableName()
	{
		return tableName;
	}

	/**
	 * Getter for NTPTimeDifference, used to update time of measure
	 * 
	 * @return difference between time on mobile device and on NTP Server
	 */
	public int getNTPTimeDifference()
	{
		return NTPTimeDifference;
	}

	/**
	 * Getter for time zone difference, used to updated time of measure
	 * 
	 * @return difference between time zone on mobile device and NTP Server
	 *         (Greenwich)
	 */
	public int getTimeZoneDifference()
	{
		return timeZoneDifference;
	}

}