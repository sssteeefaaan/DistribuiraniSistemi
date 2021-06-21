import java.rmi.*;
import java.util.Scanner;
import java.rmi.registry.*;

public class Server{
	
	public Server(){}
	
	public static void main(String[] args){
		
		String host = "localhost";
		String port = "1099";
		String service = "eBanka";
		String link = "rmi://" + host + ":" + port + "/" + service;
		EBanka banka = null;
		
		try{
			LocateRegistry.createRegistry(Integer.parseInt(port));
			System.out.println("Kreiran je Java RMI Registar!");
		}
		catch(Exception e){
			System.out.println("Java RMI Registar vec postoji!");
		}
		
		try{
			banka = new EBankaImpl(115.5f);
			System.out.println("Kreirana je instanca eBanke!");
			
			for(int i = 0; i < 10; i++)
				banka.dodajKorisnika(new KorisnikImpl("ABC" + String.valueOf(i), 20 * (i + 0.4f), 15 * (i + 0.3f)));
			System.out.println("Korisnici su inicijalizovani!");
			
			System.out.println("\nJedinsteni brojevi korisnika:");
			for(String key : banka.vratiBazu().keySet())
				System.out.println("Korisnik { " + key + " }");
			
			Naming.rebind(link, banka);
			System.out.println("\nServer je pokrenut na: " + link);
			System.out.println("Pritisnite enter za kraj...");
			Scanner console = new Scanner(System.in);
			console.nextLine();
			console.close();
		}
		catch(Exception e){
			System.out.println("Nastala je greska: " + e);
		}
		
		System.exit(0);
	}
}