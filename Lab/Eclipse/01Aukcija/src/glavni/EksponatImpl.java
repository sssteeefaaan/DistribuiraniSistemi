package glavni;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;

public class EksponatImpl extends UnicastRemoteObject implements Eksponat{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String naziv;
	private int cena;
	private Hashtable<String, KlijentAukcije> klijenti;
	private ArrayList<EAukcijaCallback> prijavljeniKlijenti;
	private int verzija;
	
	
	public EksponatImpl(String id, String naziv, int cena) throws RemoteException{
		
		super();
		
		this.id = id;
		this.naziv = naziv;
		this.cena = cena;
		this.klijenti = new Hashtable<String, KlijentAukcije>();
		this.prijavljeniKlijenti = new ArrayList<EAukcijaCallback>();
		this.verzija = 0;
	}
	
	public boolean prijaviLicitaciju(KlijentAukcije klijent) throws RemoteException{
		String ID = klijent.vratiID();
		
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
	public boolean povecajCenu(int iznos) throws RemoteException{
		if(this.cena < this.cena + iznos) {
			this.verzija++;
			this.cena += iznos;
			this.obavestiPrijavljeneKlijente();
			return true;
		}
		return false;
	}
	
	public String vratiID() throws RemoteException{
		return this.id;
	}
	
	public int vratiVerziju() throws RemoteException{
		return this.verzija;
	}
	
	private void obavestiPrijavljeneKlijente() throws RemoteException{
		for(EAukcijaCallback povratniPoziv : this.prijavljeniKlijenti)
			povratniPoziv.callback(this.verzija, this);
	}
	
	public synchronized void registruj(EAukcijaCallback povratniPoziv) throws RemoteException {
		if(!this.prijavljeniKlijenti.contains(povratniPoziv))
			this.prijavljeniKlijenti.add(povratniPoziv);
	}

	public void odjavi(EAukcijaCallback povratniPoziv) throws RemoteException {
		this.prijavljeniKlijenti.remove(povratniPoziv);
	}
}