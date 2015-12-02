package inz.comm.errorHandling;

public enum ErrorMessages
{
	NOT_CONNECTED ("ERROR - ClientSocket.java: connect()"),
	KEY_NOT_FOUND ("ERROR - KeyLoader.java: readPublicKey()"),
	REPORT_NOT_FOUND ("ERROR - Performance report not found ");
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
