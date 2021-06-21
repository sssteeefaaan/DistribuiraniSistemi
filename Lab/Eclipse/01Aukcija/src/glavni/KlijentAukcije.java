package glavni;
import java.io.Serializable;

public class KlijentAukcije implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String ime;
	private String prezime;
	
	public KlijentAukcije(String klijentAukcijeID, String ime, String prezime){
		
		this.id = klijentAukcijeID;
		this.ime = ime;
		this.prezime = prezime;
	}
	
	public String vratiID() {
		return this.id;
	}
	public String vratiIme() {
		return this.ime;
	}
	public String vratiPrezime() {
		return this.prezime;
	}
}