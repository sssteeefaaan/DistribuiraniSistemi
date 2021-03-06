package glavni;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Klijent{
	
	private EAukcijaManager aukcija;
	private EAukcijaCallback povratniPoziv;
	public Klijent(){
	}
	
	public void run(String link) {
		
		this.aukcija = null;
		
		try{
			
			this.aukcija = (EAukcijaManager) Naming.lookup(link);
			System.out.println("Povezan na server!");
			
			this.povratniPoziv = new EAukcijaCallbackImpl();
		}
		catch(Exception e)
		{
			System.out.println("Nije mogla da se uspostavi veza sa serverom!");
		}
		
		try{
			
			String idEksponata = "", temp;
			boolean runningMain, runningInteraction;
			KlijentAukcije klijent = null;
			Eksponat eksponat = null;
			
			Scanner console = new Scanner(System.in);
			runningMain = true;
			while(runningMain){
				System.out.println("Dobro dosli na elektronsku aukciju!");
				System.out.println("\nIzaberite opciju:");
				System.out.println("a) Prijava");
				System.out.print("b) Kraj\n\n>");
				temp = console.nextLine();
				
				switch(temp.charAt(0)) {
				
					case 'a':
						System.out.println("\nZa nastavak unesite licne podatke:");
						System.out.print("Identifikator: ");
						String tempID = console.nextLine();
						System.out.print("Ime: ");
						String tempIme = console.nextLine();
						System.out.print("Prezime: ");
						String tempPrezime = console.nextLine();
						klijent = new KlijentAukcije(tempID, tempIme, tempPrezime);
						
						runningInteraction = true;
						while(runningInteraction){
							System.out.println("\nUnesite identifikator za eksponat od interesa:");
							idEksponata = console.nextLine();
							eksponat = this.aukcija.vratiEksponat(idEksponata);
							
							while(eksponat == null){
								System.out.println("\nUneli ste neispravan ID, pokusajte ponovo...");
								idEksponata = console.nextLine();
								eksponat = this.aukcija.vratiEksponat(idEksponata);
							}
							
							this.prikaziInfoOEksponatu(eksponat);
							
							System.out.println("\nIzaberite opciju:");
							System.out.println("a) Licitacija");
							System.out.print("b) Odustajanje\n\n>");
							
							temp = console.nextLine();
							
							switch(temp){
								case "a":
									eksponat.prijaviLicitaciju(klijent);
									System.out.println("\nUnesite za koliko uvecavate cenu eksponata: ");
									temp = console.nextLine();
									if(eksponat.povecajCenu(Integer.parseInt(temp))) {
										System.out.println("Uspesno ste povecali cenu!");
										eksponat.registruj(this.povratniPoziv);
									}
									else
										System.out.println("Greska pri povecanju cene!");
								break;
								case "b":
									if(eksponat.odustaniOdLicitacije(klijent.vratiID())) {
										eksponat.odjavi(this.povratniPoziv);
										System.out.println("\nUspesno ste odustali od eksponenta!");
									}
									else
										System.out.println("\nGreska pri odjavljivanju sa licitacije!");
								break;
							}
						}
						break;
					case 'b':
							System.out.println("\nIzabrali ste opciju Kraj!");
							runningMain = false;
						break;
					default:
							System.out.println("\nGreska pri unosu!\nUnesite slovo pored zeljene opcije!");
						break;
				}
			}
			System.out.println("\nPrijatno!");
			console.close();
		}
		catch(Exception e)
		{
			System.out.println("Nastala greska: " + e);
		}
	}
	
	private void prikaziInfoOEksponatu(Eksponat eksponat) throws RemoteException{
		System.out.println("\nNaziv eksponata: " + eksponat.vratiNaziv());
		System.out.println("Cena eksponata: " + eksponat.vratiCenu());
		System.out.println("Identifikator eksponata: " + eksponat.vratiID());
	}

	public static void main(String[] args){
		
		String host = "localhost";
		String port = "1099";
		String service = "eAukcija";
		String link = "rmi://" + host + ":" + port + "/" + service;
		
		Klijent instanca = new Klijent();
		instanca.run(link);
		
		System.exit(0);
	}
	
	public class EAukcijaCallbackImpl extends UnicastRemoteObject implements EAukcijaCallback{
		
		private static final long serialVersionUID = 1L;
		
		public EAukcijaCallbackImpl() throws RemoteException{}
		public void callback(int version, Eksponat eksponat) throws RemoteException
		{
			prikaziInfoOEksponatu(eksponat);
		}
	}
}