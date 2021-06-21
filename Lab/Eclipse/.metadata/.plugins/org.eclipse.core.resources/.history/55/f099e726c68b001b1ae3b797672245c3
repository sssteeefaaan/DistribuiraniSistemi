package glavni;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StudentImpl extends UnicastRemoteObject implements Student{
	
	private static final long serialVersionUID = 1L;
	private String brojIndeksa;
	private String ime;
	private String prezime;
	private Prijava prijava;
	
	public StudentImpl(String brojIndeksa, String ime, String prezime) throws RemoteException{
		super();
		
		this.brojIndeksa = brojIndeksa;
		this.ime = ime;
		this.prezime = prezime;
		this.prijava = new PrijavaImpl();
	}
	
	public Prijava vratiPrijavu() throws RemoteException{
		return this.prijava;
	}
	
	public boolean prijaviIspit(Ispit ispit) throws RemoteException{
		return this.prijava.prijaviIspit(ispit);
	}
	
	public String vratiBrojIndeksa() throws RemoteException{
		return this.brojIndeksa;
	}
	public String vratiIme() throws RemoteException{
		return this.ime;
	}
	public String vratiPrezime() throws RemoteException{
		return this.prezime;
	}
}