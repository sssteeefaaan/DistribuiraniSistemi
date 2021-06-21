import java.rmi.RemoteException;
import java.util.Hashtable;
import java.rmi.server.UnicastRemoteObject;

public class EStudSluzbaImpl extends UnicastRemoteObject implements EStudSluzba{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hashtable<String, Student> baza;
	
	public EStudSluzbaImpl() throws RemoteException{
		super();
		
		this.baza = new Hashtable<String, Student>();
	}
	public Student vratiStudenta(String brojIndeksa) throws RemoteException{
		return this.baza.get(brojIndeksa.trim());
	}
	public boolean dodajStudenta(Student student) throws RemoteException{
		String indeks = student.vratiBrojIndeksa();
		if(this.baza.get(indeks) != null)
			return false;
		
		this.baza.put(indeks, student);
		return true;
	}
	public boolean ukloniStudenta(String brojIndeksa) throws RemoteException{
		return this.baza.remove(brojIndeksa.trim()) != null;
	}
	public Hashtable<String, Student> vratiBazu() throws RemoteException{
		return this.baza;
	}
}