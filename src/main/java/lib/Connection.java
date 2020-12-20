package lib;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.core.StreamWriteFeature;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.InputStream;

public class Connection {
	
	private Socket socket;
	private ObjectMapper objectMapper;
	
	public Connection(Socket socket) {
		this.socket = socket;
		objectMapper = JsonMapper.builder().disable(StreamWriteFeature.AUTO_CLOSE_TARGET)
				.disable(StreamReadFeature.AUTO_CLOSE_SOURCE)
				.enable(StreamWriteFeature.FLUSH_PASSED_TO_STREAM)
				.build();
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
		
		OutputStream dout = new ObjectOutputStream(socket.getOutputStream());
		
		objectMapper.writeValue(dout, object);		
	}
	
	public Object fetch() throws IOException, ClassNotFoundException {
		
		InputStream din = new ObjectInputStream(socket.getInputStream());
	
		return objectMapper.readValue(din, Object.class);
	}
}
