package inz.comm.socket;

import java.net.Socket;

import inz.comm.data.DataPackProvider;
import inz.comm.message.MessageDecrypter;
import inz.comm.message.MessageFormer;
import inz.comm.state.WaitingForHello;
import inz.comm.state.WrongMessageState;

/**
 * Class used in MachineState pattern as a context
 * 
 * @author Marcin Kubik <markubik@gmail.com>
 * 
 */
public class SocketConnectionContext
{
	/** Reference to state of MachineState */
	private SocketConnectionState socketConnectionState;

	/** Forms data to be send */
	private MessageFormer messageFormer;

	/** Decrypts data from remote server */
	private MessageDecrypter messageDecrypter;

	/** Used to get logs from database */
	private DataPackProvider databaseProvider;
	
	private String hostname;
	
	private String clientId;
	
	private boolean isReport;

	/**
	 * Public constructor Sets state to <code>Connected</code>
	 */
	public SocketConnectionContext(DataPackProvider databaseProvider, String hostname, String clientId, boolean isReport)
	{
		this.databaseProvider = databaseProvider;
		messageFormer = new MessageFormer();
		messageDecrypter = new MessageDecrypter();
		this.socketConnectionState = new WaitingForHello();
		this.hostname = hostname;
		this.clientId = clientId;
		this.isReport = isReport;
	}

	/**
	 * Getter for message decrypter
	 * 
	 * @return message decrypter
	 */
	public MessageDecrypter getMessageDecrypter()
	{
		return this.messageDecrypter;
	}

	/**
	 * Getter for message former
	 * 
	 * @return message former
	 */
	public MessageFormer getMessageFormer()
	{
		return this.messageFormer;
	}

	/**
	 * Method used to send data to remote server
	 */
	public byte[] getDataToSend()
	{
		return socketConnectionState.getDataToSend(this);
	}

	/**
	 * Method invoked when timeout occurs
	 */
	public void onTimeout()
	{
		socketConnectionState.onTimeout();
	}

	/**
	 * Sets timeout for each state
	 * 
	 * @param socket
	 *            Socket that is used to communicate
	 */
	public void setSoTimeout(Socket socket)
	{
		socketConnectionState.setTimeout(socket);
	}

	/**
	 * Proceeds text after it is received
	 * 
	 * @param sizeOfMessage
	 *            size of received message
	 * @param text
	 *            text received from remote server
	 * @return true, if right text was received, false otherwise
	 */
	public boolean proceedText(int sizeOfMessage, byte[] text)
	{
		return socketConnectionState.proceedText(this, sizeOfMessage, text);
	}

	/**
	 * Sets next state of State Machine Pattern
	 */
	public void setNextState()
	{
		this.socketConnectionState = socketConnectionState.setNewState();
	}

	/**
	 * Sets wrong state (if we received wrong message)
	 */
	public void setWrongState()
	{
		this.socketConnectionState = new WrongMessageState();
	}
	
	public void setStartState(SocketConnectionState s)
	{
		this.socketConnectionState = s;
	}

	/**
	 * Getter for database provider
	 * 
	 * @return database provider
	 */
	public DataPackProvider getDatabaseProvider()
	{
		return this.databaseProvider;
	}
	
	public String getHostname()
	{
		return hostname;
	}
	
	public String getClientId()
	{
		return clientId;
	}
	
	public SocketConnectionState getState()
	{
		return socketConnectionState;
	}
	
	public boolean isReport()
	{
		return isReport;
	}
}
