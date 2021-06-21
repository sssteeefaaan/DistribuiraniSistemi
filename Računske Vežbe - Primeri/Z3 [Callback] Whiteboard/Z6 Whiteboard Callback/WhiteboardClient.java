import java.rmi.*;
import java.rmi.server.*;
import java.util.Vector;
import java.awt.Rectangle;
import java.awt.Color;
import java.io.IOException;
import java.util.*; 

public class WhiteboardClient {
 
 public IWhiteboardCallback cb=null;
 public IWhiteboardManager mngr = null;
 
 public WhiteboardClient() {
 } 
 
 public void run() {

	try {
		
		mngr = (IWhiteboardManager)Naming.lookup("rmi://localhost:1099/Whiteboard");
		System.out.println("Found server");
		
		cb=new WhiteboardCallback();		
		mngr.register(cb);	
		
	} catch(RemoteException e)
	{
		System.out.println("ERROR: " + e.getMessage());
		
	} catch(Exception e)
	{
		System.out.println("ERROR: " + e.getMessage());		
	} 
	
	try {	
		
		String option;			
		System.out.println("Got vector");
	
		Scanner scn=new Scanner(System.in);
	
		while(true) {
			
			option=scn.nextLine().trim();	
			
			if (option.equals("draw")) {
				
				mngr.addNewShape("LINE", new Rectangle(50,50,300,400),Color.red,Color.blue, false);
				System.out.println("Stored shape");	
				
			} else 
			if (option.equals("exit")) {
				
				break;
				
			}			
		}	
		
		mngr.unregister(cb);

	} 
	catch(RemoteException e)
	{		
		System.out.println("ERROR: " + e.getMessage());		
	}	
	
 } 
 
 /* PRINT WHITEBOARD CONTENT */
 
  public void showWhiteboard() {
 
	System.out.println("Whiteboard content");	
 
	try {  			
		 
		Vector sList = mngr.getAllShapes();
			
		for(int i=0; i<sList.size(); i++) {
			 IShape s = (IShape)sList.elementAt(i);
			 System.out.println(s.print());						 
		}
		 
	} catch (IOException ioException) {     
	
	}    
	 
 }
 
 public static void main(String args[]) {
 
	WhiteboardClient instance=new WhiteboardClient();
	instance.run();
	
	/*
	try {  			
		System.in.read();      			 		
	} catch (IOException ioException) {     
	
	}      
	*/
	
	System.exit(0);		
	
 } 
 
 /* CALLBACK IMPLEMENTATION */
 
 public class WhiteboardCallback extends UnicastRemoteObject implements IWhiteboardCallback {
	
	public WhiteboardCallback() throws RemoteException 
	{		
	}
	
	public void callback(int version) throws RemoteException 
	{
		System.out.println("Current Whiteboard version is: "+version);			
		showWhiteboard();		
	}	
 }
 
}





