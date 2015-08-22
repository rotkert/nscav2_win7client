package mkaminski.inz.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Arrays;

/**
 * Class used for digesting and checking hash of messages
 * 
 * @author Marcin Kubik <markubik@gmail.com>
 * 
 */
public class SHAModule
{

	/**
	 * Object used to check hash
	 */
	private Signature signature;

	/**
	 * Object used to digest messages
	 */
	private MessageDigest messageDigest;

	/**
	 * Name of algorithm
	 */
	private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

	/**
	 * Name of algorithm
	 */
	private static final String DIGEST_ALGORITHM = "SHA-256";

	/**
	 * Constructor that configures SHA module
	 */
	public SHAModule()
	{
		try
		{
			signature = Signature.getInstance(SHAModule.SIGNATURE_ALGORITHM);
			messageDigest = MessageDigest.getInstance(SHAModule.DIGEST_ALGORITHM);
		} catch (NoSuchAlgorithmException e)
		{
			// TODO
		}

	}

	/**
	 * Method used to digest message
	 * 
	 * @param message
	 *            text to be digested
	 * @return digest of message
	 */
	public byte[] digest(byte[] message)
	{
		messageDigest.update(message);
		return messageDigest.digest();
	}

	/**
	 * Checks if hash or originalMessage and provided hashedMessage are the same
	 * 
	 * @param hashedMessage
	 *            hash of message (received from remote server)
	 * @param originalMessage
	 *            message, which check we would like to check
	 * @return are these two the same?
	 */
	public boolean checkHash(byte[] hashedMessage, byte[] originalMessage)
	{
		return Arrays.equals(hashedMessage, digest(originalMessage));
	}

	/**
	 * Checks if hash or message and provided hashedMessage are the same
	 * 
	 * @param hashedMessage
	 *            hash of message (received from remote server)
	 * @param message
	 *            message, which check we would like to check
	 * @return are these two the same?
	 */
	public boolean checkHashByPublicKey(byte[] hashedMessage, byte[] message, PublicKey publicKey)
	{
		try
		{
			signature.initVerify(publicKey);
			signature.update(message);
			return signature.verify(hashedMessage);
		} catch (Exception e)
		{

		}
		return false;
	}

}
