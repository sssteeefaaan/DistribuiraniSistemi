import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrijavaImpl extends UnicastRemoteObject implements Prijava{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ispiti;
	
	public PrijavaImpl() throws RemoteException{
		super();
		
		this.ispiti = "";
	}
	public String vratiPrijavljeneIspite() throws RemoteException{
		return this.ispiti;
	}
	public boolean prijaviIspit(String ispit) throws RemoteException{
		String temp = ispit.replaceAll("\n", " ").replaceAll("  ", " ").trim().toUpperCase();
		if(this.ispiti.contains(temp))
			return false;
		
		this.ispiti += temp + "\n";
		return true;
	}
	public boolean ponistiIspit(String ispit) throws RemoteException{
		String temp = ispit.replaceAll("\n", " ").replaceAll("  ", " ").trim().toUpperCase();
		if(this.ispiti.contains(temp)){
			this.ispiti = this.ispiti.replaceAll(temp + "\n", "");
			return true;
		}
		return false;
	}
}