package glavni;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class PrijavaImpl extends UnicastRemoteObject implements Prijava{
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Ispit> ispiti;
	
	public PrijavaImpl() throws RemoteException{
		super();
		
		this.ispiti = new ArrayList<Ispit>();
	}
	public ArrayList<Ispit> vratiPrijavljeneIspite() throws RemoteException{
		return this.ispiti;
	}
	public boolean prijaviIspit(Ispit ispit) throws RemoteException{
		if(this.ispiti.contains(ispit))
			return false;
		this.ispiti.add(ispit);
		return true;
	}
	public boolean ponistiIspit(Ispit ispit) throws RemoteException{
		return this.ispiti.remove(ispit);
	}
}