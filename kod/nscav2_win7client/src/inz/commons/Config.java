package inz.commons;

public class Config
{
	private static final String APP_DATA = "APPDATA";
	private static final String APP_DIR = "\\Nscav2_client";
			
	private final String configPath = System.getenv(APP_DATA) + APP_DIR + "\\nscav2_client.properties";
	private final String reportDirectory = System.getenv(APP_DATA) + APP_DIR + "\\reports";
	private final String logDirecotry = System.getenv(APP_DATA) + APP_DIR + "\\logs";
	private final String reportFileName = "\\report.html";
	private final String newReportFileName = "\\new_report.html";
	
	private String appDirectory;
	private String ip;
	private int port;
	private String reportClientId;
	private String reportHostName;
	
	private static class Holder
	{
		static final Config INSTANCE = new Config();
	}
	
	private void setProperties(ConfigReader configReader) 
	{
		appDirectory = System.getenv(APP_DATA) + APP_DIR;
		ip = configReader.getIp();
		port = Integer.parseInt(configReader.getPort());
		reportClientId = configReader.getClientId();
		reportHostName = configReader.getHost();
	}
	
	public boolean init()
	{
		Logger.getInstatnce().log(Severity.INFO, "Configuration reading started.");
		ConfigReader configReader = new ConfigReader();
		
		if (!configReader.read(configPath))
		{
			return false;
		}
		
		setProperties(configReader);
		Logger.getInstatnce().log(Severity.INFO, "Configuration loaded properly.");
		return true;
	} 
	
	public String getReportDirectory()
	{
		return reportDirectory;
	}

	public String getLogDirecotry()
	{
		return logDirecotry;
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

	public static Config getInstance() 
	{
		return Holder.INSTANCE;
	}
}
