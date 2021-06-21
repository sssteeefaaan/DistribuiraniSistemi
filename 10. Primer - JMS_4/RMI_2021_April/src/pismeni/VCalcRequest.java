package pismeni;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class VCalcRequest extends UnicastRemoteObject implements IVCalcRequest {

    private int cid;
    private ArrayList<Double> a;
    private ArrayList<Double> b;
    private IVCalcCallback cb;

    public VCalcRequest() throws RemoteException {
        super();

        cid = -1;
        a = null;
        b = null;
    }

    @Override
    public int getCID() throws RemoteException {
        return cid;
    }

    @Override
    public void setCID(int cid) throws RemoteException {
        this.cid = cid;
    }

    @Override
    public void setA(ArrayList<Double> a) throws RemoteException {
        this.a = a;
    }

    @Override
    public void setB(ArrayList<Double> b) throws RemoteException {
        this.b = b;
    }

    @Override
    public ArrayList<Double> getA() throws RemoteException {
        return this.a;
    }

    @Override
    public ArrayList<Double> getB() throws RemoteException {
        return this.b;
    }

    @Override
    public void doOperation() throws RemoteException, Exception {

        if (cid == -1) {
            throw new Exception("Nije postavljen CID!");
        }

        if (a == null) {
            throw new Exception("Vektor a nije inicijalizovan!");
        }

        if (b == null) {
            throw new Exception("Vektor b nije inicijalizovan!");
        }

        if (a.size() != b.size()) {
            throw new Exception("Vektori su različitih dimenzija!");
        }

        double partRez = 0;
        ArrayList<Double> temp = new ArrayList();

        for (int i = 0; i < a.size(); i++) {
            temp.add(a.get(i) - b.get(i));
            partRez += temp.get(i) * temp.get(i);
        }

        cb.onDone(cid, (-1) * Math.pow(partRez, 1.5));
    }

    @Override
    public void setCallback(IVCalcCallback cb) throws RemoteException {
        this.cb = cb;
    }

    @Override
    public IVCalcCallback getCallback() throws RemoteException {
        return this.cb;
    }

}
