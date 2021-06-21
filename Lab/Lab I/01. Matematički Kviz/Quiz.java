import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Quiz extends UnicastRemoteObject implements IQuiz{
	
	int points;
	int currentQuestion;
	ArrayList<IQuestion> questions;
	
	public Quiz() throws RemoteException{
		
		this.start();
		this.questions = new ArrayList<IQuestion>();
	}
	
	public void start() throws RemoteException{
		
		this.points = this.currentQuestion = 0;
	}
	
	public IQuestion getQuestion() throws RemoteException{
		
		if(this.currentQuestion < this.questions.size())
			return this.questions.get(this.currentQuestion);
		return null;
	}
	
	public void answer(String answer) throws RemoteException{
		
		IQuestion temp = this.getQuestion();
		if(temp != null && answer.charAt(0) == temp.getCorrectAnswer())
			this.points += temp.getPoints();
		this.currentQuestion++;
	}
	
	public int getPoints() throws RemoteException{
		
		return this.points;
	}
	
	public void addQuestion(IQuestion question) throws RemoteException{
		
		this.questions.add(question);
	}
	
	public int getNumberOfQuestions() throws RemoteException{
		return this.questions.size();
	}
}