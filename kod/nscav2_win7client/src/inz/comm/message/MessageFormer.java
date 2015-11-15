package inz.comm.message;

import java.security.SecureRandom;
import java.util.ArrayList;

import inz.comm.crypto.CryptoManager;
import inz.comm.data.DataPackProvider;
import inz.comm.data.DataProvider;
import inz.comm.data.IcingaLog;
import inz.comm.state.StateUtils;

/**
 * Class used to form data that will be send to remote server
 * 
 * @author Marcin Kubik <markubik@gmail.com>
 * 
 */
public class MessageFormer
{

	/**
	 * Forms data to establish encryption
	 * 
	 * @return data in right format
	 */
	public byte[] formEstablishEncryption()
	{
		byte[] messageCode = new byte[1];
		messageCode[0] = (byte) StateUtils.Message.ESTABLISH_ENCRYPTION.ordinal();
		byte[] sizeOfSymmetricKeyAsByteArray = StateUtils
				.getSizeAsByteArray(CryptoManager.INSTANCE.getSymmetricKey().length);
		byte[] symmetricKey = CryptoManager.INSTANCE.getSymmetricKey();
		byte[] initializationVector = CryptoManager.INSTANCE.getInitializationVector();
		byte[] totalData = StateUtils.combineByteArrays(messageCode, sizeOfSymmetricKeyAsByteArray);
		totalData = StateUtils.combineByteArrays(totalData, symmetricKey);
		totalData = StateUtils.combineByteArrays(totalData, initializationVector);
		byte[] hashOfMessage = CryptoManager.INSTANCE.digest(totalData);
		totalData = StateUtils.combineByteArrays(totalData, hashOfMessage);
		byte[] sizeOfHashMessage = StateUtils.getSizeAsByteArray(hashOfMessage.length);
		totalData = StateUtils.combineByteArrays(sizeOfHashMessage, totalData);
		totalData = CryptoManager.INSTANCE.getRSAPublicKeyEncryptedLine(totalData);
		byte[] totalSize = StateUtils.getSizeAsByteArray(totalData.length);
		totalData = StateUtils.combineByteArrays(totalSize, totalData);
		return totalData;
	}

	/**
	 * Forms data of Client ID
	 * 
	 * @return data in right format
	 */
	public byte[] formClientID(String clientId)
	{
		SecureRandom secureRandom = new SecureRandom();
		byte[] random = new byte[8];
		secureRandom.nextBytes(random);
		byte[] clientIdWithAdd = StateUtils.combineByteArrays(random,
				clientId.getBytes());
		byte[] dataToBeEncrypted = new byte[clientIdWithAdd.length + 1];
		dataToBeEncrypted[0] = (byte) StateUtils.Message.CLIENT_ID.ordinal();
		System.arraycopy(clientIdWithAdd, 0, dataToBeEncrypted, 1, clientIdWithAdd.length);
		byte[] dataToSend = CryptoManager.INSTANCE.getRSAPublicKeyEncryptedLine(dataToBeEncrypted);
		byte[] sizeAsByteArray = new byte[StateUtils.constBytesSize];
		sizeAsByteArray[3] = (byte) dataToSend.length;
		byte[] totalData = new byte[sizeAsByteArray.length + dataToSend.length];
		System.arraycopy(sizeAsByteArray, 0, totalData, 0, sizeAsByteArray.length);
		System.arraycopy(dataToSend, 0, totalData, sizeAsByteArray.length, dataToSend.length);

		return totalData;
	}

	/**
	 * Forms data for chosen algorithm
	 * 
	 * @return data in right format
	 */
	public byte[] formChosenAlgorithm(String clientId)
	{
		byte[] dataToBeEncrypted = new byte[clientId.getBytes().length + 1];
		// byte[] dataToBeEncrypted = new
		// byte[StateUtils.getClientId().getBytes().length + 1];
		dataToBeEncrypted[0] = (byte) StateUtils.Message.CHOSEN_ALGORITHM.ordinal();
		System.arraycopy(StateUtils.getChosenAlgorithm().getBytes(), 0, dataToBeEncrypted, 1,
				StateUtils.getChosenAlgorithm().getBytes().length);
		byte[] dataToSend = CryptoManager.INSTANCE.getRSAPublicKeyEncryptedLine(dataToBeEncrypted);
		byte[] sizeAsByteArray = new byte[StateUtils.constBytesSize];
		sizeAsByteArray[3] = (byte) dataToSend.length;
		byte[] totalData = new byte[sizeAsByteArray.length + dataToSend.length];
		System.arraycopy(sizeAsByteArray, 0, totalData, 0, sizeAsByteArray.length);
		System.arraycopy(dataToSend, 0, totalData, sizeAsByteArray.length, dataToSend.length);

		return totalData;
	}

	/**
	 * Forms data to choose auth module
	 * 
	 * @return data in right format
	 */
	public byte[] formChosenAuthModule()
	{
		byte[] code = new byte[1];
		code[0] = (byte) StateUtils.Message.CHOSEN_AUTH_MODULE.ordinal();
		byte[] authModule = StateUtils.getAuthModule().getBytes();
		byte[] totalData = StateUtils.combineByteArrays(code, authModule);
		byte[] hash = CryptoManager.INSTANCE.digest(totalData);
		byte[] sizeOfMessageHash = StateUtils.getSizeAsByteArray(hash.length);
		totalData = StateUtils.combineByteArrays(sizeOfMessageHash, totalData);
		totalData = StateUtils.combineByteArrays(totalData, hash);
		totalData = CryptoManager.INSTANCE.getAESKeyEncryptedLine(totalData);
		byte[] sizeOfMessage = StateUtils.getSizeAsByteArray(totalData.length);
		totalData = StateUtils.combineByteArrays(sizeOfMessage, totalData);
		return totalData;
	}

	/**
	 * Forms data for protocol name
	 * 
	 * @return data in right format
	 */
	public byte[] formProtocolName()
	{
		String dataToSend;

		String protocol = StateUtils.getProtocolName();

		dataToSend = protocol;
		byte[] sizeAsByteArray = StateUtils.getSizeAsByteArray(dataToSend.length() + 1);

		byte[] totalData = new byte[sizeAsByteArray.length + dataToSend.getBytes().length + 1];
		System.arraycopy(sizeAsByteArray, 0, totalData, 0, sizeAsByteArray.length);
		totalData[StateUtils.constBytesSize] = (byte) StateUtils.Message.REQUEST_PROTOCOL.ordinal();
		System.arraycopy(dataToSend.getBytes(), 0, totalData, sizeAsByteArray.length + 1, dataToSend.getBytes().length);
		return totalData;
	}

	/**
	 * Forms data for login
	 * 
	 * @return data in right format
	 */
	public byte[] formLogin()
	{
		try
		{
			byte[] code = new byte[2];
			code[0] = (byte) StateUtils.Message.AUTH_DATA.ordinal();
			code[1] = 1;
			byte[] login = DataProvider.getInstance().getLogin().getBytes("UTF-8");
			byte[] totalData = StateUtils.combineByteArrays(code, login);
			byte[] hash = CryptoManager.INSTANCE.digest(totalData);
			byte[] sizeOfMessageHash = StateUtils.getSizeAsByteArray(hash.length);
			totalData = StateUtils.combineByteArrays(totalData, hash);
			totalData = StateUtils.combineByteArrays(sizeOfMessageHash, totalData);
			totalData = CryptoManager.INSTANCE.getAESKeyEncryptedLine(totalData);
			byte[] sizeOfMessage = StateUtils.getSizeAsByteArray(totalData.length);
			totalData = StateUtils.combineByteArrays(sizeOfMessage, totalData);
			return totalData;
		} catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * Forms data for password
	 * 
	 * @return data in right format
	 */
	public byte[] formPassword()
	{
		try
		{
			byte[] code = new byte[2];
			code[0] = (byte) StateUtils.Message.AUTH_DATA.ordinal();
			code[1] = 3;
			byte[] password = DataProvider.getInstance().getPassword().getBytes("UTF-8");
			byte[] totalData = StateUtils.combineByteArrays(code, password);
			byte[] hash = CryptoManager.INSTANCE.digest(totalData);
			byte[] sizeOfMessageHash = StateUtils.getSizeAsByteArray(hash.length);
			totalData = StateUtils.combineByteArrays(totalData, hash);
			totalData = StateUtils.combineByteArrays(sizeOfMessageHash, totalData);
			totalData = CryptoManager.INSTANCE.getAESKeyEncryptedLine(totalData);
			byte[] sizeOfMessage = StateUtils.getSizeAsByteArray(totalData.length);
			totalData = StateUtils.combineByteArrays(sizeOfMessage, totalData);
			return totalData;
		} catch (Exception e)
		{
			// TODO
			return null;
		}
	}

	/**
	 * Forms data for log
	 * 
	 * @return data in right format
	 */
	public byte[] formLog(DataPackProvider dataPackProvider, String hostname)
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
			code[0] = (byte) StateUtils.Message.LOGS_PORTION.ordinal();

			byte[] totalData = code;

			String tablename = dataPackProvider.getIcingaService();
			String perfdataName = dataPackProvider.getPerfdataName();
			String reportName = dataPackProvider.getReportName();
			String separator = "_DiagSep_";
			byte[] hostnameBytes = hostname.getBytes("UTF-8");
			
			for (IcingaLog i : logs)
			{
				String value = separator + i.getValue() + separator + reportName + separator + reportName;
				Long timestamp = i.getTimestamp();
				String icingaLevel = i.getIcingaLevel();

				
				totalData = StateUtils.combineByteArrays(totalData, one);
				
		
				byte[] timestampAsByteArray = StateUtils.getTimeStampAsByteArray(timestamp);
				totalData = StateUtils.combineByteArrays(totalData, timestampAsByteArray);
				totalData = StateUtils.combineByteArrays(totalData, hostnameBytes);
				totalData = StateUtils.combineByteArrays(totalData, zero);
				byte[] serviceName = tablename.getBytes("UTF-8");
				totalData = StateUtils.combineByteArrays(totalData, serviceName);
				totalData = StateUtils.combineByteArrays(totalData, zero);
				byte[] state = new byte[1];
				state[0] = (byte) Integer.parseInt(icingaLevel);
				totalData = StateUtils.combineByteArrays(totalData, state);
				byte[] output = value.getBytes("UTF-8");
				byte[] textToOutput = StateUtils.combineByteArrays(serviceName, "=".getBytes("UTF-8"));
				output = StateUtils.combineByteArrays(textToOutput, output);
				totalData = StateUtils.combineByteArrays(totalData, output);
				totalData = StateUtils.combineByteArrays(totalData, pipe);
				totalData = StateUtils.combineByteArrays(totalData, StateUtils.getTextForGraph(perfdataName));
				totalData = StateUtils.combineByteArrays(totalData, "1".getBytes("UTF-8"));
				
				totalData = StateUtils.combineByteArrays(totalData, StateUtils.getTextAfterGraph(perfdataName));
				totalData = StateUtils.combineByteArrays(totalData, zero);
			}

			byte[] hash = CryptoManager.INSTANCE.digest(totalData);
			byte[] sizeOfHash = StateUtils.getSizeAsByteArray(hash.length);
			totalData = StateUtils.combineByteArrays(sizeOfHash, totalData);
			totalData = StateUtils.combineByteArrays(totalData, hash);
			totalData = CryptoManager.INSTANCE.getAESKeyEncryptedLine(totalData);
			byte[] sizeOfMessage = StateUtils.getSizeAsByteArray(totalData.length);
			totalData = StateUtils.combineByteArrays(sizeOfMessage, totalData);
			return totalData;
		} catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * Forms data for end of communication
	 * 
	 * @return data in right format
	 */
	public byte[] formEnd()
	{
		byte[] code = new byte[1];
		code[0] = (byte) StateUtils.Message.END.ordinal();

		byte[] totalData = code;
		byte[] hashOfMessage = CryptoManager.INSTANCE.digest(code);
		byte[] sizeOfHash = StateUtils.getSizeAsByteArray(hashOfMessage.length);
		totalData = StateUtils.combineByteArrays(sizeOfHash, totalData);
		totalData = StateUtils.combineByteArrays(totalData, hashOfMessage);

		totalData = CryptoManager.INSTANCE.getAESKeyEncryptedLine(totalData);
		byte[] messageSize = StateUtils.getSizeAsByteArray(totalData.length);
		totalData = StateUtils.combineByteArrays(messageSize, totalData);

		// TODO
		//ClientSocket.stopSocketThread();

		return totalData;
	}
}
