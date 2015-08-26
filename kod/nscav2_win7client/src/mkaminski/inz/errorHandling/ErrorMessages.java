package mkaminski.inz.errorHandling;

public enum ErrorMessages
{
	NOT_CONNECTED ("ERROR - ClientSocket.java: connect()");
	
	private final String msg;
	
	private ErrorMessages(String msg) 
	{
		this.msg = msg;
	}
	
	public String toString()
	{
		return this.msg;
	}
}
