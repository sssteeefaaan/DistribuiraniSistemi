/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pismeni;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IVCalcRequest extends Remote {

    int getCID() throws RemoteException;

    void setCID(int cld) throws RemoteException;

    void setA(ArrayList<Double> a) throws RemoteException;

    void setB(ArrayList<Double> b) throws RemoteException;

    ArrayList<Double> getA() throws RemoteException;

    ArrayList<Double> getB() throws RemoteException;

    void setCallback(IVCalcCallback cb) throws RemoteException;

    IVCalcCallback getCallback() throws RemoteException;

    void doOperation() throws RemoteException, Exception;
}
