/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atomatizovanaproizvodnja;

import java.util.ArrayList;
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

/**
 *
 * @author Velja
 */
public class Klijent {
    
    private int id;
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
    
    public Klijent(int Id) throws NamingException, JMSException
    {
        id = Id;
        
        InitialContext ictx = new InitialContext();
        
        tNovi = (Topic)ictx.lookup("tNovi");
        qNoviOdgovor = (Queue) ictx.lookup("qNoviOdgovor");
        
        qPomoc = (Queue) ictx.lookup("qPomoc");
        qPomocOdgovor = (Queue) ictx.lookup("qPomocOdgovor");
        
        TopicConnectionFactory tcf = (TopicConnectionFactory)ictx.lookup("tcf");
        QueueConnectionFactory qcf = (QueueConnectionFactory)ictx.lookup("qcf");
        
        ictx.close();
        
        tc = (TopicConnection) tcf.createTopicConnection();
        ts = (TopicSession) tc.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);
        
        qc = (QueueConnection) qcf.createQueueConnection();
        qs = (QueueSession) qc.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
    }
    
    public void Start(ArrayList<String> poslovi) throws JMSException
    {
        TopicSubscriber subNovi =ts.createSubscriber(tNovi);
        
        tc.start();
        TopicPublisher novi = ts.createPublisher(tNovi);
        
        Message msg = ts.createMessage();
        msg.setIntProperty("IdN", id);
        
        novi.send(msg);
        ts.commit();
        
        subNovi.setMessageListener(new NoviMessageListener(this));
        
            QueueReceiver rNovog = qs.createReceiver(qNoviOdgovor, "idN = "+id);
            rNovog.setMessageListener(new NoviOdgovorMessageListener(this));
        
            sender = qs.createSender(qPomoc);
            noviSender = qs.createSender(qNoviOdgovor);
        
        for(String posao :poslovi)
        {
            QueueReceiver receiver = qs.createReceiver(qPomoc, "posao = '"+posao+"'");
            receiver.setMessageListener(new PomocMessageListener(posao, this));
        }
        
            QueueReceiver rPomoc = qs.createReceiver(qPomocOdgovor, "id = "+id);
            rPomoc.setMessageListener(new PomocOdgovorMessageListener(this));
        
        qc.start();
    }
    
    public void Pomoc(String posao) throws JMSException
    {
        Message msg = qs.createMessage();
        msg.setIntProperty("Id", id);
        msg.setStringProperty("posao", posao);
        sender.send(msg);
        qs.commit();
    }

    void ObradiNovog(Message msg) {
        try {
            int idNovi = msg.getIntProperty("idN");
            System.out.println("Novi:");
            System.out.println(idNovi);
            
            msg.setIntProperty("id", id);
            noviSender.send(msg);
            qs.commit();
        } catch (JMSException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    void ObradiNoviOdgovor(Message msg) {
        int idNovi;
        try {
            idNovi = msg.getIntProperty("id");
            System.out.println("Novi: "+idNovi);
        } catch (JMSException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void ObradiPozivZaPomoc(String posao, Message msg) {
        try {
            int idNovi = msg.getIntProperty("id");
            System.out.println("Pomoc za posao " + posao+" tazi " + idNovi);
            
            msg.setIntProperty("id", id);
            sender.send(msg);
            qs.commit();
        } catch (JMSException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void Pomogao(Message msg) {
        int idNovi;
        try {
            idNovi = msg.getIntProperty("id");
            System.out.println("Pomogao: "+idNovi);
        } catch (JMSException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
