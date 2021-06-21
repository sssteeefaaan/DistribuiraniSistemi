
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Stefan
 */
public class GymReservationClient {

    private GymReservationManager manager;
    private HashMap<Integer, Reservation> rezervacije;

    public GymReservationClient(String host, String port, String service) throws RemoteException, MalformedURLException, NotBoundException {
        manager = (GymReservationManager) Naming.lookup("rmi://" + host + ":" + port + "/" + service);
        rezervacije = new HashMap();
    }

    public Reservation rezervisi(int month, int day, int hour, int numHours) throws RemoteException {
        Reservation r = manager.makeReservation(month, day, hour, numHours);
        if (r != null) {
            rezervacije.put(r.getID(), r);
        }

        return r;
    }

    public Reservation produzi(int id, int numHours) throws RemoteException {
        Reservation r = rezervacije.get(id);

        if (r != null) {
            r = manager.extendReservation(r, numHours);

            if (r != null) {
                rezervacije.replace(id, r);
            }
        }

        return r;
    }

    public void otkazi(int id) throws RemoteException {
        manager.cancelReservation(rezervacije.get(id));
        rezervacije.remove(id);
    }

    public void prikaziRezervacije() throws RemoteException {
        int i = 1;
        Reservation r = null;
        for (Map.Entry<Integer, Reservation> entry : rezervacije.entrySet()) {
            r = entry.getValue();
            System.out.println(Integer.toString(i++) + ". Rezervacija:");
            System.out.println("\tID: " + Integer.toString(r.getID()));
            System.out.println("\tMesec: " + Integer.toString(r.getMonth()));
            System.out.println("\tDan: " + Integer.toString(r.getDay()));
            System.out.println("\tSat: " + Integer.toString(r.getHour()));
            System.out.println("\tBroj sati za produzenje: " + Integer.toString(r.getNumHours()));

            System.out.println();
        }
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
        GymReservationClient client = new GymReservationClient("localhost", "1099", "GymReservationService");
        System.out.println("Uspesno povezivanje na servis!");

        String option;
        Scanner s = new Scanner(System.in);
        boolean loop = true;
        int id, month, day, hour, numberOfHours;
        Reservation temp = null;
        while (loop) {
            System.out.println("\n***Glavni menu***");
            System.out.println("1 - Napravite novu rezervaciju");
            System.out.println("2 - Prikazi svoje rezervacije");
            System.out.println("3 - Produzite rezervaciju");
            System.out.println("4 - Otkazite rezervaciju");
            System.out.println("5 - Exit\n");
            System.out.print("Unesite opciju: ");

            option = s.nextLine();
            switch (option) {
                case ("1"):
                    System.out.println("\n***Nova rezervacija***");

                    System.out.println("\nUnesite mesec: ");
                    month = Integer.parseInt(s.nextLine().trim());

                    System.out.println("\nUnesite dan: ");
                    day = Integer.parseInt(s.nextLine().trim());

                    System.out.println("\nUnesite sat: ");
                    hour = Integer.parseInt(s.nextLine().trim());

                    System.out.println("\nUnesite broj sati: ");
                    numberOfHours = Integer.parseInt(s.nextLine().trim());

                    if (client.rezervisi(month, day, hour, numberOfHours) != null) {
                        System.out.println("Uspesno kreirana rezervacija!");
                    } else {
                        System.out.println("Rezervacija nije uspela!");
                    }

                    break;

                case ("2"):
                    System.out.println("\n***Prikaz rezervacija***");
                    client.prikaziRezervacije();
                    break;

                case ("3"):
                    System.out.println("\n***Produzi rezervaciju***");

                    System.out.println("\nUnesite id rezervacije: ");
                    id = Integer.parseInt(s.nextLine().trim());

                    System.out.println("\nUnesite broj sati za produzenje: ");
                    numberOfHours = Integer.parseInt(s.nextLine().trim());

                    if (client.produzi(id, numberOfHours) != null) {
                        System.out.println("Produzenje je uspelo!");
                    } else {
                        System.out.println("Produzenje nije uspelo!");
                    }
                    break;
                case ("4"):
                    System.out.println("\n***Otkazi rezervaciju***");

                    System.out.println("\nUnesite id rezervacije: ");
                    id = Integer.parseInt(s.nextLine().trim());

                    client.otkazi(id);
                    break;
                case ("5"):
                    System.out.println("\n***Exit***");
                    loop = false;
                    break;
                default:
                    System.out.println("\nUnesite jedan od brojeva pridruzenih opcijama!");
                    break;
            }
        }

        System.exit(0);
    }
}
