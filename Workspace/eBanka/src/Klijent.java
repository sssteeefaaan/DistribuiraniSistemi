import java.rmi.*;
import java.util.Scanner;

public class Klijent{
	
	public Klijent(){}
	
	public static void main(String[] args){
		
		String host = "localhost";
		String port = "1099";
		String service = "eBanka";
		String link = "rmi://" + host + ":" + port + "/" + service;
		EBanka banka = null;
		
		try{
			banka = (EBanka) Naming.lookup(link);
			System.out.println("Uspesno konektovan na server: " + link + " i referenciran objekat eBanke!");
			
			System.out.println("\nDobro dosli u eBank korisnicki servis!");
			
			String option, jbk, iznos;
			Korisnik korisnik = null;
			boolean runningMain = true, runningInteraction;
			
			Scanner console = new Scanner(System.in);
			while(runningMain){
				
				System.out.println("--- Trenutni kurs iznosi: " + banka.vratiKurs() + " rsd/devizi ---");
				System.out.println("\nZa nastavak izaberite opciju:");
				System.out.println("a) Prijava");
				System.out.println("b) Kraj");
				
				System.out.print("\n>");
				option = console.nextLine();
				switch(option.charAt(0)){
					case 'a':
						System.out.println("\nIzabrali ste opciju za Prijavu!");
						System.out.print("\nUnesite jedinstveni broj korsinika\n>");
						jbk = console.nextLine();
						korisnik = banka.vratiKorisnika(jbk);
						
						while(korisnik == null) {
							System.out.print("\nKorisnik sa JBK-om {" + jbk + "} ne postoji!\nPokusajte ponovo...\n>");
							jbk = console.nextLine();
							korisnik = banka.vratiKorisnika(jbk);
						}
							
						System.out.println("\nUspesno ste se prijavili sa ID-om {" + jbk + "}!");
						
						runningInteraction = true;
						while(runningInteraction){
							System.out.println("--- Trenutni kurs iznosi: " + banka.vratiKurs() + " rsd/devizi ---");
							System.out.println("\nZa nastavak izaberite opciju:");	
							System.out.println("a) Transfer sa dinarskog na devizni racun");
							System.out.println("b) Transfer sa deviznog na dinarski racun");
							System.out.println("c) Provera stanja racuna");
							System.out.println("d) Nazad");
							
							System.out.print("\n>");
							option = console.nextLine();
								
							switch(option.charAt(0)){
								case 'a':
									System.out.println("\nIzabrali ste opciju za transfer sa dinarskog na devizni racun");
									System.out.print("Unesite zeljeni iznos u dinarima:\n>");
									iznos = console.nextLine();
									
									if(korisnik.transferDinarskiNaDevizni(Float.parseFloat(iznos), 1/banka.vratiKurs()))
										System.out.println("\nUspesno ste prebacili novac sa dinarskog na devizni racun!");
									else
										System.out.println("\nGreska, nista nije promenjeno!");
								break;
								case 'b':
									System.out.println("\nIzabrali ste opciju za transfer sa deviznog na dinarski racun");
									System.out.print("Unesite zeljeni iznos u devizama:\n>");
									iznos = console.nextLine();
									
									if(korisnik.transferDevizniNaDinarski(Float.parseFloat(iznos), banka.vratiKurs()))
										System.out.println("\nUspesno ste prebacili novac sa deviznog na dinarski racun!");
									else
										System.out.println("\nGreska, nista nije promenjeno!");
								break;
								case 'c':
									System.out.println("\nIzabrali ste opciju za proveru stanja.");
									System.out.println("Stanje Vaseg racuna:");
									System.out.println("Dinari: " + String.valueOf(korisnik.vratiStanje().vratiDinarskiIznos()));
									System.out.println("Devize: " + String.valueOf(korisnik.vratiStanje().vratiDevizniIznos()));
								break;
								case 'd':
									System.out.println("\nIzabrali ste opciju za povratak na prethodni Menu!");
									runningInteraction = false;
								break;
								default:
									System.out.println("\nGreska pri unosu!\nUnesite malo slovo opcije koja Vas interesuje!");
								break;
							}
							System.out.println("--------------------------------------------------------------------------------");
						}
						System.out.println("--------------------------------------------------------------------------------");
					break;
					case 'b':
						System.out.println("\nIzabrali ste opciju za Kraj!");
						runningMain = false;
					break;
					default:
						System.out.println("\nGreska pri unosu!\nUnesite malo slovo opcije koja Vas interesuje!");
					break;
				}
			}
			System.out.println("\nPrijatno!");
			console.close();
		}
		catch(Exception e)
		{
			System.out.println("Error occurred: " + e);
		}
		
		System.exit(0);
	}
}