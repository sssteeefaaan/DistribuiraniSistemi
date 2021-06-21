import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Ispit extends Remote
{
	public String VratiSifru() throws RemoteException;
	
	public String VratiIme() throws RemoteException;
	
	public int VratiBrojPrijavljenihStudenata() throws RemoteException;
	
	public void PrijaviIspit(Student stud) throws RemoteException;
}