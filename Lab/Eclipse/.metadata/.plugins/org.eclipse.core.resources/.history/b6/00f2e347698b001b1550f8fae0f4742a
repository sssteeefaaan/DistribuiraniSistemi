import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;

public interface EAukcija extends Remote{
	
	public Eksponat vratiEksponat(String id) throws RemoteException;
	public boolean dodajEksponat(Eksponat eksponat) throws RemoteException;
	
	
	public Hashtable<String, Eksponat> vratiEksponate() throws RemoteException;
}