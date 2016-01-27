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
	private int receivingPort;
	private String login;
	private String password;
	
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

	public int getReceivingPort()
	{
		return receivingPort;
	}
	
	@XmlElement
	public void setReceivingPort(int receivingPort)
	{
		this.receivingPort = receivingPort;
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
}
