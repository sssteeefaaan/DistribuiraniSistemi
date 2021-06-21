import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GymReservationManager extends Remote{
    Reservation makeReservation(int month, int day, int hour, int numHours) throws RemoteException;
    Reservation extendReservation(Reservation reservation, int numExtraHours) throws RemoteException;
    void cancelReservation(Reservation res) throws RemoteException;
}
