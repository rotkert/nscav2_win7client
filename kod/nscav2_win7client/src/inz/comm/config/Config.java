package inz.comm.config;

public final class Config
{
	private static String appDirectory;
	private static String ip;
	private static int port;
	private static String reportHostName;
	private static String eventHostName;
	private static String reportClientId;
	private static String eventClientId;
	
	private Config()
	{
		appDirectory = System.getenv("APPDATA") + "\\Nscav2_client";
		ip = "192.168.0.13";
		port = 8888;
		reportHostName = "win7";
		eventHostName = "win7";
		reportClientId = "win7::report";
		eventClientId = "win7::event";
	}
	
	public static void init()
	{
		new Config();
	}
	
	public static String getAppDirectory()
	{
		return appDirectory;
	}

	public static void setAppDirectory(String appDirectory)
	{
		Config.appDirectory = appDirectory;
	}

	public static String getIp()
	{
		return ip;
	}

	public static void setIp(String ip)
	{
		Config.ip = ip;
	}

	public static int getPort()
	{
		return port;
	}

	public static void setPort(int port)
	{
		Config.port = port;
	}
	
	public static String getReportHostName()
	{
		return reportHostName;
	}
	
	public static String getEventHostname()
	{
		return eventHostName;
	}
	
	public static String getReportClientId()
	{
		return reportClientId;
	}
	
	public static String getEventClientId()
	{
		return eventClientId;
	}
}


