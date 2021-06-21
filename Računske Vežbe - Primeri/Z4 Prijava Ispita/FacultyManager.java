import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FacultyManager extends Remote
{
	public Ispit PronadjiIspit(String sifra) throws	RemoteException;
}