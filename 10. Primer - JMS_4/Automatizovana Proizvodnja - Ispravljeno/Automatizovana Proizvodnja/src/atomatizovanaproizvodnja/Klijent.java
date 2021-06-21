package atomatizovanaproizvodnja;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Klijent {
    
    private final int id;
    private final Topic tNovi;
    private final Queue qNoviOdgovor;
    private final Queue qPomoc;
    private final Queue qPomocOdgovor;
    private final TopicSession ts;
    private final QueueSession qs;
    private final TopicConnection tc;
    private QueueSender sender;
    private final QueueConnection qc;
    private QueueSender noviSender;
    private QueueSender senderPomoc;
    
    public Klijent(int ID) throws NamingException, JMSException
    {
        id = ID;
        
        InitialContext ictx = new InitialContext();
        
        tNovi = (Topic)ictx.lookup("tNovi");
        qNoviOdgovor = (Queue) ictx.lookup("qNoviOdgovor");
        
        qPomoc = (Queue) ictx.lookup("qPomoc");
        qPomocOdgovor = (Queue) ictx.lookup("qPomocOdgovor");
        
        TopicConnectionFactory tcf = (TopicConnectionFactory)ictx.lookup("tcf");
        QueueConnectionFactory qcf = (QueueConnectionFactory)ictx.lookup("qcf");
        
        ictx.close();
        
        tc = (TopicConnection) tcf.createTopicConnection();
        ts = (TopicSession) tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        
        qc = (QueueConnection) qcf.createQueueConnection();
        qs = (QueueSession) qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    }
    
    public void Start(String[] poslovi) throws JMSException
    {
        TopicPublisher novi = ts.createPublisher(tNovi);
        sender = qs.createSender(qPomoc);
        // 4. Greška
        senderPomoc = qs.createSender(qPomocOdgovor);
        noviSender = qs.createSender(qNoviOdgovor);
        
        // 1. Greška, Klijent ujedno i objavljuje na ovaj topic, pa bi trebalo da se filtrira
        // kako ne bi dobijao sopstvene poruke
        TopicSubscriber subNovi = ts.createSubscriber(tNovi, "NOT (IDnovi = " + id + ")", true);
        subNovi.setMessageListener(new NoviMessageListener(this));
          
        QueueReceiver rNovog = qs.createReceiver(qNoviOdgovor, "IDnovi = " + id);
        rNovog.setMessageListener(new NoviOdgovorMessageListener(this));
        
        for(String posao : poslovi)
        {
            QueueReceiver receiver = qs.createReceiver(qPomoc, "Posao = '" + posao + "'");
            receiver.setMessageListener(new PomocMessageListener(this));
        }
        
        QueueReceiver rPomoc = qs.createReceiver(qPomocOdgovor, "IDdobio = " + id);
        rPomoc.setMessageListener(new PomocOdgovorMessageListener(this));
        
        qc.start();
        tc.start();
        
        Message msg = ts.createMessage();
        msg.setIntProperty("IDnovi", id);
        novi.send(msg);
    }
    
    public void Pomoc(String posao) throws JMSException
    {
        Message msg = qs.createMessage();
        msg.setIntProperty("IDtrazi", id);
        msg.setStringProperty("Posao", posao);
        sender.send(msg);
    }

    void ObradiNovog(Message msg) {
        try {
            System.out.println("Klijent [" + id + "] Novi klijent: " + msg.getIntProperty("IDnovi"));
            
            // 2. Greška, poruka msg se prima iz sesije kreirane iz topic-a tNovi
            // ne može se koristiti i za slanje u qNovi, nego mora da se kreira nova poruka za taj queue
            // iz odgovarajuće sesije
            Message newMessage = qs.createMessage();
            newMessage.setIntProperty("IDnovi", msg.getIntProperty("IDnovi"));
            newMessage.setIntProperty("ID", id);
            noviSender.send(newMessage);
        } catch (JMSException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void ObradiNoviOdgovor(Message msg) {
        try {
            System.out.println("Klijent [" + id + "] Posoji klijent: " + msg.getIntProperty("ID"));
        } catch (JMSException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void ObradiPozivZaPomoc(Message msg) {
        try {
            System.out.println("Klijent [" + id + "] Pomoc za posao: '" + msg.getStringProperty("Posao") + "', trazi klijent {" + msg.getIntProperty("IDtrazi") + "}");
            
            Message newMessage = qs.createMessage();
            newMessage.setStringProperty("Posao", msg.getStringProperty("Posao"));
            newMessage.setIntProperty("IDpomogao", id);
            newMessage.setIntProperty("IDdobio", msg.getIntProperty("IDtrazi"));
            senderPomoc.send(newMessage);
            
        } catch (JMSException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception e)
        {
            System.out.println(msg.toString());
        }
    }

    void Pomogao(Message msg) {
        try {
            System.out.println("Klijent [" + id +"] Za posao ' " + msg.getStringProperty("Posao") + "' je pomogao je klijent {" + msg.getIntProperty("IDpomogao") + "}");
        } catch (JMSException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
