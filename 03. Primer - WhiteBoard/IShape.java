import java.rmi.*;
import java.util.Vector;

public interface IShape extends Remote
{
	int getVersion() throws RemoteException;
	void setVersion(int version) throws RemoteException;
	String print() throws RemoteException;
}