import java.rmi.RemoteException;

public class CalculatorImp extends java.rmi.server.UnicastRemoteObject implements Calculator{
	
	public CalculatorImp() throws RemoteException
	{
		super();
	}
	
	public double add(double a, double b) throws RemoteException
	{
		return a + b;
	}
	
	public double sub(double a, double b) throws RemoteException
	{
		return a - b;
	}
	
	public double mul(double a, double b) throws RemoteException
	{
		return a * b;
	}
	
	public double div(double a, double b) throws RemoteException, Exception
	{
		if(b == 0)
			throw new Exception("Can't divide by zero!");
		return a / b;
	}
}