import java.rmi.*;
import java.rmi.server.*;
import java.util.*; 

public class Klijent{
	
	public Klijent() {}
	
	public static void main(String[] args){
		
		String host = "localhost";
		String port = "1099";
		String service = "MobilniOperater";
		String link = "rmi://" + host + ":" + port + "/" + service;
		
		try{
			Operater operater = (Operater) Naming.lookup(link);
			
			String option, number, temp;
			Korisnik korisnik = null;
			while(true){
				
				for(int j = 0 ; j < 30; j++)
					System.out.print("-");
				System.out.println("-");
				
				System.out.println("Dobro dosli u korisnicki servis mobilnog operatera!");
				System.out.println("Za nastavak, izaberite opciju:");
				System.out.println("a) Uplata Minuta");
				System.out.println("b) Uplata Poruka");
				System.out.println("c) Uplata Interneta/Megabajta");
				System.out.println("d) Provera Stanja");
				System.out.println("e) Kraj");
				
				option = System.console().readLine().trim();
				if(option.charAt(0) == 'e')
					break;
				
				number = System.console().readLine("Unesite broj telefona korisnika:").trim();
				korisnik = operater.vratiKorisnika(number);
				
				while(korisnik == null){
					
					temp = System.console().readLine("Uneti broj nije u bazi. Pokušajte ponovo y/N?");
					if(temp.trim().toLowerCase().charAt(0) == 'n')
						break;
					
					number = System.console().readLine("Unesite broj telefona korisnika: ").trim();
					korisnik = operater.vratiKorisnika(number);
				}
				if(korisnik != null)
					switch(option.charAt(0)){
						case 'a':
							System.out.println("Izbrali ste opciju za uplatu dodatnih minuta.");
							temp = System.console().readLine("Unesite broj dodatnih minuta: ");
							
							if(korisnik.uplatiMinute(Integer.parseInt(temp)))
								System.out.println("Uspesna uplata minuta!");
							else
								System.out.println("Greska pri uplati!");
							break;
						case 'b':
							System.out.println("Izbrali ste opciju za uplatu dodatnih poruka.");
							temp = System.console().readLine("Unesite broj dodatnih poruka: ");
							
							if(korisnik.uplatiPoruke(Integer.parseInt(temp)))
								System.out.println("Uspesna uplata poruka!");
							else
								System.out.println("Greska pri uplati!");
							break;
						case 'c':
							System.out.println("Izbrali ste opciju za uplatu dodatnih megabajta.");
							temp = System.console().readLine("Unesite broj dodatnih megabajta: ");
							
							if(korisnik.uplatiInternet(Integer.parseInt(temp)))
								System.out.println("Uspesna uplata interneta!");
							else
								System.out.println("Greska pri uplati!");
							break;
						case 'd':
							System.out.println("Izbrali ste opciju za proveru stanja.");
							System.out.println("Dostupni minuti: " + String.valueOf(korisnik.vratiStanje().vratiMinute()) + " min");
							System.out.println("Dostupne poruke: " + String.valueOf(korisnik.vratiStanje().vratiPoruke()) + " sms");
							System.out.println("Dostupni internet: " + String.valueOf(korisnik.vratiStanje().vratiInternet()) + " megabajta");
							System.out.println("Zaduzenje: " + String.valueOf(korisnik.vratiStanje().vratiRacun()) + " rsd");
							break;
						default:
							System.out.println("Greska pri odabiru opcija. Pokusajte ponovo...");
							break;
					}
			}
		}
		catch(Exception e)
		{
			System.out.println("Error occurred: " + e);
		}
	}
}