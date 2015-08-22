package mkaminski.inz.cache;


/**
 * Class that stores information about one particular log
 * 
 * @see DatabaseCache
 * @see DatabaseProvider
 * 
 * @author Marcin Kubik <markubik@gmail.com>
 * 
 */
public class IcingaLog {

	private String id;
	private String value;
	private String timestamp;
	private String icingaLevel;

	public IcingaLog(String id, String value, String timestamp,
			String icingaLevel) {
		this.id = id;
		this.value = value;
		this.timestamp = timestamp;
		this.icingaLevel = icingaLevel;
	}

	public String getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getIcingaLevel() {
		return icingaLevel;
	}

}
