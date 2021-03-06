import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;

public class EAukcijaImpl extends UnicastRemoteObject implements EAukcija{
	
	private Hashtable<String, Eksponat> eksponati;
	
	
	public EAukcijaImpl() throws RemoteException{
		super();
		this.eksponati = new Hashtable<String, Eksponat>();
	}
	
	public Eksponat vratiEksponat(String id) throws RemoteException{
		return this.eksponati.get(id);
	}
	
	public boolean dodajEksponat(Eksponat eksponat) throws RemoteException{
		
		String ID = eksponat.vratiID();
		if(this.eksponati.get(ID) == null)
		{
			this.eksponati.put(ID, eksponat);
			return true;
		}
		
		return false;
	}
	
	public Hashtable<String, Eksponat> vratiEksponate() throws RemoteException{
		return this.eksponati;
	}
}