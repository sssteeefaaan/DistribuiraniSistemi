import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IQuestion extends Remote{
	
	public String getContent() throws RemoteException;
	
	public char getCorrectAnswer() throws RemoteException;
	public int getPoints() throws RemoteException;
}