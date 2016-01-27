package inz.mkamins.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class Properties
{
	private String ip;
	private int port;
	private String reportClientId;
	private String reportHostName;
	private String serviceName;
	private String pluginOutputLevel;
	private String login;
	private String password;
	private int receivingPort;
	
	public String getIp()
	{
		return ip;
	}
	
	@XmlElement
	public void setIp(String ip)
	{
		this.ip = ip;
	}
	
	public int getPort()
	{
		return port;
	}
	
	@XmlElement
	public void setPort(int port)
	{
		this.port = port;
	}
	
	public String getReportClientId()
	{
		return reportClientId;
	}
	
	@XmlElement
	public void setReportClientId(String reportClientId)
	{
		this.reportClientId = reportClientId;
	}
	
	public String getReportHostName()
	{
		return reportHostName;
	}
	
	@XmlElement
	public void setReportHostName(String reportHostName)
	{
		this.reportHostName = reportHostName;
	}
	
	public String getServiceName()
	{
		return serviceName;
	}
	
	@XmlElement
	public void setServiceName(String serviceName)
	{
		this.serviceName = serviceName;
	}

	public String getPluginOutputLevel()
	{
		return pluginOutputLevel;
	}
	
	@XmlElement
	public void setPluginOutputLevel(String pluginOutputLevel)
	{
		this.pluginOutputLevel = pluginOutputLevel;
	}

	public String getLogin()
	{
		return login;
	}
	
	@XmlElement
	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getPassword()
	{
		return password;
	}

	@XmlElement
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public int getReceivingPort()
	{
		return receivingPort;
	}
	
	@XmlElement
	public void setReceivingPort(int receivingPort)
	{
		this.receivingPort = receivingPort;
	}
}
