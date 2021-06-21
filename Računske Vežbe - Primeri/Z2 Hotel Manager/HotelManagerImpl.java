import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HotelManagerImpl extends UnicastRemoteObject implements HotelManager {
 
 private Soba[] sobeniz = {
  new SobaImpl(500, 3),
  new SobaImpl(200, 4),
  new SobaImpl(600, 5),
  new SobaImpl(100, 2)
 };
 
 public HotelManagerImpl() throws RemoteException {
  super();
 }
 
 public Soba nadjisobu(int mc, int nb) throws RemoteException {
  
  int i = 0;
  while (i < sobeniz.length && !(sobeniz[i].status() == false && sobeniz[i].kolikokreveta() == nb && sobeniz[i].kojacena() <= mc)) i++;
  
  if (i < sobeniz.length) return (Soba) this.sobeniz[i];
  
  else return null;
  
 }
 
}