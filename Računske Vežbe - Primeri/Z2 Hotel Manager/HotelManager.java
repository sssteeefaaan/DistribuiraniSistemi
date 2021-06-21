import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HotelManager extends Remote {
	
 public Soba nadjisobu(int maxc, int brkr) throws RemoteException;
 
}