package telran.net;

import java.net.*;
import java.io.*;

public class TcpClientServer implements Runnable {
	private static final int READ_TIMEOUT = 100;
	private static final int CLIENT_IDLE_TIMEOUT = 600;
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private ApplProtocol protocol;
	private TcpServer tcpServer;
	private int idlePeriod = 0;
	
	public TcpClientServer(Socket socket, ApplProtocol protocol, TcpServer tcpServer) throws Exception {
		this.protocol = protocol;
		this.socket = socket;
		this.socket.setSoTimeout(READ_TIMEOUT);
		this.tcpServer = tcpServer;
		input = new ObjectInputStream(socket.getInputStream());
		output = new ObjectOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {
		while (!tcpServer.isShutdown) {
			try {
				Request request = (Request) input.readObject();
				if (request != null) {
					idlePeriod = 0;
				}
				Response response = protocol.getResponse(request);
				output.writeObject(response);
			} catch (SocketTimeoutException e) {
				idlePeriod++;
			} catch (EOFException e) {
				System.out.println("client closed connection");
				break;
			} catch (Exception e) {
				System.out.println("abnormal closing connection " + e.getMessage());
				break;
			}
			if (idlePeriod >= CLIENT_IDLE_TIMEOUT && tcpServer.getCountConnections() > tcpServer.getNThreads()) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		if (tcpServer.isShutdown) {
			System.out.println("client connection closed by server shutdown");
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}

