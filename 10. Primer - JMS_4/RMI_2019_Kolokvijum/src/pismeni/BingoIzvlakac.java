/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pismeni;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 *
 * @author Stefan
 */
public class BingoIzvlakac {
    private IBingoManager manager;
    
    public BingoIzvlakac(String host, String port, String service) throws NotBoundException, MalformedURLException, RemoteException
    {
        manager = (IBingoManager) Naming.lookup("rmi://" + host + ":" + port + "/" + service);
    }
    
    public void izvuciNoviBroj() throws RemoteException
    {
        int number = manager.drawNumbers();
        if(number != -1)
            System.out.println("Uspesno izvucen broj: " + Integer.toString(number));
        else
            System.out.println("Igra je zavrsena!");
    }
    
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException
    {
        BingoIzvlakac client = new BingoIzvlakac("localhost", "1099", "BingoService");
        System.out.println("Povezan na server!");
        
        String option;
        Scanner s = new Scanner(System.in);
        while(true)
        {
            System.out.println("IzvuciBroj/Exit?");
            option = s.nextLine().trim().toLowerCase();
            
            if(option.equals("exit"))
                break;
            
            if(option.equals("izvucibroj"))
                client.izvuciNoviBroj();
        }
        
        System.exit(0);
    }
}
