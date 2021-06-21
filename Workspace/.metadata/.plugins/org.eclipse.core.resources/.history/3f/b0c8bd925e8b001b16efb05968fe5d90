import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class KorisnikImpl extends UnicastRemoteObject implements Korisnik{
	
	private Stanje stanje;
	private String jbk;
	
	
	public KorisnikImpl(String jbk, float iznosDinarski, float iznosDevizni) throws RemoteException{
		super();
		
		this.jbk = jbk;
		this.stanje = new StanjeImpl(iznosDinarski, iznosDevizni);
	}
	public Stanje vratiStanje() throws RemoteException{
		return this.stanje;
	}
	public boolean transferDinarskiNaDevizni(float iznos, float kurs) throws RemoteException{
		return this.stanje.prebaciNaDevizni(iznos, kurs);
	}
	public boolean transferDevizniNaDinarski(float iznos, float kurs) throws RemoteException{
		return this.stanje.prebaciNaDinarski(iznos, kurs);
	}
	public String vratiJBK() throws RemoteException{
		return this.jbk;
	}
}