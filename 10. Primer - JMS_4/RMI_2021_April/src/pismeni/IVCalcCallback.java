package pismeni;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IVCalcCallback extends Remote {

    void onDone(int cid, double result) throws RemoteException;
}
