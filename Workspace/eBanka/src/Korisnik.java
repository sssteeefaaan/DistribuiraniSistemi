import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Korisnik extends Remote{
	
	Stanje vratiStanje() throws RemoteException;
	boolean transferDinarskiNaDevizni(float iznos, float kurs) throws RemoteException;
	boolean transferDevizniNaDinarski(float iznos, float kurs) throws RemoteException;
	
	String vratiJBK() throws RemoteException;
}