package pismeni;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

public class CarManager extends UnicastRemoteObject implements ICarManager {

    private ArrayList<ICar> vozila;
    private ArrayList<String> adrese;
    private int roundRobin;
    private int queueSize;
    private Random rand;

    public CarManager(int queueSize) throws RemoteException {
        super();
        
        vozila = new ArrayList();
        adrese = new ArrayList();
        roundRobin = -1;
        this.queueSize = queueSize;
        
        rand = new Random();
    }

    @Override
    public boolean requestCar(String adresa) throws RemoteException {
        boolean ret = false;

        for (int i = 0; i < vozila.size(); i++) {
            roundRobin = (roundRobin + 1) % vozila.size();
            if (ret |= vozila.get(roundRobin).isFree()) {
                vozila.get(roundRobin).setAdresa(adresa);
                vozila.get(roundRobin).getCallback().notifyCar(adresa);
                return true;
            }
        }

        if (adrese.size() < queueSize) {
            adrese.add(adresa);
            return true;
        }

        return false;
    }

    @Override
    public int registerCar(ICar car) throws RemoteException {
        int ID = rand.nextInt(9999);
        
        car.setID(queueSize);
        vozila.add(car);
        
        return ID;
    }
}
