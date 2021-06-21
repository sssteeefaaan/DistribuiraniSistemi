package pismeni;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class Ticket extends UnicastRemoteObject implements ITicket {

    private int ID;
    private Vector<Integer> numbers;
    private IBingoCallback callback;

    public Ticket() throws RemoteException {
        super();

        this.ID = -1;
        numbers = new Vector<Integer>();
        callback = null;
    }

    @Override
    public void setID(int ID) throws RemoteException {
        this.ID = ID;
    }

    @Override
    public int getID() throws RemoteException {
        return this.ID;
    }

    @Override
    public void setNumbers(Vector<Integer> numbers) throws RemoteException {
        this.numbers = numbers;
    }

    @Override
    public Vector<Integer> getNumbers() throws RemoteException {
        return this.numbers;
    }

    @Override
    public void setCallback(IBingoCallback callback) throws RemoteException {
        this.callback = callback;
    }

    @Override
    public IBingoCallback getCallback() throws RemoteException {
        return this.callback;
    }

}
