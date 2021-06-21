import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*; 

public class WhiteboardServer {
	
	public WhiteboardServer() {
		
	}
	
	public static void main(String args[]){

		try {
			
			LocateRegistry.createRegistry(1099); 
			System.out.println("java RMI registry created.");			
	
		} catch (RemoteException e) {            
			System.out.println("java RMI registry already exists.");
		}		
	
		try {
		 
			IWhiteboardManager mngr = new WhiteboardManager();	
			
			Naming.rebind("rmi://localhost:1099/Whiteboard",mngr); 

			System.out.println("Whiteboard server ready");
		 }
		 catch(Exception e) {
			System.out.println("Whiteboard server main " + e.getMessage());
		 }
 
	}
 
}