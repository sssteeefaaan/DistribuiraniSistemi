import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BankManager extends Remote {

 public Account getAccount(String accountNumber) throws RemoteException;

 public Client getClient(String clientName) throws RemoteException;
 
}