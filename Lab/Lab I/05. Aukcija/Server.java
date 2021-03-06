import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*;
import java.util.Hashtable;

public class Server{
	
	public Server(){}
	
	public static void main(String[] args){
		
		String host = "localhost";
		String port = "1099";
		String service = "Aukcija";
		String link = "rmi://" + host + ":" + port + "/" + service;
		
		try{
			
			LocateRegistry.createRegistry(Integer.parseInt(port));
			System.out.println("Java RMI Registry created at url: " + link);
		}
		catch(Exception e)
		{
			System.out.println("Java RMI Registry already exists!");
		}

		try{
			
			EAukcija aukcija = new EAukcijaImpl();
			
			for(int i = 0; i < 10; i++)
				aukcija.dodajEksponat(new EksponatImpl("123" + (char)('A' + (i * i) % 26) + (char)('B' + (2 * i) % 25) + (char)('0' + (1 + i) % 10), "Random" + String.valueOf(i + 1), 1000 * (5 + i)));
			
			System.out.println("\nID-ovi eksponata u serveru: ");
			Hashtable<String, Eksponat> baza = aukcija.vratiEksponate();
			for(String key : baza.keySet())
				System.out.println(key + " => " + baza.get(key).vratiNaziv());
			
			Naming.rebind(link, aukcija);
			System.console().readLine("\nPress enter to quit...");
		}
		catch(Exception e)
		{ System.out.println("Error occurred: " + e); }
		
		System.exit(0);
	}
}