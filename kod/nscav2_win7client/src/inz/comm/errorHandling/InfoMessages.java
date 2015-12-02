package inz.comm.errorHandling;

public enum InfoMessages
{
	NEW_STATE ("INFO - Set new state: ");
	
	private final String msg;
	
	private InfoMessages(String msg) 
	{
		this.msg = msg;
	}
	
	public String toString()
	{
		return this.msg;
	}
}
