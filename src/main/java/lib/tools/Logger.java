package lib.tools;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.JoinPoint;

@Aspect
public class Logger {
	
	@Before("execution(* client.ChatApplicationClient.main(..))")
	public void LogBeforStartClient() {
		
		System.out.println("Client Starting");
		
	}
	
	@Before("execution(* server.ChatApplicationServer.main(..))")
	public void LogBeforStartServer() {
		
		System.out.println("Server Starting");
		
	}
	
	@AfterReturning(value = "call(* fetch())", returning = "transferredData")
	public void LogAfterServerReading(JoinPoint jp, Object transferredData) {			
		if(jp.getSourceLocation().getWithinType().equals(server.ChatApplicationServer.class))
			System.out.println("Client sends: " + transferredData);
		else if(jp.getSourceLocation().getWithinType().equals(client.ChatApplicationClient.class))
			System.out.println("Server sends: " + transferredData);
		
	}	
	
	@After("execution(* client.ChatApplicationClient.main(..))")
	public void LogAfterEndClient() {
		
		System.out.println("Client Ending");
		
	}	
	
	@After("execution(* server.ChatApplicationServer.main(..))")
	public void LogAfterEndServer() {
		
		System.out.println("Server Ending");
		
	}	
}
