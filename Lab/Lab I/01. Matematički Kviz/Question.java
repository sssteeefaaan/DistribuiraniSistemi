import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Question extends UnicastRemoteObject implements IQuestion{
	
	private String question;
	private String[] answers = new String[3];
	private char correctAnswer;
	private int points;
	
	public Question(String question, String a, String b, String c, char correctAnswer, int points) throws RemoteException{
		
		this.question = question;
		this.answers[0] = a;
		this.answers[1] = b;
		this.answers[2] = c;
		
		this.correctAnswer = correctAnswer;
		this.points = points;
	}
	
	public int getPoints() throws RemoteException{
		
		return this.points;
	}
	
	public String getContent() throws RemoteException{
		
		String ret = this.question + "?\n";
		for(int i = 0; i < this.answers.length; i++){
			if(i != 0)
				ret += "\t";
			ret += (char)('a' + i) + ") " + this.answers[i];
		}
		return ret + "\n";
	}
	
	public char getCorrectAnswer() throws RemoteException
	{
		return this.correctAnswer;
	}
}