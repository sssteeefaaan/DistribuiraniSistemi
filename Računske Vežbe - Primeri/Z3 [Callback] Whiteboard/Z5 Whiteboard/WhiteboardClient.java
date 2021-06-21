import java.rmi.*;
import java.rmi.server.*;
import java.util.Vector;
import java.awt.Rectangle;
import java.awt.Color;
import java.io.IOException;
import java.util.*; 

public class WhiteboardClient {
 
 public static void main(String args[]) {
 
	IWhiteboardManager mngr = null;
	
	try {
		
		mngr = (IWhiteboardManager)Naming.lookup("rmi://localhost:1099/Whiteboard");
		System.out.println("Found server");
		
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
		
			if (option.equals("read")) {				
			
				Vector sList = mngr.getAllShapes();
			
				for(int i=0; i<sList.size(); i++) {
					 IShape s = (IShape)sList.elementAt(i);
					 System.out.println(s.print());						 
				}
				
			} else 
			if (option.equals("draw")) {
				
				mngr.addNewShape("LINE", new Rectangle(50,50,300,400),Color.red,Color.blue, false);
				System.out.println("Stored shape");	
				
			} else 
			if (option.equals("exit")) {
				
				break;
				
			}			
		}	

	} 
	catch(RemoteException e)
	{		
		System.out.println("ERROR: " + e.getMessage());		
	}	
	
 } 
 
}





