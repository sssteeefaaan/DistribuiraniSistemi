import java.rmi.*;
import java.util.Hashtable;
import java.rmi.registry.*;
import java.util.Random;
import java.util.Scanner;

public class Server{
	public Server(){}
	
	public static void main(String[] args){
		
		String host = "localhost";
		String port = "1099";
		String service = "eStudentskaSluzba";
		String link = "rmi://" + host + ":" + port + "/" + service;
		
		EStudSluzba sluzba = null;
		
		try{
			LocateRegistry.createRegistry(Integer.parseInt(port));
			System.out.println("Java RMI Registar je uspesno kreiran!");
			
			sluzba = new EStudSluzbaImpl();
			System.out.println("\nBaza kreirana!");
			
			Random rand = new Random();
			
			int i = 0;
			while(i < 20)
				if(sluzba.dodajStudenta(new StudentImpl(String.valueOf(16980 + rand.nextInt(700)))))
					i++;
				
			System.out.println("\nPodaci su uneti u bazu!");
			
			System.out.println("\nPregled brojeva indeksa:");
			Hashtable<String, Student> baza = sluzba.vratiBazu();
			i = 1;
			for(String indeks : baza.keySet())
				System.out.println(String.valueOf(i++) + ". Student: { " + indeks + " }");
			
			Naming.rebind(link, sluzba);
			System.out.println("\nServer je pokrenut na adresi: " + link);
			System.out.println("Pritisnite enter za kraj...");
			Scanner console = new Scanner(System.in);
			console.nextLine();
			console.close();
		}
		catch(Exception e)
		{
			System.out.println("Nastala je greska!\n" + e);
		}
		
		System.out.println("\nServer se gasi...");
		System.exit(0);
	}
}