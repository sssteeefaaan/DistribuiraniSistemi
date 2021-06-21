import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StanjeImpl extends UnicastRemoteObject implements Stanje{
	
	String broj;
	int minuti;
	int poruke;
	int internet;
	float racun;
	
	public StanjeImpl(String broj, int minuti, int poruke, int internet, float racun) throws RemoteException {
		super();
		
		this.broj = broj;
		this.minuti = minuti;
		this.poruke = poruke;
		this.internet = internet;
		this.racun = racun;
	}
	
	public int vratiMinute() throws RemoteException{
		return this.minuti;
	}
	public int vratiPoruke() throws RemoteException{
		return this.poruke;
	}
	public int vratiInternet() throws RemoteException{
		return this.internet;
	}
	public float vratiRacun() throws RemoteException{
		return this.racun;
	}
	
	public String vratiBroj() throws RemoteException{
		return this.broj;
	}
	public boolean uplatiMinute(int minuti, int tarifa) throws RemoteException{
		if(minuti > 0 && tarifa >= 0){
			this.minuti += minuti;
			this.racun += minuti * tarifa;
			return true;
		}
		return false;
	}
	public boolean uplatiPoruke(int poruke, int tarifa) throws RemoteException{
		if(poruke > 0 && tarifa >= 0){
			this.poruke += poruke;
			this.racun += poruke * tarifa;
				return true;
		}
		return false;
	}
	public boolean uplatiInternet(int internet, int tarifa) throws RemoteException{
		if(internet > 0 && tarifa >= 0){
			this.internet += internet;
			this.racun += internet * tarifa;
				return true;
		}
		return false;
	}
}