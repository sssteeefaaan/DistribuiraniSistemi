import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class Room extends UnicastRemoteObject implements IRoom
{
	private Status status;
	private Guest guest;
	private int numberOfBeds;
	private int price;
	
	public Room(int price, int numberOfBeds) throws RemoteException
	{
		this.price = price;
		this.numberOfBeds = numberOfBeds;
		this.status = Status.Available;
	}
	
	public int getNumberOfBeds() throws RemoteException
	{
		return this.numberOfBeds;
	}
	
	public int getPrice() throws RemoteException
	{
		return this.price;
	}
	
	public Status getStatus() throws RemoteException
	{
		return this.status;
	}
	
	public boolean reserve(Guest guest) throws RemoteException
	{
		if(this.status == Status.Available)
		{
			this.status = Status.Reserved;
			this.guest = guest;
			return true;
		}
		
		return false;
	}
}