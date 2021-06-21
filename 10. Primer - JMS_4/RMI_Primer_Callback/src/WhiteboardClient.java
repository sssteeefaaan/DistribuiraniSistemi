
import java.rmi.*;
import java.awt.Rectangle;
import java.awt.Color;
import java.net.MalformedURLException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class WhiteboardClient extends UnicastRemoteObject implements IWhiteboardCallback {

    public IWhiteboardManager mngr = null;

    public WhiteboardClient() {
    }

    public void run() throws RemoteException, NotBoundException, MalformedURLException {
        mngr = (IWhiteboardManager) Naming.lookup("rmi://localhost:1099/Whiteboard");
        System.out.println("Found server");

        mngr.register(this);

        String option;
        Scanner scn = new Scanner(System.in);

        while (true) {
            System.out.println("draw/exit?");
            option = scn.nextLine().trim();

            if (option.equals("draw")) {

                mngr.addNewShape("LINE", new Rectangle(50, 50, 300, 400), Color.red, Color.blue, false);
                System.out.println("Stored shape");

            } else if (option.equals("exit")) {
                break;
            }
        }

        mngr.unregister(this);
    }

    public void showWhiteboard() throws RemoteException {
        System.out.println("Whiteboard content");

        Vector sList = mngr.getAllShapes();

        for (int i = 0; i < sList.size(); i++) {
            IShape s = (IShape) sList.elementAt(i);
            System.out.println(s.print());
        }
    }

    public static void main(String args[]) throws RemoteException, NotBoundException, MalformedURLException {
        WhiteboardClient instance = new WhiteboardClient();
        instance.run();
        System.exit(0);
    }

    @Override
    public void callback(int version) throws RemoteException {
        System.out.println("Current Whiteboard version is: " + version);
        showWhiteboard();
    }
}
