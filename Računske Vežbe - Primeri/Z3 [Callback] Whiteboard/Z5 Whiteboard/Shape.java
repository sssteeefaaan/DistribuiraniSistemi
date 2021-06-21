import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.awt.Rectangle;
import java.awt.Color;

public class Shape extends UnicastRemoteObject implements IShape {
 
	 int myVersion;	 	 
	 public String type;
	 public Rectangle enclosing;
	 public Color line;
	 public Color fill;
	 public boolean isFilled;

	 public Shape(String aType,Rectangle anEnclosing, Color aLine,Color aFill, boolean anIsFilled) throws RemoteException {
		type = aType;
		enclosing = anEnclosing;
		line = aLine;
		fill = aFill;
		isFilled = anIsFilled;
	 }

	 public int getVersion() throws RemoteException {
		return myVersion;
	 }
	 
	 public void setVersion(int ver) throws RemoteException {
		 myVersion=ver;
	 } 
	 
	 public String print() throws RemoteException {
		
		String out="";
		
		out+=type+"";
		out+=enclosing.x + " , " + enclosing.y + " , " + enclosing.width + " , " + enclosing.height;
		
		//System.out.print(type);
		//System.out.print(enclosing.x + " , " + enclosing.y + " , " + enclosing.width + " , " + enclosing.height);
		
		if (isFilled) {
			out+="-filled";
			//System.out.println(" filled"); 
		} else {
			out+="not filled";
			//System.out.println(" not filled");
		}
		
		return out;
		
	}
	 
}