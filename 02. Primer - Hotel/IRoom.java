import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRoom extends Remote
{
	public int getPrice() throws RemoteException;
	public int getNumberOfBeds() throws RemoteException;
	public Status getStatus() throws RemoteException;
	public boolean reserve(Guest guest) throws RemoteException;
}