import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;

public interface EBanka extends Remote{
	
	Korisnik vratiKorisnika(String jbk) throws RemoteException;
	boolean dodajKorisnika(Korisnik korisnik) throws RemoteException;
	Hashtable<String, Korisnik> vratiBazu() throws RemoteException;
	float vratiKurs() throws RemoteException;
}