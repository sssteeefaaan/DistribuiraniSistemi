import java.rmi.*;
import java.util.Scanner;

public class Klijent{
	
	public Klijent(){}
	
	public static void main(String[] args){
		
		String host = "localhost";
		String port = "1099";
		String service = "eStudentskaSluzba";
		String link = "rmi://" + host + ":" + port + "/" + service;
		
		EStudSluzba sluzba = null;
		
		try{
			sluzba = (EStudSluzba) Naming.lookup(link);
			System.out.println("\nUspesno povezanje na server: " + link + " i referenciranje objekta eStudentskaSluzba!");
			
			String option, brojIndeksa, ispit;
			Student student = null;
			Scanner console = new Scanner(System.in);
			boolean runningMenu = true, runningInteraction;
			while(runningMenu){
				
				System.out.println("\nDobro dosli u korisnicki servis studentske sluzbe!");
				System.out.println("\nIzaberite jednu od opcija glavnog menija:");
				System.out.println("a) Prijavljinje");
				System.out.println("b) Kraj");
				
				System.out.print("\n>");
				option = console.nextLine();
				switch(option.charAt(0)){
					
					case 'a':
						System.out.println("\nIzabrali ste opciju Prijavljinje!");
						System.out.println("Unesite broj indeksa");
						
						brojIndeksa = console.nextLine();
						student = sluzba.vratiStudenta(brojIndeksa);
						
						while(student == null) {
							System.out.print("\nGreska pri unosu broja indeksa!\nStudent sa tim indeksom ne postoji!\nPokusajte ponovo!");
							brojIndeksa = console.nextLine();
							student = sluzba.vratiStudenta(brojIndeksa);
						}
						
						System.out.println("Uspesno ste se prijavili sa brojem indeksa { " + brojIndeksa + " }!");
						
						runningInteraction = true;
						while(runningInteraction){
							System.out.println("\nZa nastavak izaberite jednu od opcija:");
							System.out.println("a) Prijava ispita");
							System.out.println("b) Ponistavanje prijave");
							System.out.println("c) Pregled prijavljenih ispita");
							System.out.println("d) Nazad na glavni meni");
							
							System.out.print("\n>");
							option = console.nextLine();
							switch(option.charAt(0)){
								
								case 'a':
									System.out.println("\nIzabrali ste opciju prijavljivanja ispita!");
									System.out.println("Unesite naziv ispita koji zelite da prijavite!");
									System.out.print("\n>");
									ispit = console.nextLine();
									
									if(student.vratiPrijavu().prijaviIspit(ispit))
										System.out.println("\nUspesno ste prijavili ispit '" + ispit + "'");
									else{
										System.out.println("\nGreska pri prijavljivanju ispita '" + ispit + "'");
										System.out.println("Proverite da li je ovaj ispit u listi prijavljenih ispita!");
									}
								break;
								case 'b':
									System.out.println("\nIzabrali ste opciju ponistavanja prijave!");
									System.out.println("Unesite naziv ispita koji zelite da otkazete!");
									System.out.print("\n>");
									ispit = console.nextLine();
									
									if(student.vratiPrijavu().ponistiIspit(ispit))
										System.out.println("\nUspesno ste ponistili ispit '" + ispit + "'");
									else{
										System.out.println("\nGreska pri ponistavanju ispita '" + ispit + "'");
										System.out.println("Proverite da li je ovaj ispit u listi prijavljenih ispita!");
									}
								break;
								case 'c':
									System.out.println("\nIzabrali ste opciju pregleda prijavljenih ispita!");
									String temp = student.vratiPrijavu().vratiPrijavljeneIspite();
									if(!temp.isEmpty())
										System.out.println("Prijavljeni ispiti:\n" + temp);
									else
										System.out.println("Nemate prijavljene ispite!");
								break;
								case 'd':
									System.out.println("\nIzabrali ste opciju povratka na glavni meni!");
									runningInteraction = false;
								break;
								default:
									System.out.println("\nGreska pri unosu!\nUnesite slovo pored zeljene opcije!");
								break;
							}
						}
						System.out.println("--------------------------------------------------------------------------------");
					break;
					case 'b':
						System.out.println("\nIzabrali ste opciju Kraj!");
						runningMenu = false;
					break;
					default:
						System.out.println("\nGreska pri unosu!\nUnesite slovo pored zeljene opcije!");
					break;
				}
			}
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println("\nPrijatno!");
			
			console.close();
		}
		catch(Exception e){
			System.out.println("Nastala je greska: " + e);
		}
		
		System.exit(0);
	}
}