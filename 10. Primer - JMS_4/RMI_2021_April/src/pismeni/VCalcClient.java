package pismeni;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

public class VCalcClient extends UnicastRemoteObject implements IVCalcCallback{

    private ICalcManager calc = null;
    
    public VCalcClient(String host, String port, String service) throws NotBoundException, MalformedURLException, RemoteException
    {
        super();
        
        calc = (ICalcManager) Naming.lookup("rmi://" + host + ":" + port + "/" + service);
        System.out.println("Povezan na server!");
    }
    
    public int run(ArrayList<Double> a, ArrayList<Double> b) throws RemoteException
    {
        VCalcRequest req = new VCalcRequest();//(a, b, this);
        req.setA(a);
        req.setB(b);
        req.setCallback(this);
        
        return calc.SendVCalcRequest(req);
    }
    
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException
    {
        VCalcClient client = new VCalcClient("localhost", "1099", "VCalcService");
        
        String option;
        Scanner s = new Scanner(System.in);
        
        ArrayList<Double> a = new ArrayList(), b = new ArrayList();
        int size;
        
        while(true)
        {
            System.out.println("NewRequest/Exit?");
            option = s.nextLine().trim().toLowerCase();
            
            if(option.equals("exit"))
                break;
            
            if(option.equals("newrequest"))
            {
                System.out.println("Unesite dimenziju vektora:");
                size = s.nextInt();
                
                if(size <= 0)
                    continue;
                
                System.out.println("Unesite vektor a:");
                for(int i = 0; i < size; i++)
                    a.add(s.nextDouble());
                
                System.out.println("Unesite vektor b:");
                for(int i = 0; i < size; i++)
                    b.add(s.nextDouble());
                
                System.out.println("CID operacije: " + Integer.toString(client.run(a, b)));
            }
        }
        
        System.exit(0);
    }
    
    @Override
    public void onDone(int cid, double result) throws RemoteException {
        System.out.println("Zahtev za računanjem sa ID-om {" + cid + "} je završen!");
        System.out.println("Rezultat je: " + Double.toString(result));
    }
    
}
