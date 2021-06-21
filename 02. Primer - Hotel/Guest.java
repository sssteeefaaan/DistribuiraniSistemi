import java.io.Serializable;

public class Guest implements Serializable
{
	public String firstName;
	public String lastName;
	public String id;
	
	public Guest(String firstName, String lastName, String id)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
	}
}