package inz.comm.crypto;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import inz.comm.errorHandling.ErrorMessages;

/**
 * Class used for read public key that is stored externally in storage
 * 
 * @author Marcin Kubik <markubik@gmail.com>
 * 
 */
public class KeyLoader
{

	/**
	 * Path to file that contains public key on storage
	 */
	String publicKeyPath;

	/**
	 * Constructor that sets the path correctly;
	 */
	public KeyLoader()
	{
		publicKeyPath = "C:\\Users\\Miko\\Desktop\\inz_praca\\key\\Providers__Provider1.pub";
	}

	/**
	 * Method used to read public key from external storage
	 * 
	 * @return public key in bytes
	 */
	public byte[] readPublicKey()
	{
		try
		{
			File publicKeyFile = new File(publicKeyPath);
			FileInputStream fis = new FileInputStream(publicKeyFile);
			DataInputStream dis = new DataInputStream(fis);
			byte[] publicKeyBytes = new byte[(int) publicKeyFile.length()];
			dis.readFully(publicKeyBytes);
			dis.close();
			return publicKeyBytes;
		} catch (FileNotFoundException e)
		{	
			System.out.println(ErrorMessages.KEY_NOT_FOUND + ". Exception message: " + e.getMessage());
			return null;
		} catch (IOException e)
		{
			return null;
		}

	}
}
