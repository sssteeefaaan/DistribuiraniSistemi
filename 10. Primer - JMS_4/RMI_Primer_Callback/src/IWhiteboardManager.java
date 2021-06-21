import java.rmi.*;
import java.util.Vector;
import java.awt.Rectangle;
import java.awt.Color;

public interface IWhiteboardManager extends Remote {

    IShape addNewShape(String aType,Rectangle anEnclosing, Color aLine, Color aFill, boolean anIsFilled) throws RemoteException;

    Vector<IShape> getAllShapes() throws RemoteException;

    int getVersion() throws RemoteException;

    void register (IWhiteboardCallback callback) throws RemoteException;

    void unregister (IWhiteboardCallback callback) throws RemoteException;

}