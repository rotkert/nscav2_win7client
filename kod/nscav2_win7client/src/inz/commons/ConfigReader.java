package inz.commons;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class ConfigReader
{
	private String ip;
	private String port;
	private String clientId;
	private String host;
	private String service;
	
	public boolean read(String configPath)
	{
		File configFile = new File(configPath);
		
		try 
		{
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);
			
			ArrayList<String> emptyProperites = new ArrayList<>();
			
			port = props.getProperty("port");
			if (port == null || port.equals(""))
			{
				emptyProperites.add("port");
			}
			
			ip = props.getProperty("ip");
			if (ip == null || ip.equals(""))
			{
				emptyProperites.add("ip");
			}
			
			clientId = props.getProperty("clientId");
			if (clientId == null || clientId.equals(""))
			{
				emptyProperites.add("clientId");
			}
			
			host = props.getProperty("host");
			if (host == null || host.equals(""))
			{
				emptyProperites.add("host");
			}
			
			service = props.getProperty("service");
			if (service == null || service.equals(""))
			{
				emptyProperites.add("service");
			}
			
			if(emptyProperites.size() > 0)
			{
				Logger.getInstatnce().log(Severity.ERROR, "Following properties are not specified: " + String.join(", ", emptyProperites) + ".");
				return false;
			}
			
			reader.close();
		}
		catch(FileNotFoundException e)
		{
			Logger.getInstatnce().log(Severity.ERROR, "Config file not found");
			return false;
		}
		catch(IOException e)
		{
			Logger.getInstatnce().log(Severity.ERROR, "Error while reading configuration");
			return false;
		}
		
		return true;
	}

	public String getIp()
	{
		return ip;
	}

	public String getClientId()
	{
		return clientId;
	}

	public String getHost()
	{
		return host;
	}

	public String getService()
	{
		return service;
	}
	
	public String getPort()
	{
		return port;
	}
}
