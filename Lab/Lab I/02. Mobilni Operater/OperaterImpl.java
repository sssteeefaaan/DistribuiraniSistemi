import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;

public class OperaterImpl extends UnicastRemoteObject implements Operater{
	
	private Hashtable<String, Korisnik> baza;
	
	public OperaterImpl() throws RemoteException{
		this.baza = new Hashtable<String, Korisnik>();
	}
	
	public Korisnik vratiKorisnika(String broj) throws RemoteException{
		return this.baza.get(broj);
	}
	
	public boolean dodajKorisnika(Korisnik korisnik) throws RemoteException{
		
		String broj = korisnik.vratiStanje().vratiBroj();
		
		if(this.baza.get(broj) != null)
			return false;
		
		this.baza.put(broj, korisnik);
		
		return true;
	}
}