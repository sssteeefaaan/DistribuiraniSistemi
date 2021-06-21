/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proba;
import java.rmi.*;
/**
 *
 * @author Stefan
 */
public interface ICalculator extends Remote{
    double add(double a, double b) throws RemoteException;
    double sub(double a, double b) throws RemoteException;
    double mul(double a, double b) throws RemoteException;
    double div(double a, double b) throws RemoteException, Exception;
}
