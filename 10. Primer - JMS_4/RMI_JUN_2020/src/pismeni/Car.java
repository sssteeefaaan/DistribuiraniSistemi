package pismeni;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Car extends UnicastRemoteObject implements ICar {

    private int id;
    private String adresa;
    private boolean isFree;
    private ICarCallback callback;

    public Car() {
        id = -1;
        adresa = "";
        isFree = true;
        callback = null;
    }

    public Car(int id, String adresa, boolean isFree) {
        this.id = id;
        this.adresa = adresa;
        this.isFree = isFree;
    }

    @Override
    public void setID(int id) throws RemoteException {
        this.id = id;
    }

    @Override
    public int getID() throws RemoteException {
        return this.id;
    }

    @Override
    public void setAdresa(String adresa) throws RemoteException {
        this.adresa=adresa;
    }

    @Override
    public String getAdresa() throws RemoteException {
        return this.adresa;
    }

    @Override
    public void setFree(boolean isFree) throws RemoteException {
        this.isFree=isFree;
    }

    @Override
    public boolean isFree() throws RemoteException {
        return this.isFree;
    }

    @Override
    public void setCallback(ICarCallback callback) throws RemoteException {
        this.callback=callback;
    }

    @Override
    public ICarCallback getCallback() throws RemoteException {
        return this.callback;
    }

}
