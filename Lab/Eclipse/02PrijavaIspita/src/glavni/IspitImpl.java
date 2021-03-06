package glavni;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class IspitImpl extends UnicastRemoteObject implements Ispit{

	private static final long serialVersionUID = 1L;
	private String naziv;
	private int brojPrijava;
	private ArrayList<EStudSluzbaCallback> studenti;
	
	public IspitImpl(String naziv) throws RemoteException{
		super();
		
		this.naziv = naziv;
		this.brojPrijava = 0;
		this.studenti = new ArrayList<EStudSluzbaCallback>();
	}
	public String vratiNaziv() throws RemoteException{
		return this.naziv;
	}
	public int vratiBrojPrijava() throws RemoteException{
		return this.brojPrijava;
	}
	
	public void obavestiSve() throws RemoteException{
		for(EStudSluzbaCallback student : this.studenti)
			student.callback(naziv, brojPrijava);
	}
	public synchronized void registruj(EStudSluzbaCallback povratniPoziv) throws RemoteException {
		if(this.studenti.contains(povratniPoziv))
			return;
		this.brojPrijava++;
		this.obavestiSve();
		this.studenti.add(povratniPoziv);
	}
	public synchronized void odjavi(EStudSluzbaCallback povratniPoziv) throws RemoteException {
		if(this.studenti.remove(povratniPoziv))
			this.brojPrijava--;
	}
}