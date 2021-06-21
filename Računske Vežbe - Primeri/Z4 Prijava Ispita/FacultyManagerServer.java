import java.io.IOException;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.net.MalformedURLException; 

public class FacultyManagerServer {
 
 public FacultyManagerServer() {
 }
 
 public static void main(String args[]) {
	
	String host = args[0];
	String port = args[1];
	String server = args[2];
    
	FacultyManager fm = null;
 
	try {
		fm = new FacultyManagerImpl();
	} catch (RemoteException remoteException) {
		System.err.println("Failure during object export to RMI: " + remoteException);
	}

	try
	{ 
		LocateRegistry.createRegistry(Integer.parseInt(port)); 		
		String url = "//" + host + ":" + port + "/" + server;
		Naming.rebind(url, fm);
	} catch (RemoteException remoteException) {
		System.err.println("Failure during Name registration: " + remoteException);
	} catch (MalformedURLException	malformedException) {
		System.err.println("Failure during Name registration: " + malformedException);
	}
 
	 System.out.println("Server started.");
	 System.out.println("Enter <CR> to end.");
	 
	 try {
		System.in.read();
	 } catch (IOException ioException) {
	 }
	 
	 System.exit(0);
 }
}