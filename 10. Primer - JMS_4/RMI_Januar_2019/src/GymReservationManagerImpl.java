
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GymReservationManagerImpl extends UnicastRemoteObject implements GymReservationManager {

    private static HashMap<Integer, Reservation> rezervacije = new HashMap();
    private static Random rand = new Random();

    @Override
    public Reservation makeReservation(int month, int day, int hour, int numHours) throws RemoteException {

        if (day > 31 || day < 0
                || month > 12 || month < 0
                || hour > 24 || hour < 0
                || numHours > 24 || numHours < 0) {
            return null;
        }
        for (Map.Entry<Integer, Reservation> entry : rezervacije.entrySet()) {
            if (entry.getValue().getDay() == day && entry.getValue().getMonth() == month) {
                return null;
            }
        }
        int ID = rand.nextInt(999);

        Reservation ret = new ReservationImpl(ID, month, day, hour, numHours);
        rezervacije.put(ID, ret);
        return ret;
    }

    @Override
    public Reservation extendReservation(Reservation reservation, int numExtraHours) throws RemoteException {
        int ID = reservation.getID();

        Reservation temp = rezervacije.get(reservation.getID());
        if (temp == null) {
            return null;
        }

        if (temp.getHour() + numExtraHours > 24) {
            return null;
        }

        temp.setNumHours(numExtraHours);

        return temp;
    }

    @Override
    public void cancelReservation(Reservation res) throws RemoteException {
        rezervacije.remove(res.getID());
    }

}
