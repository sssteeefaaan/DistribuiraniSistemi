import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*;

public class MathQuizServer{

	public MathQuizServer(){
		
	}
	
	public static void main(String[] args){
		
		String host = "localhost";
		String port = "1099";
		String service = "MathQuiz";
		String link = "rmi://" + host + ":" + port + "/" + service;
		
		try{
			
			LocateRegistry.createRegistry(Integer.parseInt(port));
			System.out.println("Java RMI registry's been created at: " + port);
		}
		catch(Exception e)
		{
			System.out.println("Error occurred: " + e);
		}
		
		try{
			
			IQuiz quiz = new Quiz();
			
			quiz.addQuestion(new Question("1 + 1 = ", "1", "2", "3", 'b', 10));
			quiz.addQuestion(new Question("2 * 3 = ", "6", "2", "1", 'a', 10));
			quiz.addQuestion(new Question("10 / 2 = ", "1", "2", "5", 'c', 10));
			
			Naming.rebind(link, quiz);
			System.console().readLine("Whiteboard server's running on url: " + link + "\nPress any key to close...");
		}
		catch(Exception e)
		{
			System.out.println("Error occurred: " + e);
		}
	}
}