package glavni;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EStudSluzbaCallback extends Remote{
	void callback(String ispit, int brojStudenata) throws RemoteException;
}
