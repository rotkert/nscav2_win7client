package inz.mkamins.data;

public class IcingaLog
{
	private String value;
	private Long timestamp;
	private String icingaLevel;

	public IcingaLog(String value, Long timestamp,String icingaLevel) {
		this.value = value;
		this.timestamp = timestamp;
		this.icingaLevel = icingaLevel;
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
