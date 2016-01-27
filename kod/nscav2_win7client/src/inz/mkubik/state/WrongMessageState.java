package inz.mkubik.state;

import java.net.Socket;
import java.net.SocketException;

import inz.mkamins.logger.AndroidLogger;
import inz.mkamins.logger.Level;
import inz.mkubik.socket.SocketConnectionContext;
import inz.mkubik.socket.SocketConnectionState;

public class WrongMessageState implements SocketConnectionState {


	public byte[] getDataToSend(SocketConnectionContext socketConnectionContext) {
		AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.INFO,
				"Getting data to send");
		return null;
	}

	public void onTimeout() {

	}

	public void setTimeout(Socket socket) {
		try {
			socket.setSoTimeout(SOCKET_TIMEOUT);
		} catch (SocketException e) {
			AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.ERROR,
					"Error while setting timeout");
		}
	}

	public boolean proceedText(SocketConnectionContext socketConnectionContext,
			int sizeOfMessage, byte[] text) {
		AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.INFO,
				"Proceeding text");
		return false;
	}

	public SocketConnectionState setNewState() {
		return null;
	}

}
