package glavni;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.Hashtable;
import java.util.Scanner;

public class Server{
	
	public Server(){}
	
	public static void main(String[] args){
		
		String host = "localhost";
		String port = "1099";
		String service = "eAukcija";
		String link = "rmi://" + host + ":" + port + "/" + service;
		
		try{
			LocateRegistry.createRegistry(Integer.parseInt(port));
			System.out.println("\nJava RMI registar kreiran na linku: " + link);
		}
		catch(Exception e)
		{ System.out.println("\nJava RMI registar vec postoji!"); }

		try{
			
			EAukcijaManager aukcija = new EAukcijaManagerImpl();
			System.out.println("\neAkcija instanca kreirana!");
			
			for(int i = 0; i < 10; i++)
				aukcija.dodajEksponat(new EksponatImpl("123" + (char)('A' + (i * i) % 26) + (char)('B' + (2 * i) % 25) + (char)('0' + (1 + i) % 10), "Nasumican Naziv " + String.valueOf(i + 1), 1000 * (5 + i)));
			
			System.out.println("\nEksponati uneti u bazu!");
			System.out.println("\nID-ovi eksponata na serveru: ");
			Hashtable<String, Eksponat> baza = aukcija.vratiBazu();
			for(String key : baza.keySet())
				System.out.println(key + " => " + baza.get(key).vratiNaziv());
			
			Naming.rebind(link, aukcija);
			System.out.println("\nInstanca eAukcije unesena u RMI registar!");
			
			System.out.println("Server je dostupan na linku " + link);
			System.out.println("Press enter to quit...");
			Scanner console = new Scanner(System.in);
			console.nextLine();
			console.close();
		}
		catch(Exception e)
		{ System.out.println("Nastala je greska: " + e); }
		
		System.exit(0);
	}
}