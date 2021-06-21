import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.*;

public class CalculatorClient {

 public static void main(String[] args) {

  String host = args[0];
  String port = args[1];
  String service = args[2];
  
  try {
   
   Calculator c = (Calculator)Naming.lookup("rmi://" + host + ":" + port + "/" + service);

   System.out.println(c.sub(4, 3));
   System.out.println(c.add(4, 5));
   System.out.println(c.mul(3, 6));
   System.out.println(c.div(9, 3));
   
  } catch (MalformedURLException murle) {
	  
   System.out.println();
   System.out.println("MalformedURLException");
   System.out.println(murle);
   
  } catch (RemoteException re) {
	  
   System.out.println();
   System.out.println("RemoteException");
   System.out.println(re);
   
  } catch (NotBoundException nbe) {
	  
   System.out.println();
   System.out.println("NotBoundException");
   System.out.println(nbe);
   
  } catch (java.lang.ArithmeticException ae) {
	  
   System.out.println();
   System.out.println("java.lang.ArithmeticException");
   System.out.println(ae);
   
  }
 }
}