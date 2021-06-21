import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.util.ArrayList;
import java.awt.*;
import java.io.*;

public class WhiteboardManager extends UnicastRemoteObject implements IWhiteboardManager
{
	private Vector<IShape> shapes;
	private ArrayList<IWhiteboardCallback> clients;
	private int version;
	
	public WhiteboardManager() throws RemoteException{
		this.shapes = new Vector<IShape>();
		this.clients = new ArrayList<IWhiteboardCallback>();
		this.version = 0;
	}
	
	public IShape addNewShape(String type, Rectangle enclosing, Color line, Color fill, boolean filled) throws RemoteException{
		this.version++;
		IShape temp = new Shape(type, enclosing, line, fill, filled);
		this.shapes.addElement(temp);
		
		this.alertChange();
		
		return temp;
	}
	
	public Vector<IShape> getAllShapes() throws RemoteException { return this.shapes; }
	
	public int getVersion() throws RemoteException { return this.version; }
	public void setVersion(int version) throws RemoteException { this.version = version; }
	
	private void alertChange() {
		for(IWhiteboardCallback client : this.clients)
		{
			if(client!= null){
				try{
					client.callback(this.version);
				}
				catch(Exception e){
					System.out.println("Error occurred: " + e);
				}
			}
		}
	}
	
	public synchronized void register(IWhiteboardCallback client) throws RemoteException { this.clients.add(client); }
	
	public synchronized void unregister(IWhiteboardCallback client) throws RemoteException { this.clients.remove(client); }
}