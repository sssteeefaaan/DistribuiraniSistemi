
package proba;
import java.rmi.*;
import java.rmi.server.*;

public class Calculator extends UnicastRemoteObject implements ICalculator {
    
    public Calculator() throws RemoteException
    {
        super();
    }
    
    @Override
    public double add(double a, double b) throws RemoteException
    { return a + b + b; }
    
    @Override
    public double sub(double a, double b) throws RemoteException
    { return a - b; }
    
    @Override
    public double mul(double a, double b) throws RemoteException
    { return a * b; }
    
    @Override
    public double div(double a, double b) throws RemoteException, Exception
    { 
        if(b != 0)
            return a / b;
        throw new Exception("Can't divide by zero!");
    }
}
