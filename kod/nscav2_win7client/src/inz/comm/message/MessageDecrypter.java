package inz.comm.message;

import inz.comm.crypto.CryptoManager;
import inz.comm.state.StateUtils;

/**
 * Class used to decrypt and check data that are received from server
 * 
 * @author Marcin Kubik <markubik@gmail.com>
 * 
 */
public class MessageDecrypter
{

	/**
	 * Checks HELLO message
	 * 
	 * @param sizeOfMessage
	 *            size of received message
	 * @param text
	 *            text to be checks
	 * @return is this text ok?
	 */
	public boolean checkHello(int sizeOfMessage, byte[] text)
	{
		try
		{
			if (sizeOfMessage != 1)
			{
				return false;
			}

			String response = "";
			for (int i = StateUtils.constBytesSize; i < StateUtils.constBytesSize + sizeOfMessage; ++i)
			{
				response += text[i];
			}
			if (response.trim().equals("" + StateUtils.Message.HELLO.ordinal()))
			{
				return true;
			} else
			{
				return false;
			}
		} catch (NullPointerException e)
		{
			// TODO
			//ClientSocket.stopSocketThread();
			return false;
		}
	}

	/**
	 * Checks ACK message
	 * 
	 * @param sizeOfMessage
	 *            size of received message
	 * @param text
	 *            text to be checks
	 * @return is this text ok?
	 */
	public boolean checkACKAfterChooseAlgorithm(int sizeOfMessage, byte[] text)
	{
		try
		{
			if (sizeOfMessage != 1)
			{
				return false;
			}
			String response = "";
			for (int i = StateUtils.constBytesSize; i < StateUtils.constBytesSize + sizeOfMessage; ++i)
			{
				response += text[i];
			}
			if (response.trim().equals("" + StateUtils.Message.ACK.ordinal()))
			{
				return true;
			} else
			{
				return false;
			}
		} catch (NullPointerException e)
		{
			// TODO 
			//ClientSocket.stopSocketThread();
			return false;
		}
	}

	/**
	 * Checks ACK message
	 * 
	 * @param sizeOfMessage
	 *            size of received message
	 * @param text
	 *            text to be checks
	 * @return is this text ok?
	 */
	public boolean checkACKAfterChooseProtocol(int sizeOfMessage, byte[] text)
	{
		try
		{
			if (sizeOfMessage != 1)
			{
				return false;
			}
			String response = "";
			for (int i = StateUtils.constBytesSize; i < StateUtils.constBytesSize + sizeOfMessage; ++i)
			{
				response += text[i];
			}
			if (response.trim().equals("" + StateUtils.Message.ACK.ordinal()))
			{
				return true;
			} else
			{
				return false;
			}
		} catch (NullPointerException e)
		{
			// TODO 
			//ClientSocket.stopSocketThread();
			return false;
		}
	}

	/**
	 * Checks REQUEST LOGIN message
	 * 
	 * @param sizeOfMessage
	 *            size of received message
	 * @param text
	 *            text to be checks
	 * @return is this text ok?
	 */
	public boolean checkRequestLogin(int sizeOfMessage, byte[] text)
	{
		try
		{
			byte[] byteSizeOfMessage = new byte[StateUtils.constBytesSize];
			System.arraycopy(text, 0, byteSizeOfMessage, 0, StateUtils.constBytesSize);

			byte[] encryptedMessage = new byte[sizeOfMessage];
			System.arraycopy(text, StateUtils.constBytesSize, encryptedMessage, 0, sizeOfMessage);
			byte[] decryptedMessage = CryptoManager.INSTANCE.getAESKeyDecryptedLine(encryptedMessage);
			byte[] hash = new byte[32];
			System.arraycopy(decryptedMessage, 6, hash, 0, 32);
			byte[] code = new byte[2];

			code[0] = (byte) StateUtils.Message.AUTH_DATA.ordinal();
			code[1] = 0;
			return CryptoManager.INSTANCE.checkHash(hash, code);
		} catch (NullPointerException e)
		{
			// TODO 
			//ClientSocket.stopSocketThread();
			return false;
		}
	}

	/**
	 * Checks CHOOSE ALGORITHM message
	 * 
	 * @param sizeOfMessage
	 *            size of received message
	 * @param text
	 *            text to be checks
	 * @return is this text ok?
	 */
	public boolean checkChooseAlgorithmAndIDHash(int sizeOfMessage, byte[] text)
	{
		try
		{
			int code = (int) text[StateUtils.constBytesSize];
			if (code != StateUtils.Message.CHOOSE_ALGORITHM.ordinal())
			{
				return false;
			}
			byte[] hash = new byte[sizeOfMessage - 1];
			System.arraycopy(text, StateUtils.constBytesSize + 1, hash, 0, sizeOfMessage - 1);

			return true;
		} catch (NullPointerException e)
		{
			// TODO 
			//ClientSocket.stopSocketThread();
			return false;
		}
	}

	/**
	 * Checks CHOOSE AUTH MODULE message
	 * 
	 * @param sizeOfMessage
	 *            size of received message
	 * @param text
	 *            text to be checks
	 * @return is this text ok?
	 */
	public boolean checkChooseAuthModule(int sizeOfMessage, byte[] text)
	{
		try
		{
			byte[] byteSizeOfMessage = new byte[StateUtils.constBytesSize];
			System.arraycopy(text, 0, byteSizeOfMessage, 0, StateUtils.constBytesSize);

			byte[] encryptedMessage = new byte[sizeOfMessage];
			System.arraycopy(text, StateUtils.constBytesSize, encryptedMessage, 0, sizeOfMessage);

			byte[] decryptedMessage = CryptoManager.INSTANCE.getAESKeyDecryptedLine(encryptedMessage);

			byte[] hash = new byte[32];
			System.arraycopy(decryptedMessage, 5, hash, 0, 32);
			byte[] code = new byte[1];
			code[0] = (byte) StateUtils.Message.CHOOSE_AUTH_MODULE.ordinal();
			return CryptoManager.INSTANCE.checkHash(hash, code);
		} catch (NullPointerException e)
		{
			// TODO 
			//ClientSocket.stopSocketThread();
			return false;
		}
	}

	/**
	 * Checks REQUEST MESSAGE message
	 * 
	 * @param sizeOfMessage
	 *            size of received message
	 * @param text
	 *            text to be checks
	 * @return is this text ok?
	 */
	public boolean checkRequestPassword(int sizeOfMessage, byte[] text)
	{
		try
		{
			byte[] byteSizeOfMessage = new byte[StateUtils.constBytesSize];
			System.arraycopy(text, 0, byteSizeOfMessage, 0, StateUtils.constBytesSize);

			byte[] encryptedMessage = new byte[sizeOfMessage];
			System.arraycopy(text, StateUtils.constBytesSize, encryptedMessage, 0, sizeOfMessage);
			byte[] decryptedMessage = CryptoManager.INSTANCE.getAESKeyDecryptedLine(encryptedMessage);
			byte[] hash = new byte[32];
			byte[] data = new byte[2];
			System.arraycopy(decryptedMessage, 4, data, 0, 2);
			System.arraycopy(decryptedMessage, 6, hash, 0, 32);
			return CryptoManager.INSTANCE.checkHash(hash, data);
		} catch (NullPointerException e)
		{
			// TODO 
			//ClientSocket.stopSocketThread();
			return false;
		}
	}

	/**
	 * Checks WAITING FOR LOG message
	 * 
	 * @param sizeOfMessage
	 *            size of received message
	 * @param text
	 *            text to be checks
	 * @return is this text ok?
	 */
	public boolean checkWaitingForLogs(int sizeOfMessage, byte[] text)
	{
		try
		{
			byte[] byteSizeOfMessage = new byte[StateUtils.constBytesSize];
			System.arraycopy(text, 0, byteSizeOfMessage, 0, StateUtils.constBytesSize);

			byte[] encryptedMessage = new byte[sizeOfMessage];
			System.arraycopy(text, StateUtils.constBytesSize, encryptedMessage, 0, sizeOfMessage);
			byte[] decryptedMessage = CryptoManager.INSTANCE.getAESKeyDecryptedLine(encryptedMessage);
			byte[] hash = new byte[32];
			byte[] data = new byte[1];
			System.arraycopy(decryptedMessage, 4, data, 0, 1);
			System.arraycopy(decryptedMessage, 5, hash, 0, 32);
			return CryptoManager.INSTANCE.checkHash(hash, data);
		} catch (NullPointerException e)
		{
			// TODO
			//ClientSocket.stopSocketThread();
			return false;
		}
	}

	/**
	 * Checks ACK message
	 * 
	 * @param sizeOfMessage
	 *            size of received message
	 * @param text
	 *            text to be checks
	 * @return is this text ok?
	 */
	public boolean checkACKAndHashOfLogs(int sizeOfMessage, byte[] text)
	{
		try
		{
			byte[] size = new byte[4];
			System.arraycopy(text, 0, size, 0, 4);
			byte[] encryptedMessage = new byte[sizeOfMessage];
			System.arraycopy(text, 4, encryptedMessage, 0, sizeOfMessage);

			byte[] decryptedMessage = CryptoManager.INSTANCE.getAESKeyDecryptedLine(encryptedMessage);

			byte state = decryptedMessage[4];

			return (state == (byte) StateUtils.Message.ACK.ordinal());
		} catch (NullPointerException e)
		{
			// TODO
			//ClientSocket.stopSocketThread();
			return false;
		}
	}
}
