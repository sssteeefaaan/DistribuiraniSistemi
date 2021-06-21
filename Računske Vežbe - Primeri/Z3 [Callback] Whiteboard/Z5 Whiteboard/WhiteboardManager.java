import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.awt.Rectangle;
import java.awt.Color;

public class WhiteboardManager extends UnicastRemoteObject implements IWhiteboardManager {
 
	 private Vector<IShape> theList;
	 private int version;
	 
	 public WhiteboardManager() throws RemoteException{
		 theList = new Vector<IShape>();
		 version = 0;
	 }
	 
	 public IShape addNewShape(String aType,Rectangle anEnclosing, Color aLine,Color aFill, boolean anIsFilled) throws RemoteException{		
		version++;		
		IShape s = new Shape(aType, anEnclosing,aLine,aFill,anIsFilled);				
		theList.addElement(s);
		return (IShape)s;
	 }
	 
	 public Vector<IShape> getAllShapes() throws RemoteException{
		return theList;
	 }
	 
	 public int getVersion() throws RemoteException{
		return version;
	 } 
 
}