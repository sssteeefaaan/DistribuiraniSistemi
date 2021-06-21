import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {

 public BankManager getBankManager() throws RemoteException;

 public String getName() throws RemoteException;
 
}