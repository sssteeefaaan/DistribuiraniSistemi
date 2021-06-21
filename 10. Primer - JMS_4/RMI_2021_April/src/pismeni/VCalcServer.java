/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pismeni;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stefan
 */
public class VCalcServer {

    public ICalcManager mngr = null;

    public VCalcServer(String host, String port, String service) throws RemoteException, AlreadyBoundException, MalformedURLException {
        LocateRegistry.createRegistry(Integer.parseInt(port));

        mngr = new CalcManager();

        Naming.bind("rmi://" + host + ":" + port + "/" + service, mngr);
    }

    public boolean run() {
        try {
            return mngr.RunNextVCalcRequest();
        } catch (Exception ex) {
            Logger.getLogger(VCalcServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException {
        VCalcServer s = new VCalcServer("localhost", "1099", "VCalcService");

        Scanner scan = new Scanner(System.in);
        String option;
        while (true) {
            System.out.println("Run/Exit");
            option = scan.nextLine().trim().toLowerCase();

            if (option.equals("exit")) {
                break;
            }

            if (option.equals("run")) {
                s.run();
            }
        }

        //System.exit(0);
    }
}
