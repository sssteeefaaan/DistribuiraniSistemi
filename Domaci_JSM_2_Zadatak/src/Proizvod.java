
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author stefa
 */
public class Proizvod implements Serializable{
    private String naziv;
    private String sifra;
    private int kolicina;
    
    public Proizvod()
    {
        naziv = "UNKNOWN";
        sifra = "UNKNOWN";
        kolicina = 0;
    }
    
    public Proizvod(String naziv, String sifra, int kolicina)
    {
        this.naziv = naziv;
        this.sifra = sifra;
        this.kolicina = kolicina;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getSifra() {
        return sifra;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }
    
    public boolean isAvailable()
    {
        return kolicina > 0;
    }
}
