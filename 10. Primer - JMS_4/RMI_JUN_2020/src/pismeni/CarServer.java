package pismeni;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class CarServer {
    
    public CarServer(String host, String port, String service) throws RemoteException, MalformedURLException, AlreadyBoundException
    {
        LocateRegistry.createRegistry(Integer.parseInt(port));
        
        ICarManager manager = new CarManager(10);
        
        Naming.bind("rmi://" + host + ":" + port + "/" + service, manager);
        
        System.out.println("Server is running on " + "rmi://" + host + ":" + port + "/" + service);
    }
    
    public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException
    {
        CarServer server = new CarServer("localhost", "1099", "CarService");
        
        new Scanner(System.in).next();
        
        System.exit(0);
    }
}
