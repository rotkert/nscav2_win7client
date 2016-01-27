package inz.mkubik.crypto;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import inz.mkamins.logger.AndroidLogger;
import inz.mkamins.logger.Level;

/**
 * Class (enum) used to maintain cryptographic in application. Static pattern
 * due to complexity
 * 
 * @author Marcin Kubik <markubik@gmail.com>
 * 
 */
public enum CryptoManager {
	INSTANCE;

	/**
	 * Object of AES module
	 */
	private AESModule aesModule;

	/**
	 * Object of RSA module
	 */
	private RSAModule rsaModule;

	/**
	 * Object of SHA module
	 */
	private SHAModule shaModule;

	/**
	 * Object used to read key from storage
	 */
	private KeyLoader keyLoader;

	/**
	 * Flag that represents, whether key was successfully read
	 */
	private boolean publicKeyFileProvided = false;

	/**
	 * Public key, used to encryption and decryption
	 */
	private PublicKey publicKey;

	/**
	 * Factory used to generate right configuration
	 */
	KeyFactory keyFactory;

	/**
	 * Reads keys from external storage, used as a constructor
	 */
	public void readKeys() {
		Security.addProvider(new BouncyCastleProvider());
		aesModule = new AESModule();
		rsaModule = new RSAModule();
		shaModule = new SHAModule();
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {

		}
		setPublicKey(readPublicKey());

	}

	/**
	 * Invoked to read key by keyLoader
	 * 
	 * @return public key
	 */
	public byte[] readPublicKey() {
		keyLoader = new KeyLoader();
		return keyLoader.readPublicKey();
	}

	/**
	 * Getter for the <code>publicKeyFileProvided</code>
	 * 
	 * @return <code>publicKeyFileProvided</code> value
	 */
	public boolean checkIfKeyProvided() {
		if (!publicKeyFileProvided) {
			setPublicKey(readPublicKey());
		}

		return publicKeyFileProvided;
	}

	/**
	 * Gets result of encryption by RSA module
	 * 
	 * @param text
	 *            text to be encrypted
	 * @return encrypted line
	 */
	public byte[] getRSAPublicKeyEncryptedLine(byte[] text) {
		return rsaModule.getEncryptedLine(text, publicKey);
	}

	/**
	 * Gets result of encryption by AES module
	 * 
	 * @param text
	 *            text to be encrypted
	 * @return encrypted line
	 */
	public byte[] getAESKeyEncryptedLine(byte[] text) {
		return aesModule.encryptLine(text);
	}

	/**
	 * Gets result of decryption by AES module
	 * 
	 * @param text
	 *            text to be decrypted
	 * @return encrypted line
	 */
	public byte[] getAESKeyDecryptedLine(byte[] text) {
		return aesModule.decryptLine(text);
	}

	/**
	 * Updates initialization vector to maintain flow of algorithm
	 * 
	 * @param array
	 *            new vector
	 */
	public void updateDecryptVektor(byte[] array) {
		aesModule.updateDecryptVector(array);
	}

	/**
	 * Updates initialization vector to maintain flow of algorithm
	 * 
	 * @param array
	 *            new vector
	 */
	public void updateEncryptVector(byte[] array) {
		aesModule.updateEncryptVector(array);
	}

	/**
	 * Setter for the RSA public key
	 * 
	 * @param bytes
	 *            new public key
	 */
	private void setPublicKey(byte[] bytes) {
		try {
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bytes);
			publicKey = keyFactory.generatePublic(publicKeySpec);
			this.publicKeyFileProvided = true;
		} catch (InvalidKeySpecException e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.FATAL,
					"Public key not set");
		} catch (NullPointerException e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.DEBUG,
					"Public key not set");
		}
	}

	/**
	 * Method used to check, if the message is right - compares hashes
	 * 
	 * @param hashedMessage
	 *            message received from remote server
	 * @param text
	 *            text to be hashed
	 * @return are the hashes the same?
	 */
	public boolean checkHashByPublicKey(byte[] hashedMessage, byte[] text) {
		return shaModule.checkHashByPublicKey(hashedMessage, text, publicKey);
	}

	/**
	 * Method used to check, if the message is right - compares hashes
	 * 
	 * @param hashedMessage
	 *            message received from remote server
	 * @param originalMessage
	 *            text to be hashed
	 * @return are the hashes the same?
	 */
	public boolean checkHash(byte[] hashedMessage, byte[] originalMessage) {
		return shaModule.checkHash(hashedMessage, originalMessage);
	}

	/**
	 * Generates hash of message
	 * 
	 * @param message
	 *            message to be hashed
	 * @return result of hashing
	 */
	public byte[] digest(byte[] message) {
		return shaModule.digest(message);
	}

	/**
	 * Getter for symmetric key
	 * 
	 * @return symmetric key
	 */
	public byte[] getSymmetricKey() {
		return aesModule.getSymmetricKey();
	}

	/**
	 * Getter for initialization vector
	 * 
	 * @return initialization vector
	 */
	public byte[] getInitializationVector() {
		return aesModule.getInitializationVector();
	}

	/**
	 * Resets initialization vector after each sent message
	 */
	public void resetIV() {
		try {
			aesModule.generateInitializationVector();
		} catch (NullPointerException e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.INFO,
					"Expected exception, proceed...");
		}
	}
}
