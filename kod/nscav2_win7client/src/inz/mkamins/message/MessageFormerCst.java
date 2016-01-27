package inz.mkamins.message;

import java.util.ArrayList;

import inz.mkamins.commons.ConfigProvider;
import inz.mkamins.data.DataPackProvider;
import inz.mkamins.data.IcingaLog;
import inz.mkamins.state.StateUtilsCst;
import inz.mkubik.crypto.CryptoManager;
import inz.mkubik.message.MessageFormer;

public class MessageFormerCst extends MessageFormer
{
	public byte[] formLog(DataPackProvider dataPackProvider)
	{
		try
		{
			ArrayList<IcingaLog> logs = dataPackProvider.getDataToSend();

			if (logs == null)
				return formEnd();

			byte[] zero = new byte[1];
			zero[0] = 0;
			byte[] one = new byte[1];
			one[0] = 1;

			byte[] pipe = "|".getBytes("UTF-8");

			byte[] code = new byte[1];
			code[0] = (byte) StateUtilsCst.Message.LOGS_PORTION.ordinal();

			byte[] totalData = code;

			String tablename = dataPackProvider.getIcingaService();
			String perfdataName = dataPackProvider.getPerfdataName();
			String reportName = dataPackProvider.getReportName();
			String separator = "_DiagSep_";
			byte[] hostnameBytes = ConfigProvider.getInstance().getReportHostName().getBytes("UTF-8");
			
			for (IcingaLog i : logs)
			{
				String value = separator + i.getValue() + separator + reportName + separator + reportName;
				Long timestamp = i.getTimestamp();
				String icingaLevel = i.getIcingaLevel();

				
				totalData = StateUtilsCst.combineByteArrays(totalData, one);
				
		
				byte[] timestampAsByteArray = StateUtilsCst.getTimeStampAsByteArray(timestamp);
				totalData = StateUtilsCst.combineByteArrays(totalData, timestampAsByteArray);
				totalData = StateUtilsCst.combineByteArrays(totalData, hostnameBytes);
				totalData = StateUtilsCst.combineByteArrays(totalData, zero);
				byte[] serviceName = tablename.getBytes("UTF-8");
				totalData = StateUtilsCst.combineByteArrays(totalData, serviceName);
				totalData = StateUtilsCst.combineByteArrays(totalData, zero);
				byte[] state = new byte[1];
				state[0] = (byte) Integer.parseInt(icingaLevel);
				totalData = StateUtilsCst.combineByteArrays(totalData, state);
				byte[] output = value.getBytes("UTF-8");
				byte[] textToOutput = StateUtilsCst.combineByteArrays(serviceName, "=".getBytes("UTF-8"));
				output = StateUtilsCst.combineByteArrays(textToOutput, output);
				totalData = StateUtilsCst.combineByteArrays(totalData, output);
				totalData = StateUtilsCst.combineByteArrays(totalData, pipe);
				totalData = StateUtilsCst.combineByteArrays(totalData, perfdataName.getBytes());
				
				totalData = StateUtilsCst.combineByteArrays(totalData, zero);
			}

			byte[] hash = CryptoManager.INSTANCE.digest(totalData);
			byte[] sizeOfHash = StateUtilsCst.getSizeAsByteArray(hash.length);
			totalData = StateUtilsCst.combineByteArrays(sizeOfHash, totalData);
			totalData = StateUtilsCst.combineByteArrays(totalData, hash);
			totalData = CryptoManager.INSTANCE.getAESKeyEncryptedLine(totalData);
			byte[] sizeOfMessage = StateUtilsCst.getSizeAsByteArray(totalData.length);
			totalData = StateUtilsCst.combineByteArrays(sizeOfMessage, totalData);
			return totalData;
		} catch (Exception e)
		{
			return null;
		}
	}
}
