import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class HotelManager extends UnicastRemoteObject implements IHotelManager
{
	private IRoom[] rooms;
	private int numberOfRooms;
	private int capacity;
	
	public HotelManager(int capacity) throws RemoteException
	{
		this.capacity = capacity;
		this.numberOfRooms = 0;
		this.rooms = new IRoom[this.capacity];
	}
	
	public boolean addRoom(IRoom room) throws RemoteException
	{
		if(this.capacity > this.numberOfRooms)
		{
			this.rooms[this.numberOfRooms++] = new Room(room.getPrice(), room.getNumberOfBeds());
			return true;
		}
		
		return false;
	}
	
	public IRoom[] getRooms() throws RemoteException{return this.rooms;}
	
	public IRoom findRoom(int maxPrice, int numberOfBeds) throws RemoteException
	{
		int i = 0;
		while(i < this.numberOfRooms && !(this.rooms[i].getStatus() == Status.Available && this.rooms[i].getNumberOfBeds() == numberOfBeds && this.rooms[i].getPrice() <= maxPrice))
		{
			i++;
		}
		
		if(i < this.numberOfRooms)
			return this.rooms[i];
		
		return null;
	}
}