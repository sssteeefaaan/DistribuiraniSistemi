import java.rmi.*;

public interface IShape extends Remote {
 
  int getVersion() throws RemoteException; 
  
  void setVersion(int ver) throws RemoteException; 
  
  String print() throws RemoteException;
 
}