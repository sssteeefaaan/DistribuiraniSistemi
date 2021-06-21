package pismeni;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface ITicket extends Remote {

    void setID(int ID) throws RemoteException;

    int getID() throws RemoteException;

    void setNumbers(Vector<Integer> numbers) throws RemoteException;

    Vector<Integer> getNumbers() throws RemoteException;

    void setCallback(IBingoCallback callback) throws RemoteException;

    IBingoCallback getCallback() throws RemoteException;
}
