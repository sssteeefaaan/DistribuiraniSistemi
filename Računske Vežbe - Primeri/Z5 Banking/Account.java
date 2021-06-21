import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Account extends Remote {

 public BankManager getBankManager()  throws RemoteException;
 
 public Client getClient()  throws RemoteException;
 
 public long getBalance()  throws RemoteException;
 
 public long getCash (long amount)  throws NoCashAvailableException,RemoteException;
 
}