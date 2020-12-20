package client;

import lib.Connection;
import lib.Message;
import lib.Action;
import lib.Operation;
import lib.User;
import java.net.InetAddress;
import java.util.Scanner;
import java.io.IOException;

public class ChatApplicationClient {
	public static final Integer PORT = 7777;
	public static final InetAddress HOST = InetAddress.getLoopbackAddress();

	public static void main(String[] args) throws IOException, ClassNotFoundException {		
		
		Connection conn = new Connection(HOST, PORT);
		
		System.out.print("Enter user name: ");
		Scanner in = new Scanner(System.in);
		String userName = in.nextLine();
		in.close();
		
		conn.send(new Action(Operation.SIGN_IN, new User(userName)));
		conn.fetch();
//		conn.send(new Action(Operation.CONFIRM));
//		conn.fetch();
		
	}
}
