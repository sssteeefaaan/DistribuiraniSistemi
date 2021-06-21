package glavni;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Student extends Remote{
	
	Prijava vratiPrijavu() throws RemoteException;
	boolean prijaviIspit(Ispit ispit) throws RemoteException;
	String vratiBrojIndeksa() throws RemoteException;
	String vratiIme() throws RemoteException;
	String vratiPrezime() throws RemoteException;
}