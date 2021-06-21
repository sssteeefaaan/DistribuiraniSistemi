import java.rmi.*;
import java.rmi.server.*;

public class MathQuizClient{

	public MathQuizClient(){}
	
	public static void main(String[] args){
	
		String host = "localhost";
		String port = "1099";
		String service = "MathQuiz";
		String link = "rmi://" + host + ":" + port + "/" + service;
		
		try{
			IQuiz quiz = (IQuiz) Naming.lookup(link);
			
			int noQuestions = quiz.getNumberOfQuestions();
			
			quiz.start();
			
			for(int i = 0; i < noQuestions; i++)
			{
				for(int j = 0; j < 20; j++)
					System.out.print("-");
				System.out.println("-");
				
				System.out.println("Question " + String.valueOf(i + 1) + "\n");
				System.out.println(quiz.getQuestion().getContent());
				quiz.answer(System.console().readLine("Input a, b or c: "));
			}
			
			System.out.println("\nPoints won: " + quiz.getPoints());
		}
		catch(Exception e)
		{
			System.out.println("Error occurred: " + e);
		}
	}
}