package inz.mkamins.commons;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import inz.mkamins.config.Properties;

public class ConfigProvider
{
	private static final String APP_DATA = "APPDATA";
	private static final String APP_DIR = "\\Nscav2_client";
			
	private final String configPath = System.getenv(APP_DATA) + APP_DIR + "\\properties.xml";
	private final String reportDirectory = System.getenv(APP_DATA) + APP_DIR + "\\reports";
	private final String logDirecotry = System.getenv(APP_DATA) + APP_DIR + "\\logs";
	private final String keyPath = System.getenv(APP_DATA) + APP_DIR + "\\key\\Providers__Provider1.pub";
	private final String psScriptPath = System.getenv(APP_DATA) + APP_DIR + "\\extensions\\runPerfmon.ps1";
	private final String reportFileName = "\\report.html";
	private final String newReportFileName = "\\new_report.html";
	
	private String appDirectory;
	private String ip;
	private int port;
	private String reportClientId;
	private String reportHostName;
	private String serviceName;
	private String pluginOutputLevel;
	private String login;
	private String password;
	private int receivingPort;
	private int icingaServerTimeout;
	private int waitForConnection;
	private int diagnosticsDuration;
	private String diagnosticsName;
	
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
	
	public String getPsScriptPath()
	{
		return psScriptPath;
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
	
	public String getServiceName()
	{
		return serviceName;
	}
	
	public String getPluginOutputLevel()
	{
		return pluginOutputLevel;
	}
	
	public String getLogin()
	{
		return login;
	}

	public String getPassword()
	{
		return password;
	}
	
	public int getReceivingPort()
	{
		return receivingPort;
	}
	

	public int getIcingaServerTimeout()
	{
		return icingaServerTimeout;
	}


	public int getWaitForConnection()
	{
		return waitForConnection;
	}
	
	public int getDiagnosticsDuration()
	{
		return diagnosticsDuration;
	}
	
	public String getDiagnosticsName()
	{
		return diagnosticsName;
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
		serviceName = properties.getServiceName();
		pluginOutputLevel = properties.getPluginOutputLevel();
		login = properties.getLogin();
		password = properties.getPassword();
		receivingPort = properties.getReceivingPort();
		icingaServerTimeout = properties.getIcingaServerTimeout();
		waitForConnection = properties.getWaitForConnection();
		diagnosticsDuration = properties.getDiagnosticsDuration();
		diagnosticsName = properties.getDiagnosticsName();
	}

}
