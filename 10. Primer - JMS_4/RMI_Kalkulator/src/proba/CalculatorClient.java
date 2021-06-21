/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proba;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author Stefan
 */
public class CalculatorClient {
    
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException, Exception
    {
        ICalculator calc = (ICalculator) Naming.lookup("rmi://localhost:1099/CalculatorService");
        
        System.out.println("4 + 3 = " + Double.toString(calc.add(4, 3)));
        System.out.println("4 - 3 = " + Double.toString(calc.sub(4, 3)));
        System.out.println("4 * 3 = " + Double.toString(calc.mul(4, 3)));
        System.out.println("4 / 3 = " + Double.toString(calc.div(4, 3)));
        System.out.println("4 / 0 = " + Double.toString(calc.div(4, 0)));
    }
}
