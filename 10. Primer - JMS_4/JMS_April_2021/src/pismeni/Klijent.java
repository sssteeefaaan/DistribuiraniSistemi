package pismeni;


import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Topic;
import javax.jms.Queue;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Klijent {

    private TopicConnectionFactory tcf;
    private QueueConnectionFactory qcf;

    private Topic tPrijava;
    private Queue qPrijava;

    private Queue qPotraziIgru;
    private Queue qPosaljiIgru;

    private QueueConnection qc;
    private QueueSession qs;

    private TopicConnection tc;
    private TopicSession ts;

    private int id;
    private QueueSender posaljiIgru;
    private QueueSender traziIgru;

    public Klijent(int ID) throws NamingException, JMSException {
        this.id = ID;

        InitialContext context = new InitialContext();

        tcf = (TopicConnectionFactory) context.lookup("tcfApril2021");
        qcf = (QueueConnectionFactory) context.lookup("qcfApril2021");

        tPrijava = (Topic) context.lookup("tPrijavaApril2021");
        qPrijava = (Queue) context.lookup("qPrijavaApril2021");

        qPotraziIgru = (Queue) context.lookup("qPotraziIgruApril2021");
        qPosaljiIgru = (Queue) context.lookup("qPosaljiIgruApril2021");

        context.close();

        qc = (QueueConnection) qcf.createConnection();
        tc = (TopicConnection) tcf.createConnection();

        qs = (QueueSession) qc.createSession();
        ts = (TopicSession) tc.createSession();
    }

    public void start() throws JMSException {
        QueueSender producer = qs.createSender(qPrijava);
        QueueReceiver receiver = qs.createReceiver(qPrijava, "ID = " + this.id);
        receiver.setMessageListener(ml
                -> {
            try {
                System.out.println("Klijent [" + ml.getIntProperty("IDOnline") + "] je online!");
            } catch (JMSException ex) {
                Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        TopicPublisher publisher = ts.createPublisher(tPrijava);
        TopicSubscriber subscriber = ts.createSubscriber(tPrijava, "NOT (IDNew = " + this.id + ")", true);
        subscriber.setMessageListener(ml
                -> {
            try {
                int idNEW = ml.getIntProperty("IDNew");
                System.out.println("Korisnik [" + Integer.toString(idNEW) + "] se ulogovao!");

                Message newMessage = qs.createMessage();
                newMessage.setIntProperty("ID", idNEW);
                newMessage.setIntProperty("IDOnline", this.id);

                producer.send(newMessage);
            } catch (JMSException ex) {
                Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        traziIgru = qs.createSender(qPotraziIgru);
        posaljiIgru = qs.createSender(qPosaljiIgru);

        QueueReceiver primiIgru = qs.createReceiver(qPosaljiIgru, "IDTrazi = " + this.id);
        primiIgru.setMessageListener(ml
                -> {
            try {
                System.out.println("Korisnik [" + ml.getIntProperty("ID") + "] vam je poslao igru '" + ml.getStringProperty("Igra") + "'");
            } catch (JMSException ex) {
                Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        Message msg = ts.createMessage();
        msg.setIntProperty("IDNew", this.id);
        
        publisher.send(msg);
        
        qc.start();
        tc.start();
    }

    public void potraziIgru(String igra) throws JMSException {
        Message newMessage = qs.createMessage();
        newMessage.setIntProperty("ID", id);
        newMessage.setStringProperty("Igra", igra);

        traziIgru.send(newMessage);
        System.out.println("Poruka o potrazi za igrom poslata!");
    }

    public void podeliIgru(String igra) throws JMSException {
        QueueReceiver deljenje = qs.createReceiver(qPotraziIgru, "Igra = '" + igra + "'");
        deljenje.setMessageListener(ml
                -> {
            try {
                int id = ml.getIntProperty("ID");
                String igraTrazi = ml.getStringProperty("Igra");// isto kao i igra jer za to slusa

                System.out.println("Korisnik [" + id + "] trazi video igru '" + igra + "' koju vi posedujete.");

                Message newMessage = qs.createMessage();
                newMessage.setIntProperty("ID", this.id);
                newMessage.setIntProperty("IDTrazi", id);
                newMessage.setStringProperty("Igra", igra);
                posaljiIgru.send(newMessage);
            } catch (JMSException ex) {
                Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        System.out.println("Uspesno podeljena igra: '" + igra + "'");
    }
    
    public void odjava() throws JMSException
    {
        qc.close();
        tc.close();
        System.out.println("Uspesna odjava!");
    }

    public static void main(String[] args) throws NamingException, JMSException {
        Random rand = new Random();
        
        int id = rand.nextInt(100);
        Klijent k = new Klijent(id);
        k.start();
        System.out.println("Klijent ulogovan sa ID-em " + id);

        String option;
        Scanner s = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            System.out.println("1 - Objavi Igru / 2 - Potrazi Igru / 3 - Exit");
            option = s.nextLine().trim().toLowerCase();

            switch (option) {
                case ("1"):
                case ("objavi igru"):
                    System.out.print("Unesite naziv igre koju zelite da podelite: ");
                    k.podeliIgru(s.nextLine().trim());
                    break;
                    
                case ("2"):
                case ("potrazi igru"):
                    System.out.print("Unesite naziv igre koju zelite da pronadjete: ");
                    k.potraziIgru(s.nextLine().trim());
                    break;

                case ("3"):
                case ("exit"):
                    loop = false;
                    break;

                default:
                    break;
            }
        }
        
        k.odjava();
    }
}
