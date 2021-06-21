import java.io.IOException; 
import java.net.MalformedURLException; 
import java.rmi.Naming; 
import java.rmi.RemoteException; 
import java.rmi.registry.*; 

public class CalculatorServer { 
 
 public CalculatorServer(String host, String port, String service)  {     
 
	try 
	{ 
		LocateRegistry.createRegistry(Integer.parseInt(port)); 
		System.out.println("java RMI registry created.");			
		
    } catch (RemoteException e) {            
        System.out.println("java RMI registry already exists.");
    }	
	
	try 
	{  
	
		Calculator c = new CalculatorImpl();   
		
		Naming.rebind("rmi://" + host + ":" + port + "/" + service, c);   		
		
	}   catch (RemoteException remoteException) {       

		System.err.println("Failure during object export to RMI: "+remoteException);      
		
	}   catch (MalformedURLException malformedException) {         
	
		System.err.println("Failure during Name registration: " + malformedException);     
		
	} 
 
 }   

 public static void main(String[] args) {       
 
	String host = args[0];    
	String port = args[1];    
	String service = args[2];    
	
	new CalculatorServer(host, port, service);      
	
	System.out.println("Server started.");      
	System.out.println("Enter <CR> to end.");      
	
	try {  	
		System.in.read();     
 		
	} catch (IOException ioException) {      
	
	}      
	
	System.exit(0);  
	
} 
 
} 
 