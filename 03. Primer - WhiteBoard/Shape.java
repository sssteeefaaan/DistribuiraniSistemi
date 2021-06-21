import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.awt.Rectangle;
import java.awt.Color;

public class Shape extends UnicastRemoteObject implements IShape
{
	int version;
	public String type;
	public Rectangle enclosing;
	public Color line;
	public Color fill;
	public boolean filled;
	
	public Shape(String type, Rectangle enclosing, Color line, Color fill, boolean filled) throws RemoteException
	{
		this.type = type;
		this.enclosing = enclosing;
		this.line = line;
		this.fill = fill;
		this.filled = filled;
	}
	
	public int getVersion() throws RemoteException
	{
		return this.version;
	}
	
	public void setVersion(int version) throws RemoteException
	{
		this.version = version;
	}
	
	public String print() throws RemoteException
	{
		String out = this.type;
		out += ", " + this.enclosing.x + ", " + this.enclosing.y + ", " + this.enclosing.width + ", " + this.enclosing.height;
		
		if(this.filled)
			out += " [filled]";
			
		return out;
	}
}