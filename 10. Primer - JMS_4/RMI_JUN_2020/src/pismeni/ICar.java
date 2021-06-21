package pismeni;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICar extends Remote {

    void setID(int id) throws RemoteException;

    int getID() throws RemoteException;

    void setAdresa(String adresa) throws RemoteException;

    String getAdresa() throws RemoteException;

    void setFree(boolean isFree) throws RemoteException;

    boolean isFree() throws RemoteException;

    void setCallback(ICarCallback callback) throws RemoteException;

    ICarCallback getCallback() throws RemoteException;
}
