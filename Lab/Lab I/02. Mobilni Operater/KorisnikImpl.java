import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class KorisnikImpl extends UnicastRemoteObject implements Korisnik{
	
	Stanje stanje;
	int minutiTarifa;
	int porukeTarifa;
	int internetTarifa;
	
	public KorisnikImpl(Stanje stanje, int minutiTarifa, int porukeTarifa, int internetTarifa) throws RemoteException {
		
		super();
		
		this.stanje = stanje;
		this.minutiTarifa = minutiTarifa;
		this.porukeTarifa = porukeTarifa;
		this.internetTarifa = internetTarifa;
	}
	
	public boolean uplatiMinute(int minuti) throws RemoteException{
		return this.stanje.uplatiMinute(minuti, minutiTarifa);
	}
	public boolean uplatiPoruke(int poruke) throws RemoteException{
		return this.stanje.uplatiPoruke(poruke, porukeTarifa);
	}
	public boolean uplatiInternet(int internet) throws RemoteException{
		return this.stanje.uplatiInternet(internet, internetTarifa);
	}
	public Stanje vratiStanje() throws RemoteException{
		return this.stanje;
	}
}