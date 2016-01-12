package inz.crypto;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import inz.commons.ConfigProvider;
import inz.mkamins.logger.AndroidLogger;
import inz.mkamins.logger.Level;

/**
 * Class used for read public key that is stored externally in storage
 * 
 * @author Marcin Kubik <markubik@gmail.com>
 * 
 */
public class KeyLoader {

	/**
	 * Path to file that contains public key on storage
	 */
	String publicKeyPath;

	/**
	 * Constructor that sets the path correctly;
	 */
	public KeyLoader() {
		publicKeyPath = ConfigProvider.getInstance().getKeyPath();
	}

	/**
	 * Method used to read public key from external storage
	 * 
	 * @return public key in bytes
	 */
	public byte[] readPublicKey() {
		try {
			File publicKeyFile = new File(publicKeyPath);
			FileInputStream fis = new FileInputStream(publicKeyFile);
			DataInputStream dis = new DataInputStream(fis);
			byte[] publicKeyBytes = new byte[(int) publicKeyFile.length()];
			dis.readFully(publicKeyBytes);
			dis.close();
			return publicKeyBytes;
		} catch (FileNotFoundException e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.ERROR,
					"File not found");
			return null;
		} catch (IOException e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.ERROR,
					"IO Exception");
			return null;
		}

	}
}
