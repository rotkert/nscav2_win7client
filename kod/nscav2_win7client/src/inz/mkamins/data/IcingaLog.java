package inz.mkamins.data;

public class IcingaLog
{
	private String id;
	private String value;
	private Long timestamp;
	private String icingaLevel;

	public IcingaLog(String id, String value, Long timestamp,
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

	public Long getTimestamp() {
		return timestamp;
	}

	public String getIcingaLevel() {
		return icingaLevel;
	}
}
