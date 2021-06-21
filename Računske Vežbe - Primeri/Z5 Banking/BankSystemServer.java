import java.io.IOException;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*; 
import java.net.MalformedURLException;

public class BankSystemServer {
 
 public BankSystemServer(String host, String port, String service) {

	BankManager bm = null;
	 
	 try {
		 
		bm = new BankManagerImpl();
		
		LocateRegistry.createRegistry(Integer.parseInt(port));  
		 
		Naming.rebind("rmi://" + host + ":" + port + "/" + service, bm);
		
	 }	 
	 catch (RemoteException remoteException) {	 
		System.err.println("Failure during Name registration: " + remoteException);
	 } catch (MalformedURLException malformedException) { 
		System.err.println("Failure URL "+malformedException);
	 }
	 	
 }
 
 public static void main(String args[]) {
 
	String host = args[0];
	String port = args[1];
	String service = args[2]; 
 
	new BankSystemServer(host,port,service);
	
	System.out.println("Server started.");
	System.out.println("Enter <CR> to end.");

	try {
		int i = System.in.read();
	} catch (IOException ioException) {
	 
	}
		 
	System.exit(0);
	 
 }
}