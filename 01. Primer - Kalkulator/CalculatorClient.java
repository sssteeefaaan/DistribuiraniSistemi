import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.util.Scanner;

public class CalculatorClient {
	public Calculator proxy;

	public CalculatorClient(String host, String port, String service)
			throws NotBoundException, RemoteException, MalformedURLException {
		proxy = (Calculator) Naming.lookup("rmi://" + host + ":" + port + "/" + service);
	}

	public static void main(String[] args) throws NotBoundException, RemoteException, MalformedURLException, Exception {
		CalculatorClient c = new CalculatorClient("127.0.0.1", "1099", "Calculator");

		Scanner s = new Scanner(System.in);
		String input;
		double a, b;

		while (true) {

			System.out.println();
			System.out.println("-----------Glavni menu-----------");
			System.out.println("Odaberite opciju:");
			System.out.println("+");
			System.out.println("-");
			System.out.println("*");
			System.out.println("/");
			System.out.println("Exit");
			System.out.println();

			input = s.nextLine();
			input = input.toLowerCase();
			if (input.equals("exit"))
				break;

			if (!(input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/"))) {
				System.out.println("Pogrešna opcija!");
				continue;
			}

			System.out.println("Unesite prvi operand!");
			a = Double.parseDouble(s.nextLine());

			System.out.println();

			System.out.println("Unesite drugi operand!");
			b = Double.parseDouble(s.nextLine());

			System.out.println();

			switch (input) {
				case ("+"):
					System.out.println("Rezultat je: " + Double.toString(c.proxy.add(a, b)));
					break;
				case ("-"):
					System.out.println("Rezultat je: " + Double.toString(c.proxy.sub(a, b)));
					break;
				case ("*"):
					System.out.println("Rezultat je: " + Double.toString(c.proxy.mul(a, b)));
					break;
				case ("/"):
					System.out.println("Rezultat je: " + Double.toString(c.proxy.div(a, b)));
					break;
			}
		}

		s.close();

		System.exit(0);
	}
}