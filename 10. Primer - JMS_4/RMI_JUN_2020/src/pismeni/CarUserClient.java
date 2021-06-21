package pismeni;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class CarUserClient {
    
    private ICarManager manager;
    
    public CarUserClient(String host, String port, String service) throws RemoteException, MalformedURLException, NotBoundException
    {
        manager = (ICarManager) Naming.lookup("rmi://" + host + ":" + port + "/" + service);
        System.out.println("Uspešno povezan na server!");
    }
    
    public void requestCar(String address) throws RemoteException
    {
        if(manager.requestCar(address))
            System.out.println("Vozilo je uspesno pozvano!");
        else
            System.out.println("Nije moguce dobiti vozilo u ovom trenutku!");
    }
    
    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException
    {
        CarUserClient user = new CarUserClient("localhost", "1099", "CarService");
        
        String option;
        Scanner s = new Scanner(System.in);
        
        while(true)
        {
            System.out.println("NaruciVozilo/Exit?");
            option = s.nextLine().trim().toLowerCase();
            
            if(option.equals("exit"))
                break;
            
            if(option.equals("narucivozilo"))
            {
                System.out.println("Unesite adresu:");
                if((option = s.nextLine().trim()).length() > 0)
                    user.requestCar(option);
            }
        }
        
        System.exit(0);
    }
}
