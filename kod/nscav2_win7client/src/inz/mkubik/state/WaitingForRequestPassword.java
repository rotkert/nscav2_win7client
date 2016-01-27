package inz.mkubik.state;

import java.net.Socket;

import inz.mkamins.logger.AndroidLogger;
import inz.mkamins.logger.Level;
import inz.mkamins.state.WaitingForWaitingForLogsCst;
import inz.mkubik.socket.SocketConnectionContext;
import inz.mkubik.socket.SocketConnectionState;

public class WaitingForRequestPassword implements SocketConnectionState {

	public byte[] getDataToSend(SocketConnectionContext socketConnectionContext) {
		AndroidLogger.INSTANCE.writeToLog(this.getClass(), Level.INFO,
				"Getting data to send");
		return socketConnectionContext.getMessageFormer().formPassword();
	}

	public void onTimeout() {
		// TODO Auto-generated method stub

	}

	public void setTimeout(Socket socket) {
		// TODO Auto-generated method stub

	}

	public boolean proceedText(SocketConnectionContext socketConnectionContext,
			int sizeOfMessage, byte[] text) {
		return socketConnectionContext.getMessageDecrypter()
				.checkRequestPassword(sizeOfMessage, text);
	}

	public SocketConnectionState setNewState() {
		return new WaitingForWaitingForLogsCst();
	}

}
