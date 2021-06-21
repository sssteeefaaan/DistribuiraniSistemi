package pismeni;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBingoCallback extends Remote {

    void isWinner(ITicket ticket) throws RemoteException;

    void newNumber(int number) throws RemoteException;
}
