package atomatizovanaproizvodnja;

import java.util.*;
import javax.jms.JMSException;
import javax.naming.NamingException;

public class AtomatizovanaProizvodnja {

    public static void main(String[] args) throws NamingException, JMSException {
        List<Klijent> k = new ArrayList<>();
        String[] poslovi = {"Kopanje", "Pecanje", "Pranje", "Peglanje", "Kupovina", "Mesanje maltera", "Sviranje", "Kuglanje"};
        
        for(int i = 0; i < 10; i++)
            k.add(new Klijent(i + 1));
        
        for(Klijent kl : k)
            kl.Start(poslovi);
        
        for(int i = 3; i < 6; i++)
           k.get(i).Pomoc(poslovi[i + 1]);
        
        new Scanner(System.in).next();
    }
    
}
