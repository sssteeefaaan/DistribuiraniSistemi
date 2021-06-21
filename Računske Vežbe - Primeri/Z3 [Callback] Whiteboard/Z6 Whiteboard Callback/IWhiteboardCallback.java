import java.rmi.*;

public interface IWhiteboardCallback extends Remote {
	
	void callback(int version) throws RemoteException;
	
}