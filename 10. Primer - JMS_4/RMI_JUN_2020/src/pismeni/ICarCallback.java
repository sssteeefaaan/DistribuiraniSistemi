package pismeni;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICarCallback extends Remote {

    void notifyCar(String address) throws RemoteException;
}
