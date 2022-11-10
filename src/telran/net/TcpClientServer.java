package telran.net;

import java.net.*;
import java.io.*;

public class TcpClientServer implements Runnable {
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private ApplProtocol protocol;
	public TcpClientServer(Socket socket, ApplProtocol protocol) throws Exception {
		this.protocol = protocol;
		this.socket = socket;
		input = new ObjectInputStream(socket.getInputStream());
		output = new ObjectOutputStream(socket.getOutputStream());
	}
	@Override
	public void run() {
		try {
			while(true) {
				Request request = (Request) input.readObject();
				Response response = protocol.getResponse(request);
				output.writeObject(response);
			}
		} catch (EOFException e) {
			System.out.println("client closed connection");
		}
		catch (Exception e) {
			System.out.println("abnormal closing connection " + e.getMessage());
		}

	}

}
