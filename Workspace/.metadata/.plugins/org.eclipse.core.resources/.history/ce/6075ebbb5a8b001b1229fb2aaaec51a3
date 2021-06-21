import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StudentImpl extends UnicastRemoteObject implements Student{
	
	private String brojIndeksa;
	private Prijava prijava;
	
	public StudentImpl(String brojIndeksa) throws RemoteException{
		super();
		
		this.brojIndeksa = brojIndeksa;
		this.prijava = new PrijavaImpl();
	}
	
	public Prijava vratiPrijavu() throws RemoteException{
		return this.prijava;
	}
	
	public boolean prijaviIspit(String ispit) throws RemoteException{
		return this.prijava.prijaviIspit(ispit);
	}
	
	public String vratiBrojIndeksa() throws RemoteException{
		return this.brojIndeksa;
	}
}