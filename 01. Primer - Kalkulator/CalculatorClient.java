import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.*;

public class CalculatorClient{

	public static void main(String[] args){
		
		try{
			Calculator calc = (Calculator)Naming.lookup("rmi://" + args[0] + ":" + args[1] + "/" + args[2]);
			
			System.out.println("4 + 3 = " + Double.toString(calc.add(4, 3)));
			System.out.println("4 - 3 = " + Double.toString(calc.sub(4, 3)));
			System.out.println("4 * 3 = " + Double.toString(calc.mul(4, 3)));
			System.out.println("4 / 3 = " + Double.toString(calc.div(4, 3)));
			System.out.println("4 / 0 = " + Double.toString(calc.div(4, 0)));
		}
		catch(MalformedURLException mURLE){
			System.out.println("Failure due to MalformedURL: " + mURLE);
		}
		catch(RemoteException re){
			System.out.println("Failure due to Remote: " + re);
		}
		catch(Exception e){
			System.out.println("Exception: " + e.getMessage());
		}
	}
}