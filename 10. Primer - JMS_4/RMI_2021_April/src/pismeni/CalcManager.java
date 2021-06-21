package pismeni;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

public class CalcManager extends UnicastRemoteObject implements ICalcManager{
    private ArrayList<IVCalcRequest> zahtevi;
    public static Random rand;
    
    public CalcManager()
    {
        zahtevi = new ArrayList<IVCalcRequest>();
        rand = new Random();
    }

    @Override
    public int SendVCalcRequest(IVCalcRequest req) throws RemoteException {
        int cid = rand.nextInt(9999);
        
        req.setCID(cid);
        zahtevi.add(req);
        
        return cid;
    }

    @Override
    public boolean RunNextVCalcRequest() throws RemoteException, Exception {
        if(zahtevi.size() == 0)
            throw new Exception("Red je prazan!");
        zahtevi.remove(0).doOperation();
        
        return true;
    }
    
}
