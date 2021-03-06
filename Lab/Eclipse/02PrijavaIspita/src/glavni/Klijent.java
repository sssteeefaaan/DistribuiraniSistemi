package glavni;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

public class Klijent{
	
	private EStudSluzba sluzba;
	private EStudSluzbaCallback povratniPoziv;
	public Klijent(){
		this.sluzba = null;
		this.povratniPoziv = null;
	}
	
	public void pokreni(String link) {
		try {
			
			this.sluzba = (EStudSluzba) Naming.lookup(link);
			System.out.println("\nUspesno povezanje na server: " + link + " i referenciranje objekta eStudentskaSluzba!");
			
			this.povratniPoziv = new EStudSluzbaCallbackImpl();
			
			String option, brojIndeksa, nazivIspita;
			Student student = null;
			Ispit ispit = null;
			boolean runningMenu = true, runningInteraction;
			Scanner console = new Scanner(System.in);
			while(runningMenu){
				
				System.out.println("\nDobro dosli u korisnicki servis studentske sluzbe!");
				System.out.println("\nIzaberite jednu od opcija glavnog menija:");
				System.out.println("a) Prijavljinje");
				System.out.println("b) Kraj");
				
				System.out.print("\n>");
				option = console.nextLine();
				switch(option.charAt(0)){
					
					case 'a':
						System.out.println("\nIzabrali ste opciju Prijavljivanje!");
						System.out.println("Unesite broj indeksa");
						
						System.out.print("\n>");
						brojIndeksa = console.nextLine();
						student = this.sluzba.vratiStudenta(brojIndeksa);
						
						while(student == null) {
							System.out.print("\nGreska pri unosu broja indeksa!");
							System.out.println("Student sa tim indeksom ne postoji!");
							System.out.println("Pokusajte ponovo!");
							
							System.out.print("\n>");
							brojIndeksa = console.nextLine();
							student = this.sluzba.vratiStudenta(brojIndeksa);
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
									nazivIspita = console.nextLine();
									ispit = this.sluzba.vratiIspit(nazivIspita);
									
									while(ispit == null) {
										System.out.print("\nGreska pri unosu naziva ispita!");
										System.out.println("Ispit sa tim imenom ne postoji!");
										System.out.println("Pokusajte ponovo!");
										
										System.out.print("\n>");
										nazivIspita = console.nextLine();
										ispit = this.sluzba.vratiIspit(nazivIspita);
									}
									
									if(student.vratiPrijavu().prijaviIspit(ispit)) {
										System.out.println("\nUspesno ste prijavili ispit '" + ispit.vratiNaziv() + "'");
										ispit.registruj(this.povratniPoziv);
									}
									else{
										System.out.println("\nGreska pri prijavljivanju ispita '" + ispit + "'");
										System.out.println("Proverite da li je ovaj ispit u listi prijavljenih ispita!");
									}
								break;
								case 'b':
									System.out.println("\nIzabrali ste opciju ponistavanja prijave!");
									System.out.println("Unesite naziv ispita koji zelite da otkazete!");
									
									System.out.print("\n>");
									nazivIspita = console.nextLine();
									ispit = this.sluzba.vratiIspit(nazivIspita);
									
									while(ispit == null) {
										System.out.print("\nGreska pri unosu naziva ispita!");
										System.out.println("Ispit sa tim imenom ne postoji!");
										System.out.println("Pokusajte ponovo!");
										
										System.out.print("\n>");
										nazivIspita = console.nextLine();
										ispit = this.sluzba.vratiIspit(nazivIspita);
									}
									
									if(student.vratiPrijavu().ponistiIspit(ispit)) {
										System.out.println("\nUspesno ste ponistili ispit '" + ispit.vratiNaziv() + "'");
										ispit.odjavi(this.povratniPoziv);
									}
									else{
										System.out.println("\nGreska pri ponistavanju ispita '" + ispit.vratiNaziv() + "'");
										System.out.println("Proverite da li je ovaj ispit u listi prijavljenih ispita!");
									}
								break;
								case 'c':
									System.out.println("\nIzabrali ste opciju pregleda prijavljenih ispita!");
									this.prikaziPrijavljeneIspite(student);
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
	}
	private void prikaziPrijavljeneIspite(Student student) throws RemoteException {
		System.out.println("\nStudent { " + student.vratiBrojIndeksa() + " }:");
		System.out.println("*Broj indeksa: " + student.vratiBrojIndeksa());
		System.out.println("*Ime: " + student.vratiIme());
		System.out.println("*Prezime: " + student.vratiPrezime());
		ArrayList<Ispit> prijavljeniIspiti = student.vratiPrijavu().vratiPrijavljeneIspite();
		if(!prijavljeniIspiti.isEmpty()) {
			System.out.println("*Prijavljeni ispiti:");
			for(Ispit i : prijavljeniIspiti) {
				System.out.println("\n\tNaziv ispita: " + i.vratiNaziv());
				System.out.println("\tBroj prijavljenih studenata: " + String.valueOf(i.vratiBrojPrijava()));
			}
		}
		else
			System.out.println("*Nemate prijavljene ispite!");
	}

	public static void main(String[] args){
		
		String host = "localhost";
		String port = "1099";
		String service = "eStudentskaSluzba";
		String link = "rmi://" + host + ":" + port + "/" + service;
		
		Klijent instanca = new Klijent();
		instanca.pokreni(link);
		
		System.exit(0);
	}
	
	public class EStudSluzbaCallbackImpl extends UnicastRemoteObject implements EStudSluzbaCallback{
		
		private static final long serialVersionUID = 1L;

		public EStudSluzbaCallbackImpl()throws RemoteException{
			super();
		}
		
		public void callback(String ispit, int brojStudenata) throws RemoteException{
			System.out.println("\n******************************************* Obavestenje! *******************************************");
			System.out.println("Ispit '" + ispit + "' je prijavilo " + String.valueOf(brojStudenata) + " studenta!");
			System.out.println("****************************************************************************************************");
		}
	}
}