package inz.mkamins.state;

import java.io.UnsupportedEncodingException;

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
	
	public static byte[] getTextForGraph(String serviceName) {
		try
		{
			return (serviceName + "=1").getBytes("UTF-8");
		} 
		catch (UnsupportedEncodingException e)
		{
			return (serviceName + "=1").getBytes();
		}
	}
}
