import java.rmi.*;
import java.net.MalformedURLException;
import java.io.* ;

public class FacultyManagerClient
{
 
 private FacultyManager FM;
 
 public FacultyManagerClient(String host, String port, String server)
 {
	try
	{
		FM = (FacultyManager)Naming.lookup("rmi://"+host + ":" + port + "/" + server);
	
	} catch (MalformedURLException malformedException) {
	
		System.err.println("Bad URL: " + malformedException);
	
	} catch (NotBoundException notBoundException) {
		
		System.err.println("Not Bound: " + notBoundException);
		
	} catch (RemoteException remoteException) {
		System.err.println("Remote Exception: " +remoteException);
	}
 
	try
	{		
		Unos unos = new Unos(); //pomocna klasa za unos sa tastature

		String brind = unos.getUserInput("Unesi broj indeksa: ");
		String iip = unos.getUserInput("Unesi ime i prezime: ");
		String email = unos.getUserInput("Unesi email: ");
		
		Student stud = new Student(brind, iip, email);
		String sifra = unos.getUserInput("Unesi sifru ispita koji student zeli da prijavi: ");
		
		Ispit zeljeniIspit = FM.PronadjiIspit(sifra);
		zeljeniIspit.PrijaviIspit(stud);
		
		int brojprijavljenih = zeljeniIspit.VratiBrojPrijavljenihStudenata();
		System.out.println("Broj prijavljenih studenata je: " + brojprijavljenih);

	} catch (RemoteException remoteException) {		
		System.err.println(remoteException);		
	}
 }
 
 public static void main(String[] args) {

	String host = args[0];
	String port = args[1];
	String server = args[2];
	
	new FacultyManagerClient(host, port, server);
 }
 
}