package inz.mkamins.commons;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.w3c.dom.css.Counter;

import inz.mkamins.config.Properties;

public class ConfigProvider
{
	private static final String APP_DATA = "APPDATA";
	private static final String APP_DIR = "\\Nscav2_client";
			
	private final String configPath = System.getenv(APP_DATA) + APP_DIR + "\\properties.xml";
	private final String reportDirectory = System.getenv(APP_DATA) + APP_DIR + "\\reports";
	private final String logDirecotry = System.getenv(APP_DATA) + APP_DIR + "\\logs";
	private final String keyPath = System.getenv(APP_DATA) + APP_DIR + "\\key\\Providers__Provider1.pub";
	private final String reportFileName = "\\report.html";
	private final String newReportFileName = "\\new_report.html";
	
	private String appDirectory;
	private String ip;
	private int port;
	private String reportClientId;
	private String reportHostName;
	private int receivingPort;
	private String login;
	private String password;
	
	private static class Holder
	{
		static final ConfigProvider INSTANCE = new ConfigProvider();
	}
	
	public String getReportDirectory()
	{
		return reportDirectory;
	}

	public String getLogDirecotry()
	{
		return logDirecotry;
	}
	
	public String getKeyPath()
	{
		return keyPath;
	}

	public String getReportFileName()
	{
		return reportFileName;
	}

	public String getNewReportFileName()
	{
		return newReportFileName;
	}

	public String getAppDirectory()
	{
		return appDirectory;
	}

	public String getIp()
	{
		return ip;
	}

	public int getPort()
	{
		return port;
	}

	public String getReportHostName()
	{
		return reportHostName;
	}

	public String getReportClientId()
	{
		return reportClientId;
	}

	public static ConfigProvider getInstance() 
	{
		return Holder.INSTANCE;
	}
	
	public void init() throws JAXBException
	{
		Logger.getInstatnce().log(Severity.INFO, "Configuration reading started.");
		Properties properties = readConfig();
		setProperties(properties);
		Logger.getInstatnce().log(Severity.INFO, "Configuration loaded properly.");
	}
	
	private Properties readConfig() throws JAXBException
	{
		File file = new File(configPath);
	
		JAXBContext jContext = JAXBContext.newInstance(Properties.class);
		
		Unmarshaller jUnmarshaller = jContext.createUnmarshaller();
		return (Properties) jUnmarshaller.unmarshal(file);
	}
	
	private void setProperties(Properties properties) 
	{
		appDirectory = System.getenv(APP_DATA) + APP_DIR;
		ip = properties.getIp();
		port = properties.getPort();
		reportClientId = properties.getReportClientId();
		reportHostName = properties.getReportHostName();
		receivingPort = properties.getReceivingPort();
		login = properties.getLogin();
		password = properties.getPassword();
	}

	public int getReceivingPort()
	{
		return receivingPort;
	}

	public String getLogin()
	{
		return login;
	}

	public String getPassword()
	{
		return password;
	}
}
