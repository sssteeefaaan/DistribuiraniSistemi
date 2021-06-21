package glavni;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;

public interface EStudSluzba extends Remote{
	
	Student vratiStudenta(String brojIndeksa) throws RemoteException;
	Ispit vratiIspit(String naziv) throws RemoteException;
	boolean dodajStudenta(Student student) throws RemoteException;
	boolean dodajIspit(Ispit ispit) throws RemoteException;
	boolean ukloniStudenta(String brojIndeksa) throws RemoteException;
	boolean ukloniIspit(String naziv) throws RemoteException;
	Hashtable<String, Student> vratiStudente() throws RemoteException;
	Hashtable<String, Ispit> vratiIspite() throws RemoteException;
}