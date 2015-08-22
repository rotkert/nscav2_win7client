package mkaminski.inz.crypto;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

import org.omg.CORBA.Environment;

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
		publicKeyPath = Environment.getExternalStorageDirectory() + File.separator + "DataCollector" + File.separator
				+ "Providers--Provider1.pub";

		String directString = Environment.getExternalStorageDirectory() + File.separator + "DataCollector";
		File directory = new File(directString);
		File[] files = directory.listFiles();
		for (int i = 0; i < files.length; ++i)
		{
			if (files[i].isFile())
			{
				publicKeyPath = files[i].getAbsolutePath();
				break;
			}
		}
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
