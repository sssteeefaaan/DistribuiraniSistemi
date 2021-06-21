package pismeni;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICalcManager extends Remote {

    int SendVCalcRequest(IVCalcRequest req) throws RemoteException;

    boolean RunNextVCalcRequest() throws RemoteException, Exception;

}
