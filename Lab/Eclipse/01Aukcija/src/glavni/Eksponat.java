package glavni;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Eksponat extends Remote{
	
	boolean prijaviLicitaciju(KlijentAukcije klijent) throws RemoteException;
	KlijentAukcije vratiKlijentaAukcije(String klijentAukcijeID) throws RemoteException;
	boolean odustaniOdLicitacije(String klijentAukcijeID) throws RemoteException;
	
	String vratiNaziv() throws RemoteException;
	int vratiCenu() throws RemoteException;
	boolean povecajCenu(int iznos) throws RemoteException;
	
	String vratiID() throws RemoteException;
	
	int vratiVerziju() throws RemoteException;
	void registruj(EAukcijaCallback povratniPoziv) throws RemoteException;
	void odjavi(EAukcijaCallback povratniPoziv) throws RemoteException;
}