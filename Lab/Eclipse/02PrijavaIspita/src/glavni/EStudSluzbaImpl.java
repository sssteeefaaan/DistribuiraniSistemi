package glavni;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.rmi.server.UnicastRemoteObject;

public class EStudSluzbaImpl extends UnicastRemoteObject implements EStudSluzba{
	
	private static final long serialVersionUID = 1L;
	private Hashtable<String, Student> studenti;
	private Hashtable<String, Ispit> ispiti;
	
	public EStudSluzbaImpl() throws RemoteException{
		super();
		
		this.studenti = new Hashtable<String, Student>();
		this.ispiti = new Hashtable<String, Ispit>();
	}
	public Student vratiStudenta(String brojIndeksa) throws RemoteException{
		return this.studenti.get(brojIndeksa.trim());
	}
	public Ispit vratiIspit(String naziv) throws RemoteException{
		return this.ispiti.get(naziv);
	}
	public boolean dodajStudenta(Student student) throws RemoteException{
		String indeks = student.vratiBrojIndeksa();
		if(this.studenti.get(indeks) != null)
			return false;
		
		this.studenti.put(indeks, student);
		return true;
	}
	public boolean dodajIspit(Ispit ispit) throws RemoteException{
		String naziv = ispit.vratiNaziv();
		if(this.ispiti.get(naziv) != null)
			return false;
		
		this.ispiti.put(naziv, ispit);
		return true;
	}
	public boolean ukloniStudenta(String brojIndeksa) throws RemoteException{
		return this.studenti.remove(brojIndeksa.trim()) != null;
	}
	public boolean ukloniIspit(String naziv) throws RemoteException{
		return this.ispiti.remove(naziv) != null;
	}
	public Hashtable<String, Student> vratiStudente() throws RemoteException{
		return this.studenti;
	}
	public Hashtable<String, Ispit> vratiIspite() throws RemoteException{
		return this.ispiti;
	}
}