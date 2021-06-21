import java.rmi.*;
import java.rmi.server.*;
import java.util.Vector;
import java.awt.Rectangle;
import java.awt.Color;
import java.io.IOException;
import java.util.*; 

public class WhiteboardClient{
	
	public IWhiteboardCallback callback;
	public IWhiteboardManager manager;
	
	public WhiteboardClient()
	{
		this.callback = null;
		this.manager = null;
	}
	
	public void run(String link)
	{
		try{
			this.manager = (IWhiteboardManager) Naming.lookup(link);
			System.out.println("Server found!");
			
			this.callback = new WhiteboardCallback();
			this.manager.register(this.callback);
			
		}
		catch(Exception e)
		{ System.out.println("Error occurred: " + e); }
		
		try{
			
			String option;
			
			while(true)
			{
				option = System.console().readLine("Draw/Exit: ").trim();
				
				if(option.equals("Draw")){
					this.manager.addNewShape("Line", new Rectangle(50, 50, 300, 500), Color.red, Color.blue, true);
					System.out.println("Shape stored!");
				}
				else if(option.equals("Exit"))
					break;
			}
			
			this.manager.unregister(this.callback);
		}
		catch(Exception e)
		{
			System.out.println("Error occurred: " + e);
		}
	}
	
	public void showWhiteboard()
	{
		try{
			System.out.println("Whiteboard version: " + Integer.toString(this.manager.getVersion()));
			
			Vector<IShape> shapes = this.manager.getAllShapes();
			
			for(IShape shape : shapes){
				if(shape != null){
					System.out.println(shape.print());
				}
			}
		}
		catch(Exception e)
		{ System.out.println("Error occurred: " + e); }
	}
	
	// Callback
	public class WhiteboardCallback extends UnicastRemoteObject implements IWhiteboardCallback{
		
		public WhiteboardCallback() throws RemoteException{}
		
		public void callback(int version) throws RemoteException
		{
			showWhiteboard();
			// if(manager.getVersion() < version){
				// manager.setVersion(version);
				// showWhiteboard();
			// }
		}
	}
	
	// Main
	public static void main(String args[])
	{
		String host = "localhost";
		String port = "1099";
		String service = "WhiteboardManager";
		String link = "rmi://" + host + ":" + port + "/" + service;
		
		WhiteboardClient client = new WhiteboardClient();
		client.run(link);
		
		System.exit(0);
	}
}