import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class Klijent{
	
	public Klijent(){}
	
	public static void main(String[] args){
		
		String host = "localhost";
		String port = "1099";
		String service = "Aukcija";
		String link = "rmi://" + host + ":" + port + "/" + service;
		
		EAukcija aukcija = null;
		
		try{
			
			aukcija = (EAukcija) Naming.lookup(link);
			System.out.println("Connected to the server!");
		}
		catch(Exception e)
		{
			System.out.println("Couldn't connect to the server!");
		}
		
		try{
			
			String idEksponata = "", naziv, temp;
			KlijentAukcije klijent = null;
			Eksponat eksponat = null;
			int cena;
			
			while(true){
				System.out.println("Dobro dosli na elektronsku aukciju.");
				System.out.println("\nIzaberite opciju:");
				System.out.println("a) Prijava");
				System.out.println("b) Kraj");
				temp = System.console().readLine();
				
				if(temp.charAt(0) == 'b'){
					System.out.println("\nKraj");
					break;
				}
				
				System.out.println("\nZa nastavak unesite licne podatke:");
				klijent = new KlijentAukcije(System.console().readLine("Identifikator:"), System.console().readLine("Ime:"), System.console().readLine("Prezime:"));
				
				while(true){
					idEksponata = System.console().readLine("\nUnesite identifikator za eksponat od interesa:");
					eksponat = aukcija.vratiEksponat(idEksponata);
					
					while(eksponat == null){
						eksponat = aukcija.vratiEksponat(System.console().readLine("\nUneli ste neispravan ID, pokusajte ponovo..."));
					}
					
					naziv = eksponat.vratiNaziv();
					cena = eksponat.vratiCenu();
					
					System.out.println("\nNaziv eksponata: " + naziv);
					System.out.println("Cena eksponata: " + cena);
					
					System.out.println("\nIzaberite opciju:");
					System.out.println("a) Licitacija");
					System.out.println("b) Odustajanje");
					
					temp = System.console().readLine();
					
					switch(temp){
						case "a":
							eksponat.prijaviLicitaciju(klijent);
							temp = System.console().readLine("\nUnesite za koliko uvecavate cenu eksponata: ");
							eksponat.povecajCenu(cena + Integer.parseInt(temp));
						break;
						case "b":
							eksponat.odustaniOdLicitacije(klijent.id);
							System.out.println("\nUspesno ste odustali od eksponenta!");
						break;
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Error occurred: " + e);
		}
		
		System.exit(0);
	}
}