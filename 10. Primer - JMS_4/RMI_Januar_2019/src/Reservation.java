
import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Stefan
 */
public interface Reservation extends Remote {

    int getID() throws RemoteException;

    int getDay() throws RemoteException;

    int getMonth() throws RemoteException;

    int getHour() throws RemoteException;

    int getNumHours() throws RemoteException;

    void setID(int id) throws RemoteException;

    void setMonth(int month) throws RemoteException;

    void setDay(int day) throws RemoteException;

    void setHour(int hour) throws RemoteException;

    void setNumHours(int numHours) throws RemoteException;
}
