package server;

import lib.Connection;
import lib.Action;
import lib.Operation;
import lib.User;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

public class ChatApplicationServer {
	
	public static final Integer LOCAL_PORT = 7777;
	public static Map <Connection.ProxyS, User> users = new HashMap<Connection.ProxyS, User>();
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		Connection conn = new Connection(LOCAL_PORT);		
		
		Action action = (Action) conn.fetch();
		
		if(action.getOperation().equals(Operation.SIGN_IN)) {
			if(users.putIfAbsent(conn.getProxyS(), (User)action.getTarget()) == null)
				conn.send(new Action(Operation.SUCCESS, null));
			else
				conn.send(new Action(Operation.ALREADY_CONNECTED, null));			
		}
		
		action = (Action) conn.fetch();
				
		if(action.getOperation().equals(Operation.USER_LIST))
			conn.send(new Action(Operation.SUCCESS, users));
	}
}
