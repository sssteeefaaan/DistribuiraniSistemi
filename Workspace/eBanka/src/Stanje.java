import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Stanje extends Remote{
	
	float vratiDinarskiIznos() throws RemoteException;
	float vratiDevizniIznos() throws RemoteException;
	boolean prebaciNaDevizni(float iznos, float kurs) throws RemoteException;
	boolean prebaciNaDinarski(float iznos, float kurs) throws RemoteException;
}