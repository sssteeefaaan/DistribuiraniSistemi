import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*; 

public class Server{
	
	public Server(){}
	
	public static void main(String[] args){
		
		String host = "localhost";
		String port = "1099";
		String service = "MobilniOperater";
		String link = "rmi://" + host + ":" + port + "/" + service;
		Operater operater = null;
		
		try{
			LocateRegistry.createRegistry(Integer.parseInt(port));
			System.out.println("Java RMI Registry created at: " + port);
		}
		catch(Exception e)
		{
			System.out.println("Java RMI Registry already exists!");
		}
		
		try{
			
			operater = new OperaterImpl();
			
			for(int i = 0; i < 10 ; i++)
				operater.dodajKorisnika(new KorisnikImpl(new StanjeImpl("06331254" + String.valueOf(i), i + 1, i + 1, i + 1, i + 1), 3, 2, 8));
				
			Naming.rebind(link, operater);
			
			System.console().readLine("Server's running on: " + link + "\nPress enter to quit...");
		}
		catch(Exception e)
		{
			System.out.println("Error occurred: " + e);
		}
	}
}