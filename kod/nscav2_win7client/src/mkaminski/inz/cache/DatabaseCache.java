package mkaminski.inz.cache;

import inz.datacollector.MySQLLiteDataHelper;
import inz.sharedpref.SharedPreferencesProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for holding information from database
 * <p>
 * This class is a storage for data from SQLiteDatabase that is used for Socket
 * Communication classes
 * <p>
 * 
 * @see DatabaseProvider
 * 
 * @author Marcin Kubik <markubik@gmail.com>
 * 
 */
public class DatabaseCache {

	/**
	 * List of list of <code>IcingaLog</code>, used to send data to remote
	 * server in packages
	 * 
	 * @see IcingaLog
	 */
	private ArrayList<ArrayList<IcingaLog>> logs = new ArrayList<ArrayList<IcingaLog>>();

	/**
	 * Number of logs that are sent in one package
	 */
	private static int NUMBER_OF_LOGS_IN_PACKAGE = 2;

	/**
	 * List of id's that are store in database, used when app should delete rows
	 * with this id's from database.
	 */
	private List<String> listIds = new ArrayList<String>();

	/**
	 * Holds the name of the most filled table (the one that gives rows to send)
	 */
	private String mostFilledTableName;

	public DatabaseCache() {
		int numberOfLogs = SharedPreferencesProxy.getInstance()
				.getLogsInPackage();
		if (numberOfLogs < 2)
			numberOfLogs = 2;
		if (numberOfLogs > 50)
			numberOfLogs = 50;
		DatabaseCache.NUMBER_OF_LOGS_IN_PACKAGE = numberOfLogs;
	}

	/**
	 * Getter for the <code>mostFilledTableName</code> variable
	 * 
	 * @return value of <code>mostFilledTableName</code>
	 */
	public String getMostFilledTableName() {
		return mostFilledTableName;
	}

	/**
	 * Setter for the <code>mostFilledTableName</code> variable
	 * 
	 * @param mostFilledTableName
	 *            value of <code>mostFilledTableName</code>
	 */
	public void setMostFilledTableName(String mostFilledTableName) {
		this.mostFilledTableName = mostFilledTableName;
	}

	/**
	 * Used to update list of id's that are read from database
	 * 
	 * @param i
	 *            id as the value from column id in specific table
	 */
	public void addToListIds(String i) {
		listIds.add(i);
	}

	/**
	 * Method used to get all id's of the rows that were sent to server
	 * 
	 * @return list of ids
	 */
	public List<String> getListIds() {
		return listIds;
	}

	/**
	 * Method used to put another log (that was got from database)
	 * 
	 * @param log
	 *            representation of state of some service
	 */
	synchronized public void putToCache(IcingaLog log) {
		if (logs.size() == 0) {
			ArrayList<IcingaLog> newList = new ArrayList<IcingaLog>();
			newList.add(log);
			logs.add(newList);
			return;
		}
		for (ArrayList<IcingaLog> i : logs) {
			if (i.size() < NUMBER_OF_LOGS_IN_PACKAGE) {
				i.add(log);
				return;
			} else
				continue;
		}
		ArrayList<IcingaLog> newList = new ArrayList<IcingaLog>();
		newList.add(log);
		logs.add(newList);
		return;

	}

	/**
	 * Used to get information about how many portions of logs are still
	 * available to sending
	 * 
	 * @return number of portions
	 */
	public int getNumberOfLogsPortions() {
		return logs.size();
	}

	/**
	 * Get one portion of logs (that contains
	 * <code>NUMBER_OF_LOGS_IN_PACKAGE</code> logs
	 * 
	 * @return list of logs
	 */
	public ArrayList<IcingaLog> getDataPortion() {
		if (logs.size() != 0) {
			ArrayList<IcingaLog> logsToSend = logs.get(0);
			for (IcingaLog i : logsToSend) {
				listIds.add(i.getId());
			}

			return logsToSend;
		} else {
			return null;
		}
	}

	/**
	 * Removes log that were successfully received by remote server
	 */
	public void remove() {
		MySQLLiteDataHelper.deleteFromDatabase(this);
		logs.remove(0);
		listIds.clear();
	}

}
