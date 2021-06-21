import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IHotelManager extends Remote
{
	public IRoom findRoom(int maxPrice, int numberOfBeds) throws RemoteException;
	public IRoom[] getRooms() throws RemoteException;
	public boolean addRoom(IRoom room) throws RemoteException;
}