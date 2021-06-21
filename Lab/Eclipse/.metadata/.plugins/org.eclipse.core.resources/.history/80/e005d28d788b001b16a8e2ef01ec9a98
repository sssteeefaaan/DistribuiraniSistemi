import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;

public interface EStudSluzba extends Remote{
	
	Student vratiStudenta(String brojIndeksa) throws RemoteException;
	boolean dodajStudenta(Student student) throws RemoteException;
	boolean ukloniStudenta(String brojIndeksa) throws RemoteException;
	Hashtable<String, Student> vratiBazu() throws RemoteException;
}