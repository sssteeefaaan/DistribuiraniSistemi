import java.rmi.server.*;
import java.rmi.RemoteException;
import java.util.*;

public class BankManagerImpl extends UnicastRemoteObject implements BankManager {
 
 private Hashtable<String,Account> accounts;
 private Hashtable<String,Client> clients;

 public BankManagerImpl() throws RemoteException {
	initialize();
 }
 
 public Account getAccount(String accountNumber) throws RemoteException {
	
	AccountImpl account = (AccountImpl)accounts.get(accountNumber);
	return account;
 }
 
 public Client getClient(String clientName) throws RemoteException {
 
	ClientImpl client =(ClientImpl)clients.get(clientName);
	return client;
 }
 
 public void initialize() throws RemoteException {
 
	// Create the hashtables
	accounts = new Hashtable<String,Account>(20);
	clients = new Hashtable<String,Client>(10);
	
	Client clientCharlie = new ClientImpl(this,"Charlie");
	Client clientShannon = new ClientImpl(this,"Shannon");

	clients.put("Charlie", clientCharlie);
	clients.put("Shannon", clientShannon);
	
	Account account;
	account = new AccountImpl(this,	clientCharlie, "4434");
	((AccountImpl)account).deposit(500);
	
	accounts.put("4434", account);
	account = new AccountImpl(this,	clientCharlie, "4461");
	((AccountImpl)account).deposit(600);

	accounts.put("4461", account);
	account = new AccountImpl(this,	clientShannon, "6678");
	((AccountImpl)account).deposit(700);

	accounts.put("6678", account);
 }
 
}