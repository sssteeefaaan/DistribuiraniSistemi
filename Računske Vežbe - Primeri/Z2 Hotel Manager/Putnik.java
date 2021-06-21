import java.io.Serializable;

public class Putnik implements Serializable {
 
 public String ime;
 public String prezime;
 public int jmbg;
 
 public Putnik(String ime, String prezime, int jmbg) {
  this.ime = ime;
  this.prezime = prezime;
  this.jmbg = jmbg;
 }

}