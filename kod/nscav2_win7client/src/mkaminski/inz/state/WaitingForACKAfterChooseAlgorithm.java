package mkaminski.inz.state;

import java.net.Socket;
import java.net.SocketException;

import mkaminski.inz.socket.SocketConnectionContext;
import mkaminski.inz.socket.SocketConnectionState;

public class WaitingForACKAfterChooseAlgorithm implements SocketConnectionState {

	public byte[] getDataToSend(SocketConnectionContext socketConnectionContext) {
		return socketConnectionContext.getMessageFormer()
				.formEstablishEncryption();
	}

	public void onTimeout() {

	}

	public void setTimeout(Socket socket) {
		try {
			socket.setSoTimeout(SOCKET_TIMEOUT);
		} catch (SocketException e) {
			// TODO
		}
	}

	public boolean proceedText(SocketConnectionContext socketConnectionContext,
			int sizeOfMessage, byte[] text) {
		return socketConnectionContext.getMessageDecrypter()
				.checkACKAfterChooseAlgorithm(sizeOfMessage, text);
	}

	public SocketConnectionState setNewState() {
		return new WaitingForChooseAuthModule();
	}

}
