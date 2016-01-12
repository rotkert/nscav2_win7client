package inz.mkamins.message;

import inz.commons.ConfigProvider;

public class SharedPreferencesProxy
{
	private final String clientId;
	private final String hostName;
	private final String login;
	private final String password;
	
	private static class Holder
	{
		static final SharedPreferencesProxy INSTANCE = new SharedPreferencesProxy();
	}
	
	public static SharedPreferencesProxy getInstance()
	{
		return Holder.INSTANCE;
	}
	
	private SharedPreferencesProxy()
	{
		clientId = ConfigProvider.getInstance().getReportClientId();
		login = ConfigProvider.getInstance().getLogin();
		password = ConfigProvider.getInstance().getPassword();
		hostName = ConfigProvider.getInstance().getReportHostName();
	}

	public String getClientID()
	{
		return clientId;
	}

	public String getLogin()
	{
		return login;
	}

	public String getPassword()
	{
		return password;
	}

	public String getHostName()
	{
		return hostName;
	}
}
