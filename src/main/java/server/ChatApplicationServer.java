package server;

import lib.Connection;
import lib.Action;
import lib.Operation;
import lib.User;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;

public class ChatApplicationServer {
	
	public static final Integer PORT = 7777;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		Connection conn = new Connection(PORT);		
		Map <Integer, User> users = new HashMap<Integer, User>();
		Action action = (Action) conn.fetch();
		
		if(action.getOperation().equals(Operation.SIGN_IN)) {
			if(users.putIfAbsent(conn.getSocket().getPort(), (User)action.getTarget()) == null)
				conn.send(new Action(Operation.SUCCESS, null));
			else
				conn.send(new Action(Operation.ALREADY_CONNECTED));			
		}
		
		action = (Action) conn.fetch();
				
		if(action.getOperation().equals(Operation.USER_LIST))
			conn.send(new Action(Operation.SUCCESS, users));
	}
}
