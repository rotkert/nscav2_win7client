package mkaminski.inz.socket;

import java.net.Socket;

/**
 * Interface used for polimorphism and StateMachine pattern
 * 
 * @author Marcin Kubik <markubik@gmail.com>
 * 
 */
public interface SocketConnectionState {

	public static final int SOCKET_TIMEOUT = 4000;


	/**
	 * Send data to remote server
	 */
	public byte[] getDataToSend(SocketConnectionContext socketConnectionContext);

	/**
	 * Proceeds after timeout
	 */
	public void onTimeout();

	/**
	 * Used to set timeout on socket (that is based on actual state)
	 * 
	 * @param socket
	 *            socket that will be used for network operations
	 */
	public void setTimeout(Socket socket);

	/**
	 * Method use to check, if received message is the right one
	 * @param socketConnectionContext context (used in State Machine pattern)
	 * @param sizeOfMessage size of received message
	 * @param text received text
	 * @return true, if we received right text, false otherwise
	 */
	public boolean proceedText(SocketConnectionContext socketConnectionContext,
			int sizeOfMessage, byte[] text);

	/**
	 * Sets new state of State Machine pattern
	 * @return new state
	 */
	public SocketConnectionState setNewState();
}
