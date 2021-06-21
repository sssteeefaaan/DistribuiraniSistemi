
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Stefan
 */
public class GymReservationServer {
    
    public GymReservationServer(String host, String port, String service) throws RemoteException, AlreadyBoundException, MalformedURLException
    {
        LocateRegistry.createRegistry(Integer.parseInt(port));
        
        GymReservationManager manager = new GymReservationManagerImpl();
        Naming.bind("rmi://" + host  + ":" + port + "/" + service, manager);
    }
    
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException
    {
        GymReservationServer server = new GymReservationServer("localhost", "1099", "GymReservationService");
        System.out.println("Uspesno uspostavljanje servisa!");
        
        new Scanner(System.in).next();
        
        System.exit(0);
    }
}
