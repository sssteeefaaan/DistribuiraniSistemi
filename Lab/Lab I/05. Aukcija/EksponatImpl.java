import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;

public class EksponatImpl extends UnicastRemoteObject implements Eksponat{
	
	private String id;
	private String naziv;
	private int cena;
	private Hashtable<String, KlijentAukcije> klijenti;
	
	
	public EksponatImpl(String id, String naziv, int cena) throws RemoteException{
		
		super();
		
		this.id = id;
		this.naziv = naziv;
		this.cena = cena;
		this.klijenti = new Hashtable<String, KlijentAukcije>();
	}
	
	public boolean prijaviLicitaciju(KlijentAukcije klijent) throws RemoteException{
		String ID = klijent.id;
		
		if(this.klijenti.get(ID) == null){
			this.klijenti.put(ID, klijent);
			return true;
		}
		
		return false;
	}
	
	public KlijentAukcije vratiKlijentaAukcije(String id) throws RemoteException{
		return this.klijenti.get(id);
	}
	
	public boolean odustaniOdLicitacije(String klijentAukcijeID) throws RemoteException{
		return this.klijenti.remove(klijentAukcijeID) != null;
	}
	
	public String vratiNaziv() throws RemoteException{
		return this.naziv;
	}
	public int vratiCenu() throws RemoteException{
		return this.cena;
	}
	public void povecajCenu(int iznos) throws RemoteException{
		if(this.cena < this.cena + iznos)
			this.cena += iznos;
	}
	
	public String vratiID() throws RemoteException{
		return this.id;
	}
}