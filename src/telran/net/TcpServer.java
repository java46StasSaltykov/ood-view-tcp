package telran.net;

import java.net.*;

public class TcpServer implements Runnable {
	private ServerSocket serverSocket;
	private int port;
	private ApplProtocol protocol;

	public TcpServer(int port, ApplProtocol protocol) throws Exception {
		this.port = port;
		this.protocol = protocol;

		serverSocket = new ServerSocket(port);
	}

	@Override
	public void run() {
		System.out.println("Server is listening on the port " + port);
		try {
			while (true) {
				Socket socket = serverSocket.accept();
				TcpClientServer clientServer = new TcpClientServer(socket, protocol);
				Thread thread = new Thread(clientServer);
				thread.start();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
