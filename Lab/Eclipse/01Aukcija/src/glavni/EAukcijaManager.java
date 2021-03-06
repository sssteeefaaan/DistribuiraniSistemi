package glavni;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;

public interface EAukcijaManager extends Remote{
	
	Eksponat vratiEksponat(String id) throws RemoteException;
	boolean dodajEksponat(Eksponat eksponat) throws RemoteException;
	
	Hashtable<String, Eksponat> vratiBazu() throws RemoteException;
}