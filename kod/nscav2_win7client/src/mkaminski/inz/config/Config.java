package mkaminski.inz.config;

public final class Config
{
	private static String appDirectory;
	private static String ip;
	private static int port;
	
	private Config()
	{
		appDirectory = System.getenv("APPDATA") + "\\Nscav2_client";
		ip = "192.168.0.13";
		port = 8888;
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
}
