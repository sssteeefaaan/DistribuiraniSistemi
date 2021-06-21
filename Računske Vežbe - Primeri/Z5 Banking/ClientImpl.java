import java.rmi.server.*;
import java.rmi.RemoteException;
import java.util.*;

public class ClientImpl extends UnicastRemoteObject implements Client {
  
 private BankManager bankManager;
 private String clientName;
 
 public ClientImpl(BankManager bm, String name) throws java.rmi.RemoteException {
	this.bankManager = bm;
	this.clientName = name;
 }
 
 public BankManager getBankManager() throws RemoteException {
	return bankManager;
 }
 
 public String getName() throws RemoteException {
	return clientName;
 }
 
}
