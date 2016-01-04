package inz.config;

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
}
