
import java.util.HashMap;
import java.util.Scanner;
import javax.jms.JMSException;
import javax.naming.NamingException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author stefa
 */
public class eProdavnica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NamingException, JMSException {
        HashMap<String, Proizvod> proizvodi = new HashMap<>();
        
        for(int i = 0; i < 5; i++)
            proizvodi.put("123" + i, new Proizvod("Lutka" + i, "123" + i, i));
        
        Centrala c = new Centrala();
        c.Start(proizvodi);
        
        new Scanner(System.in).nextLine();
    }
}
