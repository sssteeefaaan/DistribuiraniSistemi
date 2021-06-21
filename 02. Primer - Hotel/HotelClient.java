import java.rmi.*;
import java.net.MalformedURLException;
import java.io.*;

public class HotelClient
{
	private IHotelManager hotel;
	
	public HotelClient(String host, String port, String service) throws Exception
	{
	   this.hotel = (IHotelManager) Naming.lookup("rmi://" + host + ":" + port + "/" + service);
	}
	public IHotelManager getHotel() {return this.hotel;}
	
	public static void main(String[] args){
		
		try{
			HotelClient client = new HotelClient(args[0], args[1], args[2]);
			
			Guest me = new Guest(System.console().readLine("First name: "), System.console().readLine("Last name: "), System.console().readLine("Citizen ID: "));
			
			String price = System.console().readLine("Room price: ");
			String numberOfBeds = System.console().readLine("Number of beds: ");
			
			IRoom r = client.getHotel().findRoom(Integer.parseInt(price), Integer.parseInt(numberOfBeds));
			
			if (r == null)
				System.out.println("No rooms!");
			else
			{
				r.reserve(me);
				System.out.println("Room reserved!");
			}
		}
		catch(Exception e){
			System.out.println("Error occurred: " + e);
		}
	}
}