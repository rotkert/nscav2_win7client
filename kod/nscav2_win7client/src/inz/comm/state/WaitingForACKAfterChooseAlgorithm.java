package inz.comm.state;

import java.net.Socket;
import java.net.SocketException;

import inz.comm.socket.SocketConnectionContext;
import inz.comm.socket.SocketConnectionState;
import mkaminski.inz.errorHandling.InfoMessages;

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
		System.out.println(InfoMessages.NEW_STATE + "WaitingForChooseAuthModule");
		return new WaitingForChooseAuthModule();
	}

}
