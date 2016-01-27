package inz.mkubik.socket;

import java.net.Socket;

import inz.mkamins.logger.AndroidLogger;
import inz.mkamins.logger.Level;
import inz.mkamins.message.DatabaseProvider;
import inz.mkubik.message.MessageDecrypter;
import inz.mkubik.message.MessageFormer;
import inz.mkubik.state.WaitingForHello;
import inz.mkubik.state.WrongMessageState;

/**
 * Class used in MachineState pattern as a context
 * 
 * @author Marcin Kubik <markubik@gmail.com>
 * 
 */
public class SocketConnectionContext {

	/**
	 * Reference to state of MachineState
	 */
	protected SocketConnectionState socketConnectionState;

	/**
	 * Forms data to be send
	 */
	private MessageFormer messageFormer;

	/**
	 * Decrypts data from remote server
	 */
	protected MessageDecrypter messageDecrypter;

	/**
	 * Used to get logs from database
	 */
	private DatabaseProvider databaseProvider;

	/**
	 * Public constructor Sets state to <code>Connected</code>
	 */
	public SocketConnectionContext(DatabaseProvider databaseProvider) {
		AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.INFO,
				"Constructor of Socket Connection Context");
		this.databaseProvider = databaseProvider;
		messageFormer = new MessageFormer();
		messageDecrypter = new MessageDecrypter();
		this.socketConnectionState = new WaitingForHello();
	}

	/**
	 * Getter for message decrypter
	 * @return message decrypter
	 */
	public MessageDecrypter getMessageDecrypter() {
		return this.messageDecrypter;
	}

	/**
	 * Getter for message former
	 * @return message former
	 */
	public MessageFormer getMessageFormer() {
		return this.messageFormer;
	}

	/**
	 * Method used to send data to remote server
	 */
	public byte[] getDataToSend() {
		return socketConnectionState.getDataToSend(this);
	}

	/**
	 * Method invoked when timeout occurs
	 */
	public void onTimeout() {
		socketConnectionState.onTimeout();
	}

	/**
	 * Sets timeout for each state
	 * 
	 * @param socket
	 *            Socket that is used to communicate
	 */
	public void setSoTimeout(Socket socket) {
		socketConnectionState.setTimeout(socket);
	}

	/**
	 * Proceeds text after it is received
	 * @param sizeOfMessage size of received message
	 * @param text text received from remote server
	 * @return true, if right text was received, false otherwise
	 */
	public boolean proceedText(int sizeOfMessage, byte[] text) {
		return socketConnectionState.proceedText(this, sizeOfMessage, text);
	}

	/**
	 * Sets next state of State Machine Pattern
	 */
	public void setNextState() {
		this.socketConnectionState = socketConnectionState.setNewState();
	}

	/**
	 * Sets wrong state (if we received wrong message)
	 */
	public void setWrongState() {
		this.socketConnectionState = new WrongMessageState();
	}

	/**
	 * Getter for database provider
	 * @return database provider
	 */
	public DatabaseProvider getDatabaseProvider() {
		return this.databaseProvider;
	}
}
