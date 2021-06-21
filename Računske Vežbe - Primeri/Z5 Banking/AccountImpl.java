import java.rmi.server.*;
import java.rmi.RemoteException;
import java.util.*;

public class AccountImpl extends UnicastRemoteObject implements Account
{

 private BankManager bankManager;
 private Client client;
 private long balance;
 private String accountNumber;

 public AccountImpl (BankManager bankManager,Client client,String accountNumber) throws RemoteException {
 
	 this.bankManager = bankManager; 
	 this.client = client;
	 this.balance = 0;
	 this.accountNumber = accountNumber;
 
 }
 
 public void deposit(long amount) throws RemoteException {
	balance += amount;
 }
 
 public BankManager getBankManager() throws RemoteException {
	 return bankManager;
 }
 
 public Client getClient() throws RemoteException {
	 return client;
 }
 
 public long getBalance() throws RemoteException {
	 return balance;
 }
 
 public long getCash(long amount) throws NoCashAvailableException, RemoteException {
	
	if (amount > balance) {
	 throw new NoCashAvailableException();
	}
 
	balance = balance - amount;
	
	return amount; 
 }
 
}