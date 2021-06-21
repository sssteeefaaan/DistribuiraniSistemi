import java.util.*;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class FacultyManagerImpl extends UnicastRemoteObject implements FacultyManager {

private Map<String, Ispit> ispiti;

public FacultyManagerImpl() throws java.rmi.RemoteException
{
 ispiti = new HashMap<String, Ispit>();
 Ispit isp1 = new IspitImpl("rm", "Racunarske mreze", 0);
 Ispit isp2 = new IspitImpl("ds", "Distribuirani sistemi", 0);
 
 ispiti.put("rm", isp1);
 ispiti.put("ds", isp2);
 
}

public Ispit PronadjiIspit(String sifra) throws RemoteException
{
	IspitImpl ispit = (IspitImpl)
	
	this.ispiti.get(sifra);
	
	return ispit;
}

}
