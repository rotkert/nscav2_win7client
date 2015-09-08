package mkaminski.inz.data;

public enum CriticalSituationType
{
	TEST ("Test");
	private final String msg;
	
	private CriticalSituationType(String msg) 
	{
		this.msg = msg;
	}
	
	public String toString()
	{
		return this.msg;
	}
}
