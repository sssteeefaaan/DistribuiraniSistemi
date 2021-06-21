package glavni;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Prijava extends Remote{
	
	ArrayList<Ispit> vratiPrijavljeneIspite() throws RemoteException;
	boolean prijaviIspit(Ispit ispit) throws RemoteException;
	boolean ponistiIspit(Ispit ispit) throws RemoteException;
}