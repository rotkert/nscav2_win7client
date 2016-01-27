package inz.mkamins.state;

import java.lang.reflect.Field;

import inz.state.StateUtils;

public class StateUtilsCst extends StateUtils
{
	public static byte[] getTimeStampAsByteArray(Long time) {
		byte[] timeAsByteArray = new byte[8];
		timeAsByteArray[0] = (byte) (time >> 56);
		timeAsByteArray[1] = (byte) (time >> 48);
		timeAsByteArray[2] = (byte) (time >> 40);
		timeAsByteArray[3] = (byte) (time >> 32);
		timeAsByteArray[4] = (byte) (time >> 24);
		timeAsByteArray[5] = (byte) (time >> 16);
		timeAsByteArray[6] = (byte) (time >> 8);
		timeAsByteArray[7] = (byte) (time >> 0);
		return timeAsByteArray;
	}
	
	@SuppressWarnings("rawtypes")
	public static byte[] getTextForGraph(String serviceName) {
		try {
			Class cls = Class.forName("inz.service."
					+ serviceName.toUpperCase() + "SERVICE");
			Field field = cls.getField("TEXT_FOR_GRAPH");
			String text = (String) field.get(null);
			byte[] value = text.getBytes("UTF-8");
			value = StateUtils.combineByteArrays(value, "=".getBytes("UTF-8"));
			return value;
		} catch (Exception e) {
			try {
				return (serviceName + "").getBytes("UTF-8");
			} catch (Exception ex) {
				return (serviceName + "").getBytes();
			}
		}
	}

	/**
	 * Used to generate text for graph in Icinga, used reflection
	 * 
	 * @param serviceName
	 *            name of service, which needs text
	 * @return text on the left site of "=" sign ("state=" by default);
	 */
	@SuppressWarnings("rawtypes")
	public static byte[] getTextAfterGraph(String serviceName) {
		try {
			Class cls = Class.forName("inz.service."
					+ serviceName.toUpperCase() + "SERVICE");
			Field field = cls.getField("TEXT_AFTER_GRAPH");
			String text = (String) field.get(null);
			byte[] value = text.getBytes("UTF-8");
			value = text.getBytes("UTF-8");
			return value;
		} catch (Exception e) {
			try {
				return ("").getBytes("UTF-8");
			} catch (Exception ex) {
				return ("").getBytes();
			}
		}
	}
}
