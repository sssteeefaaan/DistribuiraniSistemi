import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IQuiz extends Remote {
	
	public void start() throws RemoteException;
	public IQuestion getQuestion() throws RemoteException;
	public void answer(String answer) throws RemoteException;
	public int getPoints() throws RemoteException;
	
	public void addQuestion(IQuestion question) throws RemoteException;
	public int getNumberOfQuestions() throws RemoteException;
}