/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pismeni;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Stefan
 */
public interface IBingoManager extends Remote {

    ITicket playTicket(ITicket t) throws RemoteException;

    int drawNumbers() throws RemoteException;
}
