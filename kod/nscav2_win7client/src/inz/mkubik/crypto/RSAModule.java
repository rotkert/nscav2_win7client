package inz.mkubik.crypto;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import inz.mkamins.logger.AndroidLogger;
import inz.mkamins.logger.Level;

/**
 * Class used to handle RSA Cryptography, used to pass symmetric key between
 * user's device and remote server
 * 
 * @author Marcin Kubik <markubik@gmail.com>
 * 
 */
public class RSAModule {
	/**
	 * Factory used to configure algorithm
	 */
	@SuppressWarnings("unused")
	private KeyFactory keyFactory;

	/**
	 * Cipher used to encrypt data and check hash
	 */
	private Cipher cipher;

	/**
	 * Constructor that reads and configures keys
	 */
	public RSAModule() {
		try {
			this.keyFactory = KeyFactory.getInstance("RSA");
			this.cipher = Cipher.getInstance(
					"RSA/ECB/OAEPWithSHA-1AndMGF1Padding", "BC");
		} catch (NoSuchAlgorithmException e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.FATAL,
					"No Such Algorithm");
		} catch (NoSuchPaddingException e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.FATAL,
					"No Such Padding");
		} catch (NoSuchProviderException e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.FATAL,
					"No Such Provider");
		}
	}

	/**
	 * Used to encrypt line with "RSA"
	 * 
	 * @param s
	 *            line to be encrypted
	 * @throws Exception
	 *             when something goes wrong
	 */
	public byte[] getEncryptedLine(byte[] text, PublicKey publicKey) {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] wynik = cipher.doFinal(text);
			return wynik;
		} catch (InvalidKeyException e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.ERROR,
					"Invalid Key");
			return null;
		} catch (BadPaddingException e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.ERROR,
					"Bad Padding");
			return null;
		} catch (IllegalBlockSizeException e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.ERROR,
					"Illegal Block Size");
			return null;
		}
	}

	/**
	 * Used to decrypt line with "RSA"
	 * 
	 * @param s
	 *            line to be ecrypted
	 * @throws Exception
	 *             when something goes wrong
	 */
	public byte[] getDecryptedLine(byte[] text, PublicKey publicKey) {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] wynik = cipher.doFinal(text);
			return wynik;
		} catch (InvalidKeyException e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.ERROR,
					"Invalid Key");
			return null;
		} catch (BadPaddingException e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.ERROR,
					"Bad Padding");
			return null;
		} catch (IllegalBlockSizeException e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.ERROR,
					"Illegal Block Size");
			return null;
		}
	}

}
