import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Student extends Remote{
	
	Prijava vratiPrijavu() throws RemoteException;
	boolean prijaviIspit(String ispit) throws RemoteException;
	String vratiBrojIndeksa() throws RemoteException;
}