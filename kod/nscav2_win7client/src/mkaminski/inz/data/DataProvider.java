package mkaminski.inz.data;

public class DataProvider
{
	private static volatile DataProvider instance = null;

	/** */
	private final String clientId = "win7::win7";
	/** */
	private final String login = "win7";
	/** */
	private final String password = "win7";
	/** */
	private final String hostName = "win7";

	public static DataProvider getInstance()
	{
		if (instance == null)
		{
			synchronized (DataProvider.class)
			{
				if (instance == null)
				{
					instance = new DataProvider();
				}
			}
		}
		return instance;
	}

	public String getClientId()
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
