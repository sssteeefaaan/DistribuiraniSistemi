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
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author Stefan
 */
public class BingoClient extends UnicastRemoteObject implements IBingoCallback{

    private IBingoManager manager;
    
    public BingoClient(String host, String port, String service) throws NotBoundException, MalformedURLException, RemoteException
    {
        manager = (IBingoManager) Naming.lookup("rmi://" + host + ":" + port + "/" + service);
    }
    
    public void uplati(Vector<Integer> kombinacija) throws RemoteException
    {
        Ticket t = new Ticket();
        t.setNumbers(kombinacija);
        t.setCallback(this);
        
        if(manager.playTicket(t) != null)
            System.out.println("Uspesno registrovan tiket sa ID-em {"+ t.getID() +"}");
        else
            System.out.println("Igra je zavrsena!");
    }
    
    @Override
    public void isWinner(ITicket ticket) throws RemoteException {
        System.out.println("Cestitamo, pobedili ste sa tiketom:");
        System.out.println("\tID: " + Integer.toString(ticket.getID()));
        System.out.print("\tBrojevi: [");
        for(Integer i : ticket.getNumbers())
            System.out.print(Integer.toString(i) + " ");
        System.out.println("]");
    }

    @Override
    public void newNumber(int number) {
        System.out.println("Izvucen novi broj: " + Integer.toString(number));
    }
    
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException
    {
        BingoClient client = new BingoClient("localhost", "1099", "BingoService");
        System.out.println("Povezan na server!");
        
        String option;
        Scanner s = new Scanner(System.in);
        int i, broj;
        while(true)
        {
            System.out.println("NoviTiket/Exit?");
            option = s.nextLine().trim().toLowerCase();
            
            if(option.equals("exit"))
                break;
            
            if(option.equals("novitiket"))
            {
                i = 0;
                Vector<Integer> kombinacija = new Vector();
                while(i < 15)
                {
                    System.out.print("Unesite " + Integer.toString(i + 1) +". broj: ");
                    broj = s.nextInt();
                    if(kombinacija.contains(broj))
                        System.out.println("Vec ste uneli broj " + Integer.toString(broj));
                    else{
                        kombinacija.add(broj);
                        i++;
                    }
                }
                client.uplati(kombinacija);
            }
        }
    }
}
