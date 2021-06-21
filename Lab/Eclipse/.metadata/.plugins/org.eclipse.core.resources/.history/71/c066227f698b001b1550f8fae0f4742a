import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Eksponat extends Remote{
	
	public boolean prijaviLicitaciju(KlijentAukcije klijent) throws RemoteException;
	public KlijentAukcije vratiKlijentaAukcije(String klijentAukcijeID) throws RemoteException;
	public boolean odustaniOdLicitacije(String klijentAukcijeID) throws RemoteException;
	
	public String vratiNaziv() throws RemoteException;
	public int vratiCenu() throws RemoteException;
	public void povecajCenu(int iznos) throws RemoteException;
	
	public String vratiID() throws RemoteException;
}