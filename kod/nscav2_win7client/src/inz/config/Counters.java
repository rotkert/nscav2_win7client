package inz.config;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Counters
{
	private List<Counter> counter;

	public List<Counter> getCounter()
	{
		return counter;
	}
	
	@XmlElement
	public void setCounter(List<Counter> counter)
	{
		this.counter = counter;
	}
}
