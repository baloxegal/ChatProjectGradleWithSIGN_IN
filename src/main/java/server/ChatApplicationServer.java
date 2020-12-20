package server;

import lib.Connection;
import lib.Message;
import lib.Action;
import lib.Operation;
import lib.User;
import java.util.Map;
import java.util.HashMap;
import java.net.Socket;
import java.io.IOException;

public class ChatApplicationServer {
	
	public static final Integer PORT = 7777;
	Map <Socket, User> users = new HashMap<Socket, User>();

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		Connection conn = new Connection(PORT);		
		
		conn.fetch();
		conn.send(new Action(Operation.SEND_MSG, new Message("Hello Back!", new User("Mister Egor"), new User("Mister Alesha"))));
		conn.fetch();				
		conn.send(new Action(Operation.CONFITMED));
	}
}
