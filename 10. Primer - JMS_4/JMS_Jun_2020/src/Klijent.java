
import java.util.ArrayList;
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
public class Klijent {

    public static Random rand = new Random();

    private long id;
    private Topic tKombinacija;
    private Destination dIzvuceniBroj;

    private TopicConnection tc;
    private Connection con;

    private TopicSession ts;
    private Session ses;

    private TopicPublisher publisher;
    private MessageProducer sistem;
    private ArrayList<ArrayList<Integer>> kombinacije;

    public Klijent() throws NamingException, JMSException {
        this.id = GETID();
        kombinacije = new ArrayList();

        InitialContext context = new InitialContext();

        TopicConnectionFactory tcf = (TopicConnectionFactory) context.lookup("tcfJun2020");
        ConnectionFactory cf = (ConnectionFactory) context.lookup("cfJun2020");

        tKombinacija = (Topic) context.lookup("tKombinacijaJun2020");
        dIzvuceniBroj = (Destination) context.lookup("dIzvuceniBrojJun2020");
        // U zadatku stoji Destination, ali ja bih ovde stavio topic
        // idfk

        context.close();

        tc = (TopicConnection) tcf.createConnection();
        con = (Connection) cf.createConnection();

        ts = (TopicSession) tc.createSession();
        ses = (Session) con.createSession();
    }

    private void startTopic() throws JMSException {
        publisher = ts.createPublisher(tKombinacija);
        TopicSubscriber subscriber = ts.createSubscriber(tKombinacija, "NOT (ID = " + Long.toString(this.id) + ")", true);
        subscriber.setMessageListener(ml -> {
            try {
                ObjectMessage newMessage = (ObjectMessage) ml;
                ArrayList<Integer> kombinacija = (ArrayList<Integer>) newMessage.getObject();
                long id = newMessage.getLongProperty("ID");

                System.out.println("Klijent [" + Long.toString(id) + "] je uplatio kombinaciju:");
                kombinacija.forEach(el -> System.out.print(Integer.toString(el) + " "));
                System.out.println();
            } catch (JMSException ex) {
                Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        tc.start();
    }

    private void startConsumer() throws JMSException {
        MessageConsumer consumerImaBroj = ses.createConsumer(dIzvuceniBroj, "ID = " + Long.toString(this.id) + " AND ImaBroj = 'TRUE'", true);
        consumerImaBroj.setMessageListener(ml -> {
            try {
                int broj;

                TextMessage txtMsg = (TextMessage) ml;
                broj = Integer.parseInt(txtMsg.getText());

                System.out.println("Izvucen je novi broj: " + Integer.toString(broj));
                System.out.println("Vase kombinacije koje sadrze izvucen broj: ");
                kombinacije.forEach(kombinacija
                        -> {
                    if (kombinacija.contains(broj)) {
                        kombinacija.forEach(number -> System.out.print(Integer.toString(number) + " "));
                        System.out.println();
                    }
                });
            } catch (JMSException ex) {
                Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        MessageConsumer consumerNemaBroj = ses.createConsumer(dIzvuceniBroj, "ID = " + this.id + " AND ImaBroj = 'FALSE'", true);
        consumerNemaBroj.setMessageListener(ml
                -> {
            try {
                System.out.println("Izvucen je novi broj: " + ml.getIntProperty("Broj"));
                System.out.println("Nazalost nijedna Vasa kombinacija ga ne sadrzi");
            } catch (JMSException ex) {
                Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        con.start();
    }

    public void start() throws JMSException {
        startTopic();
        startConsumer();
    }

    public void posaljiKombinaciju() throws JMSException {
        System.out.print("Unesite Vasu kombinaciju: ");
        Scanner s = new Scanner(System.in);
        ArrayList<Integer> kombinacija = new ArrayList(7);
        for (int i = 0; i < 7; i++) {
            kombinacija.add(s.nextInt());
        }

        kombinacije.add(kombinacija);

        ObjectMessage newMessage = ts.createObjectMessage();
        newMessage.setObject(kombinacija);
        newMessage.setLongProperty("ID", this.id);

        publisher.send(newMessage);
    }

    public void logout() throws JMSException {
        con.close();
        tc.close();
    }

    public static long GETID() {
        return rand.nextLong();
    }

    public static void main(String[] args) throws NamingException, JMSException {
        Klijent k = new Klijent();
        k.start();
        System.out.println("Uspesno povezivanje na sistem!");

        String option;
        boolean loop = true;
        Scanner s = new Scanner(System.in);
        while (loop) {
            System.out.println("1 - Unesi kombinaciju / 2 - Exit");
            option = s.nextLine().trim().toLowerCase();

            switch (option) {
                case ("1"):
                case ("unesi kombinaciju"):
                    k.posaljiKombinaciju();
                    break;
                    
                case ("2"):
                case ("exit"):
                    k.logout();
                    loop = false;
                    break;
                    
                default:
                    break;
            }
        }
    }
}
