package pismeni;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICarManager extends Remote {

    boolean requestCar(String adresa) throws RemoteException;

    int registerCar(ICar car) throws RemoteException;
}
