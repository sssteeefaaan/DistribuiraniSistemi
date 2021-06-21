import java.rmi.*;
import java.net.MalformedURLException;
import java.io.*;

public class HotelClient {

 private HotelManager ht;

 public HotelClient(String host, String port, String server) {
  
  try {
   ht = (HotelManager) Naming.lookup("rmi://" + host + ":" + port + "/" + server);
  } catch (MalformedURLException malformedException) {
   System.err.println("Bad URL: " + malformedException);
  } catch (NotBoundException notBoundException) {
   System.err.println("Not Bound: " + notBoundException);
  } catch (RemoteException remoteException) {
   System.err.println("Remote Exception: " + remoteException);
  }
  
  try {
   
   int brkr = -1, cena = -1, matbr = -1;
   String prezime = null;
   String ime = null;
   BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
   
   try {
    
	System.out.println("Unesite ime:");	
	ime = r.readLine();
	System.out.println("Unesite prezime:");	
    prezime = r.readLine();
	System.out.println("Unesite MBR:");	
    matbr = Integer.parseInt(r.readLine());
	System.out.println("Unesite broj kreveta:");	
    brkr = Integer.parseInt(r.readLine());
    System.out.println("Unesite cenu:");	
	cena = Integer.parseInt(r.readLine());
   } catch (IOException ioe) {
    System.out.println("IO greska");
   }
   
   Putnik putnik = new Putnik(ime, prezime, matbr);
   Soba s = ht.nadjisobu(cena, brkr);
   
   if (s == null) System.out.println("Nema sobe");
   else {
    s.rezervacija(putnik);
    System.out.println("Soba rezervisana");
   }
  
  } catch (RemoteException remoteException) {
   System.err.println(remoteException);
  }
  
 }
 
 public static void main(String[] args) {
  
  String host = args[0];
  String port = args[1];
  String server = args[2];
  
  new HotelClient(host, port, server);
 
 }
 
}