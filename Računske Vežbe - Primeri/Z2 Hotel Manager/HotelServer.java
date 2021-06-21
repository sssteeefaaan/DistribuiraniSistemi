import java.io.IOException;
import java.rmi.*;
import java.rmi.server.*;
import java.net.MalformedURLException;
import java.rmi.registry.*; 

public class HotelServer {
 
 public static void main(String args[]) {
  
  String host = args[0];
  String port = args[1];
  String service = args[2];
  
  HotelManager hotel = null;
  
  try 
  { 
	LocateRegistry.createRegistry(Integer.parseInt(port)); 
	System.out.println("java RMI registry created.");			
		
  } catch (RemoteException e) {            
	System.out.println("java RMI registry already exists.");
  }  
  
  try {
   hotel = new HotelManagerImpl();
  } catch (RemoteException remoteException) {
   System.err.println("Failure during creating the university remote object");
  }
  
  try {
   Naming.rebind("rmi://" + host + ":" + port + "/" + service, hotel);
  } catch (RemoteException remoteException) {
   System.err.println("Failure during Name registration: " + remoteException);
  } catch (MalformedURLException malformedException) {
   System.err.println("Failure during Name registration: " + malformedException);
  }

  System.out.println("Server started.");
  System.out.println("Enter <CR> to end.");
  
  try {
   System.in.read();
  } catch (IOException ioException) {}
  
  System.exit(0);
  
 }
 
}