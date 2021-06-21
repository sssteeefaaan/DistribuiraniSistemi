import java.rmi.*;
import java.util.Vector;
import java.awt.*;

public interface IWhiteboardManager extends Remote
{
	IShape addNewShape(String type, Rectangle enclosing, Color line, Color fill, boolean filled) throws RemoteException;
	Vector<IShape> getAllShapes() throws RemoteException;
	int getVersion() throws RemoteException;
	void setVersion(int version) throws RemoteException;
	
	void register(IWhiteboardCallback callback) throws RemoteException;
	void unregister(IWhiteboardCallback callback) throws RemoteException;
}