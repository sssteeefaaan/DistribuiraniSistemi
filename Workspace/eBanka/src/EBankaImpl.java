import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;

public class EBankaImpl extends UnicastRemoteObject implements EBanka{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hashtable<String, Korisnik> baza;
	private float kurs;
	
	public EBankaImpl(float kurs) throws RemoteException{
		super();
		
		this.kurs = kurs;
		this.baza = new Hashtable<String, Korisnik>();
	}
	
	public Korisnik vratiKorisnika(String jbk) throws RemoteException{
		return this.baza.get(jbk);
	}
	public boolean dodajKorisnika(Korisnik korisnik) throws RemoteException{
		String ID = korisnik.vratiJBK();
		
		if(this.baza.get(ID) == null){
			this.baza.put(ID, korisnik);
			return true;
		}
		
		return false;
	}
	public Hashtable<String, Korisnik> vratiBazu() throws RemoteException{
		return this.baza;
	}
	public float vratiKurs() throws RemoteException{
		return this.kurs;
	}
}