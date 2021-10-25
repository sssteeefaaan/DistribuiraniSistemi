import java.net.MalformedURLException;
import java.nio.channels.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class CalculatorServer {
	public CalculatorServer(String host, String port, String service) throws RemoteException, MalformedURLException, AlreadyBoundException {

		LocateRegistry.createRegistry(Integer.parseInt(port));
		System.out.println("java RMI registry created");

		Calculator calc = new CalculatorImp();
		Naming.rebind("rmi://" + host + ":" + port + "/" + service, calc);
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException {
		new CalculatorServer("127.0.0.1", "1099", "Calculator");

		System.out.println("Server started");
		System.out.println("Enter <CR> to end");

		Scanner s = new Scanner(System.in);
		s.next();
		s.close();

		System.exit(0);
	}
}