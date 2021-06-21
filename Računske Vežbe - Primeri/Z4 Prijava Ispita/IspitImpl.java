import java.rmi.RemoteException;
import java.util.*;

public class IspitImpl extends java.rmi.server.UnicastRemoteObject implements Ispit
{

private String SifraIspita;
private String ImeIspita;
private int BrojPrijavljenihStudenata;
private Map<String, Student> PrijavljeniStudenti;

public IspitImpl(String sifra, String ime, int brojPS) throws RemoteException
{ 

	this.SifraIspita = sifra;
	this.ImeIspita = ime;

	this.BrojPrijavljenihStudenata = brojPS;
	PrijavljeniStudenti = new HashMap<String,Student>();
	/*Student s1 = new Student("001", "Neko Nekovic", "neko@ptt.rs");
	 Student s2 = new Student("002", "Peko Pekovic", "peko@ptt.rs");

	 PrijavljeniStudenti.put("001", s1);
	 PrijavljeniStudenti.put("002", s2);*/
}

public String VratiSifru() throws RemoteException
{
	return this.SifraIspita;
}

public String VratiIme() throws RemoteException
{
	return this.ImeIspita;
}

public int VratiBrojPrijavljenihStudenata() throws RemoteException
{
	return this.BrojPrijavljenihStudenata;
}

public void PrijaviIspit(Student stud) throws RemoteException
{
	this.PrijavljeniStudenti.put(stud.vratiIndeks(),stud);
	this.BrojPrijavljenihStudenata++;
}

}
