package lib;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.io.IOException;

public class Connection implements Serializable {
	
	private static final long serialVersionUID = -8026900816337050671L;
	private transient Socket socket;
	public static ProxyS ProxyS;
	
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
	
	public ProxyS getProxyS() {
		if(ProxyS == null)
			ProxyS = new ProxyS();
		else {
			ProxyS.localAddress = socket.getLocalAddress();
			ProxyS.remoteAddress = socket.getInetAddress();
			ProxyS.localPort = socket.getLocalPort();
			ProxyS.remotePort = socket.getPort();
		}
		return ProxyS;			
	}
	
	public void send(Object object) throws IOException {
		
		ObjectOutputStream dout = new ObjectOutputStream(socket.getOutputStream());
		
		dout.writeObject(object);		
	}
	
	public Object fetch() throws IOException, ClassNotFoundException {
		
		ObjectInputStream din = new ObjectInputStream(socket.getInputStream());
	
		return din.readObject();
	}

	@Override
	public String toString() {
		return "Connection [socket=" + socket + "]";
	}

	public class ProxyS implements Serializable {

		private static final long serialVersionUID = -8000241752157437968L;
		
		private InetAddress localAddress;
		private InetAddress remoteAddress;
		private Integer localPort;
		private Integer remotePort;
				
		private ProxyS() {
			
			this.localAddress = socket.getLocalAddress();
			this.remoteAddress = socket.getInetAddress();
			this.localPort = socket.getLocalPort();
			this.remotePort = socket.getPort();
		}

		public InetAddress getLocalAddress() {
			return localAddress;
		}
		
		public InetAddress getRemoteAddress() {
			return remoteAddress;
		}

		public Integer getLocalPort() {
			return localPort;
		}

		public Integer getRemotePort() {
			return remotePort;
		}

		@Override
		public String toString() {
			return "ProxyS [localAddress=" + localAddress + ", remoteAddress=" + remoteAddress + ", localPort="
					+ localPort + ", remotePort=" + remotePort + "]";
		}	
		
	}
		
}
