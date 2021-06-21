
import com.sun.javafx.webkit.KeyCodeMap.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Stefan
 */
public class Sistem {

    public static Random rand = new Random();

    private Topic tKombinacija;
    private Destination dIzvuceniBroj;

    private TopicConnection tc;
    private Connection con;

    private TopicSession ts;
    private Session ses;

    private MessageProducer producer;
    private HashMap<Long, ArrayList<ArrayList<Integer>>> klijenti;

    public Sistem() throws NamingException, JMSException {
        klijenti = new HashMap();

        InitialContext context = new InitialContext();

        TopicConnectionFactory tcf = (TopicConnectionFactory) context.lookup("tcfJun2020");
        ConnectionFactory cf = (ConnectionFactory) context.lookup("cfJun2020");

        tKombinacija = (Topic) context.lookup("tKombinacijaJun2020");
        dIzvuceniBroj = (Destination) context.lookup("dIzvuceniBrojJun2020");

        context.close();

        tc = (TopicConnection) tcf.createConnection();
        con = (Connection) cf.createConnection();

        ts = (TopicSession) tc.createSession();
        ses = (Session) con.createSession();
    }

    private void startTopic() throws JMSException {
        TopicSubscriber subscriber = ts.createSubscriber(tKombinacija);
        subscriber.setMessageListener(ml -> {
            try {
                ObjectMessage newMessage = (ObjectMessage) ml;
                ArrayList<Integer> kombinacija = (ArrayList<Integer>) newMessage.getObject();
                long id = newMessage.getLongProperty("ID");

                if(!klijenti.containsKey(id))
                    klijenti.put(id, new ArrayList<ArrayList<Integer>>());
                
                klijenti.get(id).add(kombinacija);
                
                System.out.println("Klijent [" + Long.toString(id) + "] je uplatio kombinaciju:");
                kombinacija.forEach(el -> System.out.print(Integer.toString(el) + " "));
                System.out.println();
            } catch (JMSException ex) {
                Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        tc.start();
    }

    private void startProducer() throws JMSException
    {
        producer = ses.createProducer(dIzvuceniBroj);
        con.start();
    }

    public void start() throws JMSException {
        startTopic();
        startProducer();
    }

    public void izvuciBroj() throws JMSException {
        int broj = rand.nextInt(40);
        boolean test;
        for(Map.Entry<Long, ArrayList<ArrayList<Integer>>> entry : klijenti.entrySet())
        {
            TextMessage msg = ses.createTextMessage();
            msg.setText(Integer.toString(broj));
            msg.setLongProperty("ID", entry.getKey());
            
            test = false;
            for(ArrayList<Integer> kombinacija : entry.getValue())
            {
                if(test|=kombinacija.contains(broj))
                    break;
            }
            
            msg.setStringProperty("ImaBroj", Boolean.toString(test).toUpperCase());
            producer.send(msg);
        }
    }

    public void logout() throws JMSException {
        con.close();
        tc.close();
    }

    public static long GETID() {
        return rand.nextLong();
    }

    public static void main(String[] args) throws NamingException, JMSException {
        Sistem sis = new Sistem();
        sis.start();
        System.out.println("Uspesno povezivanje!");

        String option;
        boolean loop = true;
        Scanner s = new Scanner(System.in);
        while (loop) {
            System.out.println("1 - Izvuci broj / 2 - Exit");
            option = s.nextLine().trim().toLowerCase();

            switch (option) {
                case ("1"):
                case ("izvuci broj"):
                    sis.izvuciBroj();
                    break;
                    
                case ("2"):
                case ("exit"):
                    sis.logout();
                    loop = false;
                    break;
                    
                default:
                    break;
            }
        }
    }
}
