/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pismeni;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author Stefan
 */
public class BingoManager extends UnicastRemoteObject implements IBingoManager{

    private ArrayList<ITicket> ucesnici;
    private Vector<Integer> izvuceni;
    private Random rand;
    private boolean izvucenPobednik;
    
    public BingoManager() throws RemoteException
    {
        super();
        
        ucesnici = new ArrayList();
        izvuceni = new Vector();
        rand = new Random();
        izvucenPobednik = false;
    }
    
    @Override
    public ITicket playTicket(ITicket t) throws RemoteException {
        if(izvuceni.size() == 90 || izvucenPobednik)
            return null;
        
        int ID = rand.nextInt(65536);
        
        t.setID(ID);
        ucesnici.add(t);
        
        return t;
    }

    @Override
    public int drawNumbers() throws RemoteException {
        if(izvuceni.size() == 90 || izvucenPobednik)
            return -1;
        
        int number;
        do{
            number = (int)(Math.random() * 90);
        }while(izvuceni.contains(number));
        
        izvuceni.add(number);
        
        boolean test;
        Vector<Integer> temp;
        for(ITicket ticket : ucesnici)
        {
            ticket.getCallback().newNumber(number);
            
            test = true;
            temp = ticket.getNumbers();
            
            for(int i = 0; i < 15; i++)
                test &= izvuceni.contains(temp.get(i));
            
            if(test)
                ticket.getCallback().isWinner(ticket);
            
            izvucenPobednik |= test;
        }
        
        return number;
    }
    
}
