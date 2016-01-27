package inz.mkubik.crypto;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import inz.mkamins.logger.AndroidLogger;
import inz.mkamins.logger.Level;
import inz.mkubik.exception.IcingaException;

/**
 * Class used for symmetric encryption and decryption of data that is sent to
 * remote server
 * 
 * @author Marcin Kubik <markubik@gmail.com>
 * 
 */
public class AESModule {

	/**
	 * Specification of symmetric key (based on receivedKey)
	 */
	private SecretKey symmetricKey;

	private static final int keySize = 128;

	/**
	 * Initialization Vector for AES Cryptographic
	 */
	private IvParameterSpec initializationVector;

	/**
	 * Size of initalization vector (IV) in kBs
	 */
	private static final int initializationVectorSize = 16;

	/**
	 * Object used to encrypt data
	 */
	private Cipher cipherEncrypt;
	
	/**
	 * Object used to decrypt data
	 */
	private Cipher cipherDecrypt;

	/**
	 * Constructor of AESModule, sets instance of symmetric algorithm
	 * for cipher
	 */
	public AESModule() {
		try {
			cipherEncrypt = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
			cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
			generateKey();
			generateInitializationVector();
			cipherEncrypt.init(Cipher.ENCRYPT_MODE, symmetricKey,
					initializationVector);
			cipherDecrypt.init(Cipher.DECRYPT_MODE, symmetricKey,
					initializationVector);
		} catch (Exception e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.ERROR,
					"AES Module not initialized properly");
			throw new IcingaException("AES Module Cipher initialization error");
		}
	}

	/**
	 * Gets line of text and encrypts it using key and vector
	 * 
	 * @param line
	 *            text that should be encrypted
	 * @return encrypted text
	 */
	public byte[] encryptLine(byte[] line) {
		try {
			byte[] returnLine = cipherEncrypt.doFinal(line);
			byte[] newIV = new byte[16];
			System.arraycopy(returnLine, returnLine.length - 16, newIV, 0, 16);
			updateEncryptVector(newIV);
			return returnLine;
		} catch (Exception e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.ERROR,
					"Problem with encrypting line: " + line);
			return null;
		}
	}

	/**
	 * Gets line of encrypted text and resolving it with key and vector
	 * 
	 * @param line
	 *            line of encrypted text
	 * @return readable line
	 */
	public byte[] decryptLine(byte[] line) {
		try {
			byte[] returnLine = cipherDecrypt.doFinal(line);
			byte[] newIV = new byte[16];
			System.arraycopy(line, line.length - 16, newIV, 0, 16);
			CryptoManager.INSTANCE.updateDecryptVektor(newIV);
			return returnLine;
		} catch (Exception e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.ERROR,
					"Problem with decrypting line: " + new String(line));
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Updates encrypt initialization vector after encryption (to maintain flow of algorithm)
	 * @param array new initialization vector
	 */
	public void updateEncryptVector(byte[] array) {
		try {
			cipherEncrypt.init(Cipher.ENCRYPT_MODE, this.symmetricKey,
					new IvParameterSpec(array));
		} catch (Exception e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.ERROR,
					"Error with init");
		}
	}

	/**
	 * Updates decrypt initialization vector after encryption (to maintain flow of algorithm)
	 * @param array new initialization vector
	 */
	public void updateDecryptVector(byte[] array) {
		try {
			cipherDecrypt.init(Cipher.DECRYPT_MODE, this.symmetricKey,
					new IvParameterSpec(array));
		} catch (Exception e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.ERROR,
					"Error with init");
		}
	}

	/**
	 * Getter for AES Symmetric Key
	 * @return symmetric key
	 */
	public byte[] getSymmetricKey() {
		return this.symmetricKey.getEncoded();
	}

	/**
	 * Getter for AES Initialization Vector
	 * @return initialization vector
	 */
	public byte[] getInitializationVector() {
		return this.initializationVector.getIV();
	}

	/**
	 * Generates key for symmetric algorithm
	 */
	public void generateKey() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES", "BC");
			keyGen.init(keySize);
			this.symmetricKey = keyGen.generateKey();

		} catch (NoSuchProviderException e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.ERROR,
					"No Such Provider");
		} catch (NoSuchAlgorithmException e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.ERROR,
					"No Such Algorithm");
		}
	}

	/**
	 * Generates initialization vector for symmetric algorithm
	 */
	public void generateInitializationVector() {
		SecureRandom random = new SecureRandom();
		byte iv[] = new byte[initializationVectorSize];
		random.nextBytes(iv);
		this.initializationVector = new IvParameterSpec(iv);
		try {
			cipherEncrypt.init(Cipher.ENCRYPT_MODE, symmetricKey,
					initializationVector);
			cipherDecrypt.init(Cipher.DECRYPT_MODE, symmetricKey,
					initializationVector);
		} catch (Exception e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.ERROR,
					"ERROR");
		}
	}

}
