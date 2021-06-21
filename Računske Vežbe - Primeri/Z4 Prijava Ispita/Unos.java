import java.io.*;

public class Unos
{

 public String getUserInput(String poruka)
 { 
	String inputLine = null;
	System.out.print(poruka + " ");
	
	try
	{
		BufferedReader is = new BufferedReader (new InputStreamReader(System.in) );
		inputLine = is.readLine();
	
		if (inputLine.length() == 0) return null;
	}
	catch (IOException e)
	{
		System.out.println("IOException: " + e);
	}
 
	return inputLine;
 
 }
 
}