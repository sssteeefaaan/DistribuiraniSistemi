/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proba;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

/**
 *
 * @author Stefan
 */
public class CalculatorServer {
    
    public CalculatorServer(String host, String port, String service) throws RemoteException, AlreadyBoundException, MalformedURLException
    {
        LocateRegistry.createRegistry(Integer.parseInt(port));
        
        Calculator calc = new Calculator();
        Naming.bind("rmi://" + host + ":" + port + "/" + service, calc);
        System.out.println("Server pokrenut na rmi://" + host + ":" + port + "/" + service);
    }
    
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException
    {
        String host = "localhost";
        String port = "1099";
        String service = "CalculatorService";
        CalculatorServer server = new CalculatorServer(host, port, service);
        
        Scanner s = new Scanner(System.in);
        s.next();
        
        return;
    }
}
