package glavni;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EAukcijaCallback extends Remote{

	void callback(int version, Eksponat eksponat) throws RemoteException;
}
