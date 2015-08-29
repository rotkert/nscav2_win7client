package mkaminski.inz.state;

import java.lang.reflect.Field;

/**
 * Class used to store important data for communication with server
 * 
 * @author Marcin Kubik <markubik@gmail.com>
 * 
 */
public class StateUtils {

	public static final int WARNING_LEVEL = 10;
	public static final int CRITICAL_LEVEL = 5;

	public enum Message {
		HELLO, REQUEST_PROTOCOL, NACK, ACK, CLIENT_ID, CHOOSE_ALGORITHM, CHOSEN_ALGORITHM, ESTABLISH_ENCRYPTION, CHOOSE_AUTH_MODULE, CHOSEN_AUTH_MODULE, AUTH_DATA, WAITING_FOR_LOGS, LOGS_PORTION, END
	}

	/**
	 * Client ID that is send to remote server and uniquely represents mobile
	 * device (and owner)
	 */
	public static final String CLIENT_ID = "Clients::Client1";

	/**
	 * AES algorithm
	 */
	public static final String CHOSEN_ALGORITHM = "AES_CBC";

	/**
	 * Version of protocol
	 */
	private static final String PROTOCOL_NAME = "v1.0";

	/**
	 * Authorization module
	 */
	private static final String AUTH_MODULE = "LoginPass";

	/**
	 * Login
	 */
	private static final String LOGIN = "LoginTestowy";

	/**
	 * Password
	 */
	private static final String PASSWORD = "PasswordTestowe";

	/**
	 * Size of bytes that stores information about size of message
	 */
	public static final int constBytesSize = 4;

	/**
	 * Getter for password
	 * 
	 * @return password
	 */
	public static String getPassword() {
		return PASSWORD;
	}

	/**
	 * Getter for login
	 * 
	 * @return login
	 */
	public static String getLogin() {
		return LOGIN;
	}

	/**
	 * Getter for authorization module
	 * 
	 * @return authorization module
	 */
	public static String getAuthModule() {
		return AUTH_MODULE;
	}

	/**
	 * Getter for algorithm
	 * 
	 * @return algorithm
	 */
	public static String getChosenAlgorithm() {
		return CHOSEN_ALGORITHM;
	}

	/**
	 * Getter for protocol name
	 * 
	 * @return protocol name
	 */
	public static String getProtocolName() {
		return PROTOCOL_NAME;
	}

	/**
	 * Getter for Client ID
	 * 
	 * @return client ID
	 */
	public static String getClientId() {
		return CLIENT_ID;
	}

	/**
	 * Used to store size as byte array
	 * 
	 * @param size
	 *            size to be stored in byte array
	 * @return size in right format
	 */
	public static byte[] getSizeAsByteArray(int size) {
		byte[] sizeAsByteArray = new byte[4];
		sizeAsByteArray[0] = (byte) (size >> 24);
		sizeAsByteArray[1] = (byte) (size >> 16);
		sizeAsByteArray[2] = (byte) (size >> 8);
		sizeAsByteArray[3] = (byte) (size >> 0);
		return sizeAsByteArray;
	}

	/**
	 * Used to store timestamp as byte array
	 * 
	 * @param size
	 *            timestamp to be stored in byte array
	 * @return timestamp in right format
	 */
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

	/**
	 * Method used to combine to byte arrays (used in communication and message
	 * forming)
	 * 
	 * @param a
	 *            first array
	 * @param b
	 *            second array
	 * @return combined arrays (a+b)
	 */
	public static byte[] combineByteArrays(byte[] a, byte[] b) {
		int length = a.length + b.length;
		byte[] result = new byte[length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}

	/**
	 * Used to check state of host
	 * 
	 * @param hostState
	 *            battery level
	 * @return state of host (2,1,0)
	 */
	public static int getStateOfHost(int hostState) {
		if (hostState < CRITICAL_LEVEL)
			return 2;
		else if (hostState < WARNING_LEVEL)
			return 1;
		else
			return 0;
	}

	/**
	 * Used to check state of host and return as a String (to the line of
	 * output)
	 * 
	 * @param hostState
	 *            battery level
	 * @return state of host (CRITICAL, WARNING, OK)
	 */
	public static byte[] getStateOfHostAsString(int hostState) {
		try {
			if (hostState < CRITICAL_LEVEL)
				return "hostState=CRITICAL".getBytes("UTF-8");
			else if (hostState < WARNING_LEVEL)
				return "hostState=WARNING".getBytes("UTF-8");
			else
				return "hostState=OK".getBytes("UTF-8");
		} catch (Exception e) {
			return "hostState=UNKNOWN".getBytes();
		}
	}

	/**
	 * Method used to create right size of pipe in host state
	 * 
	 * @param hostState
	 *            state of host
	 * @return byte representation of host state
	 */
	public static byte[] getStateOfHostAsStringWithState(int hostState) {
		try {
			if (hostState < CRITICAL_LEVEL)
				return "hostState=2".getBytes("UTF-8");
			else if (hostState < WARNING_LEVEL)
				return "hostState=1".getBytes("UTF-8");
			else
				return "hostState=0".getBytes("UTF-8");
		} catch (Exception e) {
			return "hostState=3".getBytes();
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
				return (serviceName + "_STATE=").getBytes("UTF-8");
			} catch (Exception ex) {
				return (serviceName + "_STATE=").getBytes();
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
