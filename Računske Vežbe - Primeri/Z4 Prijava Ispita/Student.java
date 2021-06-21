import java.io.Serializable;

public class Student implements Serializable {

private String brojIndeksa;
private String ImeIPrezime;
private String email;

public Student(String ind, String iip, String mail)
{
	this.brojIndeksa = ind;
	this.ImeIPrezime = iip;
	this.email = mail;
}

public String vratiIndeks()
{
	return this.brojIndeksa;
}

public String vratiIiP()
{
	return this.ImeIPrezime;
}

public String vratiEmail()
{
	return this.email;
}

}