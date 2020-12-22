package client;

import lib.Connection;
import lib.Action;
import lib.Operation;
import lib.User;
import java.net.InetAddress;
import java.util.Map;
import java.util.Scanner;
import java.io.IOException;

public class ChatApplicationClient {
	
	public static final Integer REMOTE_PORT = 7777;
	public static final InetAddress REMOTE_HOST = InetAddress.getLoopbackAddress();

	public static void main(String[] args) throws IOException, ClassNotFoundException {		
				
		System.out.print("Enter user name: ");
		Scanner in = new Scanner(System.in);
		
		Connection conn = new Connection(REMOTE_HOST, REMOTE_PORT);
		conn.send(new Action(Operation.SIGN_IN, new User(in.nextLine())));
		
		in.close();		
		
		Action action = (Action)conn.fetch();
		
		if(action.getOperation().equals(Operation.SUCCESS))	
			conn.send(new Action(Operation.USER_LIST, null));

		action = (Action)conn.fetch();
		
		@SuppressWarnings("unused")
		Map<?, ?> users = (Map<?, ?>) action.getTarget();
	}
}
