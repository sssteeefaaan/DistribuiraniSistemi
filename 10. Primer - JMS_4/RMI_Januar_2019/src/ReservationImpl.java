
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Stefan
 */
public class ReservationImpl extends UnicastRemoteObject implements Reservation {

    private int ID;
    private int month;
    private int day;
    private int hour;
    private int numHours;

    public ReservationImpl() throws RemoteException {
        super();
        ID = month = day = hour = numHours = -1;
    }

    public ReservationImpl(int id, int month, int day, int hour, int numHours) throws RemoteException {
        super();
        this.ID = id;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.numHours = numHours;
    }

    @Override
    public int getID() throws RemoteException {
        return this.ID;
    }

    @Override
    public int getDay() throws RemoteException {
        return this.day;
    }

    @Override
    public int getHour() throws RemoteException {
        return this.hour;
    }

    @Override
    public int getMonth() throws RemoteException {
        return this.month;
    }

    @Override
    public int getNumHours() throws RemoteException {
        return this.numHours;
    }

    @Override
    public void setID(int id) throws RemoteException {
        this.ID = id;
    }

    @Override
    public void setDay(int day) throws RemoteException {
        this.day = day;
    }

    @Override
    public void setHour(int hour) throws RemoteException {
        this.hour = hour;
    }

    @Override
    public void setMonth(int month) throws RemoteException {
        this.month = month;
    }

    @Override
    public void setNumHours(int numHours) throws RemoteException {
        this.numHours = numHours;
    }
}
