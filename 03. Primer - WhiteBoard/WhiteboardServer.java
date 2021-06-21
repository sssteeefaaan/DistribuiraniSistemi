import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*; 

public class WhiteboardServer{
	
	public WhiteboardServer(){}
	
	public static void main(String args[]){
		
		String host = "localhost";
		String port = "1099";
		String service = "WhiteboardManager";
		String link = "rmi://" + host + ":" + port + "/" + service;
		
		try{
			LocateRegistry.createRegistry(Integer.parseInt(port));
			System.out.println("Java RMI registry created!");
		}
		catch(RemoteException rE)
		{ System.out.println("Java RMI registry already exists!"); }
		
		try{
			
			IWhiteboardManager manager = new WhiteboardManager();
			
			Naming.rebind(link, manager);
			
			System.console().readLine("Whiteboard server's started on: " + link);
		}
		catch(Exception e)
		{ System.out.println("Error occurred: " + e); }
	}
}