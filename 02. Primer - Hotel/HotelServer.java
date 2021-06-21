import java.io.IOException;
import java.rmi.*;
import java.net.MalformedURLException;
import java.rmi.registry.*;

public class HotelServer
{
	public static void main(String args[])
	{
		String host = args[0];
		String port = args[1];
		String service = args[2];
		
		IHotelManager hotel = null;
		
		try{
			LocateRegistry.createRegistry(Integer.parseInt(port));
			System.out.println("Java RMI registry created!");
		}
		catch(RemoteException rE)
		{
			System.out.println("Java RMI registry already exists!");
		}
		
		try{
			hotel = new HotelManager(10);
			
			hotel.addRoom(new Room(500, 3));
			hotel.addRoom(new Room(300, 4));
			hotel.addRoom(new Room(800, 1));
			hotel.addRoom(new Room(250, 5));
			
			for(IRoom r : hotel.getRooms())
				if(r!=null)
					System.out.println("Room: " + Integer.toString(r.getPrice()) + ", " + Integer.toString(r.getNumberOfBeds()));
		}
		catch(RemoteException rE){
			System.out.println("Failure during the creation of the universal remote object! " + rE);
		}
		
		try{
			Naming.rebind("rmi://" + host + ":" + port + "/" + service, hotel);
		}
		catch(RemoteException rE)
		{
			System.out.println("RemoteException: " + rE);
		}
		catch(MalformedURLException mfurle)
		{
			System.out.println("MalformedURLException: " + mfurle);
		}
		
		System.out.println("Server's started...");
		System.out.println("Enter a key to exit");
		
		try{
			System.in.read();
		}
		catch(IOException ioE)
		{}
		
		System.exit(0);
	}
}