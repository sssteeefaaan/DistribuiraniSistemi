package glavni;
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

		try{
			LocateRegistry.createRegistry(Integer.parseInt(port));
			System.out.println("Java RMI Registar je uspesno kreiran!");
			
			EStudSluzbaImpl sluzba = new EStudSluzbaImpl();
			System.out.println("\nBaza kreirana!");
			
			Random rand = new Random();
			
			int i = 0;
			while(i < 20)
				if(sluzba.dodajStudenta(new StudentImpl(String.valueOf(16980 + rand.nextInt(700)), "Random ime " + String.valueOf(i), "Random prezime " + String.valueOf(i))))
					i++;
			
			sluzba.dodajIspit(new IspitImpl("Distribuirani Sistemi"));
			sluzba.dodajIspit(new IspitImpl("Racunarske Mreze"));
			sluzba.dodajIspit(new IspitImpl("Operativni Sistemi"));
			sluzba.dodajIspit(new IspitImpl("Objektno Orijentisano Proejktovanje"));
			sluzba.dodajIspit(new IspitImpl("Softversko Inzenjerstvo"));
			
			System.out.println("\nPodaci su uneti u bazu!");
			
			System.out.println("\nPregled studenata:");
			Hashtable<String, Student> studenti = sluzba.vratiStudente();
			i = 1;
			for(String indeks : studenti.keySet()) {
				System.out.println("\n" + String.valueOf(i++) + ". Student { " + indeks + " }");
				System.out.println("Ime: " + studenti.get(indeks).vratiIme());
				System.out.println("Prezime: " + studenti.get(indeks).vratiPrezime());
			}
			
			System.out.println("\nPregled ispita:");
			Hashtable<String, Ispit> ispiti = sluzba.vratiIspite();
			i = 1;
			for(String naziv : ispiti.keySet()) {
				System.out.println("\n" + String.valueOf(i++) + ". Ispit { " + naziv + " }");
				System.out.println("Naziv: " + naziv);
				System.out.println("Broj prijavljenih studenata: " + String.valueOf(ispiti.get(naziv).vratiBrojPrijava()));
			}
			
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