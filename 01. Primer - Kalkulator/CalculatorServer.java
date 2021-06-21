import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.*;

public class CalculatorServer
{
	public CalculatorServer(String host, String port, String service){
		
		try{
			LocateRegistry.createRegistry(Integer.parseInt(port));
			System.out.println("java RMI registry created");
		}
		catch(RemoteException e)
		{
			System.out.println("java RMI registry already exists!");
		}
		
		try{
			
			Calculator calc = new CalculatorImp();
			Naming.rebind("rmi://" + host + ":" + port + "/" + service, calc);
		}
		catch(RemoteException rE){
			System.out.println("Failure during object export to RMI: " + rE);
		}
		catch(MalformedURLException mfURLE)
		{
			System.out.println("Failure during Name registration: " + mfURLE);
		}
		catch(Exception e){
			System.out.println("Exception: " + e);
		}
	}
	
	public static void main(String[] args)
	{
		new CalculatorServer(args[0], args[1], args[2]);
		
		System.out.println("Server started");
		System.out.println("Enter <CR> to end");
		
		try{
			System.in.read();
		}
		catch(IOException ioE){
			
		}
		
		System.exit(0);
	}
}