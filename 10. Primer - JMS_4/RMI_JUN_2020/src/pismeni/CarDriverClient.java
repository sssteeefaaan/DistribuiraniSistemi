package pismeni;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class CarDriverClient extends UnicastRemoteObject implements ICarCallback{

    private ICarManager manager;
    
    public CarDriverClient(String host, String port, String service) throws NotBoundException, MalformedURLException, RemoteException
    {
        manager = (ICarManager) Naming.lookup("rmi://" + host + ":" + port + "/" + service);
        System.out.println("Uspešno povezan na server!");
    }
    
    public void registerCar(Car car) throws RemoteException
    {
        car.setCallback(this);
        car.setID(manager.registerCar(car));
        System.out.println("Vozilo registrovano na ID {" + Integer.toString(car.getID()) + "}!");
    }
    
    @Override
    public void notifyCar(String address) throws RemoteException {
        System.out.println("Pokupite klijenta na adresu: " + address);
    }
    
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException
    {
        CarDriverClient vozac = new CarDriverClient("localhost", "1099", "CarService");
        
        String option;
        Scanner s = new Scanner(System.in);
        while(true)
        {
            System.out.println("RegistrujteNovoVozilo/Exit?");
            option = s.nextLine().trim().toLowerCase();
            
            if(option.equals("exit"))
                break;
            
            if(option.equals("registrujtenovovozilo"))
                vozac.registerCar(new Car());
        }
        
        System.exit(0);
    }
}
