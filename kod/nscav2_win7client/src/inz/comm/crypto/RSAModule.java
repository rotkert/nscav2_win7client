package inz.comm.crypto;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Class used to handle RSA Cryptography, used to pass symmetric key between
 * user's device and remote server
 * 
 * @author Marcin Kubik <markubik@gmail.com>
 * 
 */
public class RSAModule
{
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
	public RSAModule()
	{
		try
		{
			this.keyFactory = KeyFactory.getInstance("RSA");
			this.cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding", "BC");
		} catch (NoSuchAlgorithmException e)
		{
			// TODO
		} catch (NoSuchPaddingException e)
		{
			// TODO
		} catch (NoSuchProviderException e)
		{
			// TODO
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
	public byte[] getEncryptedLine(byte[] text, PublicKey publicKey)
	{
		try
		{
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] wynik = cipher.doFinal(text);
			return wynik;
		} catch (InvalidKeyException e)
		{
			return null;
		} catch (BadPaddingException e)
		{
			return null;
		} catch (IllegalBlockSizeException e)
		{
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
	public byte[] getDecryptedLine(byte[] text, PublicKey publicKey)
	{
		try
		{
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] wynik = cipher.doFinal(text);
			return wynik;
		} catch (InvalidKeyException e)
		{
			return null;
		} catch (BadPaddingException e)
		{
			return null;
		} catch (IllegalBlockSizeException e)
		{
			return null;
		}
	}

}
