import java.rmi.*;
import java.net.MalformedURLException;
import java.io.* ;
import java.text.NumberFormat;
import java.util.Locale;

public class BankSystemClient {
 
 private BankManager bm;

 public BankSystemClient(String host, String port, String service) {
 
	 try {
		bm = (BankManager)Naming.lookup("rmi://" + host + ":" + port + "/" + service);
	 } catch (MalformedURLException malformedException) {
		System.err.println("Bad URL: " + malformedException);
	 }
	 
	 catch (NotBoundException notBoundException) {
		System.err.println("Not Bound: " +notBoundException);
	 } catch (RemoteException remoteException) {
		System.err.println("Remote Exception: "+ remoteException);
	 }
	 
	 try {
		 
		Account account = bm.getAccount("4461");

		Client client = account.getClient();
		String name = client.getName();
		
		long cash = account.getBalance();
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
		String balanceString =currencyFormat.format(cash);
		
		System.out.println(name + "'s account has " + balanceString);
	 
	 } catch (RemoteException remoteException) {
		System.err.println(remoteException);
	 }
 
 }
 
 public static void main(String[] args) {
	
	String host = args[0];
	String port = args[1];
	String service = args[2]; 
	
	new BankSystemClient(host,port,service);	
	
 }
 
}