
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.awt.Rectangle;
import java.awt.Color;
import java.util.Vector;
import java.util.ArrayList;

public class WhiteboardManager extends UnicastRemoteObject implements IWhiteboardManager {

    private Vector<IShape> theList;
    private ArrayList<IWhiteboardCallback> clients = new ArrayList<IWhiteboardCallback>();
    private int version;

    public WhiteboardManager() throws RemoteException {
        theList = new Vector<IShape>();
        version = 0;
    }

    public IShape addNewShape(String aType, Rectangle anEnclosing, Color aLine, Color aFill, boolean anIsFilled) throws RemoteException {

        version++;
        IShape s = new Shape(aType, anEnclosing, aLine, aFill, anIsFilled);
        theList.addElement(s);

        callAllCallbacks();

        return (IShape) s;

    }

    public Vector<IShape> getAllShapes() throws RemoteException {
        return theList;
    }

    public int getVersion() throws RemoteException {
        return version;
    }

    public synchronized void register(IWhiteboardCallback c) throws RemoteException {
        clients.add(c);
    }

    public synchronized void unregister(IWhiteboardCallback c) throws RemoteException {
        clients.remove(c);
    }

    private void callAllCallbacks() throws RemoteException {
        for (IWhiteboardCallback c : clients) {
            if (c != null)
                    c.callback(version);
        }
    }
}
