package glavni;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Ispit extends Remote{

	String vratiNaziv() throws RemoteException;
	int vratiBrojPrijava() throws RemoteException;
	
	void obavestiSve() throws RemoteException;
	void registruj(EStudSluzbaCallback povratniPoziv) throws RemoteException;
	void odjavi(EStudSluzbaCallback povratniPoziv) throws RemoteException;
}
