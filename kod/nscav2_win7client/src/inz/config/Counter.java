package inz.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Counter
{
	private String name;
	private String path;
	private double criticalValue;
	
	public String getName()
	{
		return name;
	}
	
	@XmlAttribute
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getPath()
	{
		return path;
	}
	
	@XmlElement
	public void setPath(String path)
	{
		this.path = path;
	}

	public double getCriticalValue()
	{
		return criticalValue;
	}

	@XmlElement
	public void setCriticalValue(double criticalValue)
	{
		this.criticalValue = criticalValue;
	}
}
