package mkaminski.inz.crypto;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
		publicKeyPath = "C:\\Users\\Miko\\Desktop\\inz - praca\\key";
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
			return null;
		} catch (IOException e)
		{
			return null;
		}

	}
}
