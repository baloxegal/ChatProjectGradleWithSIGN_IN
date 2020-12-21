package lib;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.io.IOException;

public class Connection {
	
	private transient Socket socket;
		
	public Connection(Socket socket) {
		this.socket = socket;
	}
	
	@SuppressWarnings("resource")
	public Connection(Integer port) throws IOException {
		this(new ServerSocket(port).accept());
	}
	
	public Connection(InetAddress localhost, Integer port) throws UnknownHostException, IOException {
		this(new Socket(localhost, port));
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public void send(Object object) throws IOException {
		
		ObjectOutputStream dout = new ObjectOutputStream(socket.getOutputStream());
		
		dout.writeObject(object);		
	}
	
	public Object fetch() throws IOException, ClassNotFoundException {
		
		ObjectInputStream din = new ObjectInputStream(socket.getInputStream());
	
		return din.readObject();
	}
}
