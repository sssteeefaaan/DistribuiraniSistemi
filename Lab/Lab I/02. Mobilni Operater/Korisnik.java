import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Korisnik extends Remote{
	
	public boolean uplatiMinute(int minuti) throws RemoteException;
	public boolean uplatiPoruke(int poruke) throws RemoteException;
	public boolean uplatiInternet(int internet) throws RemoteException;
	public Stanje vratiStanje() throws RemoteException;
}