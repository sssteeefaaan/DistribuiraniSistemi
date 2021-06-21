import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StanjeImpl extends UnicastRemoteObject implements Stanje{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float iznosDinarski;
	private float iznosDevizni;
	
	
	public StanjeImpl(float iznosDinarski, float iznosDevizni) throws RemoteException{
		super();
		
		this.iznosDinarski = iznosDinarski;
		this.iznosDevizni = iznosDevizni;
	}
	
	public float vratiDinarskiIznos() throws RemoteException{
		return this.iznosDinarski;
	}
	public float vratiDevizniIznos() throws RemoteException{
		return this.iznosDevizni;
	}
	public boolean prebaciNaDevizni(float iznos, float kurs) throws RemoteException{
		
		if(iznos <= this.iznosDinarski && iznos > 0 && kurs > 0){
			this.iznosDevizni += kurs * iznos;
			this.iznosDinarski -= iznos;
			
			return true;
		}
		
		return false;
	}
	public boolean prebaciNaDinarski(float iznos, float kurs) throws RemoteException{
		
		if(iznos <= this.iznosDevizni && iznos > 0 && kurs > 0){
			this.iznosDinarski += iznos * kurs;
			this.iznosDevizni -= iznos;
			
			return true;
		}
		
		return false;
	}
}