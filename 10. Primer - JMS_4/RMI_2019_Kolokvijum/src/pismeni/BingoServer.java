package pismeni;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class BingoServer {
    
    public BingoServer(String host, String port, String service) throws RemoteException, AlreadyBoundException, MalformedURLException
    {
        LocateRegistry.createRegistry(Integer.parseInt(port));
        IBingoManager manager = new BingoManager();
        Naming.bind("rmi://" + host + ":" + port + "/" + service, manager);
    }
    
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException
    {
        BingoServer server = new BingoServer("localhost", "1099", "BingoService");
        System.out.println("Server je pokrenut na: rmi://localhost:1099/BingoService");
        
        new Scanner(System.in).next();
        
        System.exit(0);
    }
}
