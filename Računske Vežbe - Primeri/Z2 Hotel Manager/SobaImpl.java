import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class SobaImpl extends UnicastRemoteObject implements Soba {
 
 private boolean zauzeto;
 private Putnik p;
 private int brkr;
 private int cn;
 
 public SobaImpl(int cena, int brojkrev) throws RemoteException {
  brkr = brojkrev;
  cn = cena;
  zauzeto = false;
 }

 public int kojacena() {
  return cn;
 }
 
 public int kolikokreveta() {
  return brkr;
 }
 
 public boolean status() {
  return zauzeto;
 }
 
 public void rezervacija(Putnik misko) {
  zauzeto = true;
  p = misko;
 }
 
}