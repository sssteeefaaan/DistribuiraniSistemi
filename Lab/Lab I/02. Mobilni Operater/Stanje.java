import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Stanje extends Remote{
	
	public int vratiMinute() throws RemoteException;
	public int vratiPoruke() throws RemoteException;
	public int vratiInternet() throws RemoteException;
	public float vratiRacun() throws RemoteException;
	
	public String vratiBroj() throws RemoteException;
	public boolean uplatiMinute(int minuti, int tarifa) throws RemoteException;
	public boolean uplatiPoruke(int poruke, int tarifa) throws RemoteException;
	public boolean uplatiInternet(int internet, int tarifa) throws RemoteException;
}