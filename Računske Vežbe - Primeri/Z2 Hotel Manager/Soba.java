import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Soba extends Remote {
	
 public int kojacena() throws RemoteException;
 
 public int kolikokreveta() throws RemoteException;
 
 public boolean status() throws RemoteException;
 
 public void rezervacija(Putnik cl) throws RemoteException;
 
}