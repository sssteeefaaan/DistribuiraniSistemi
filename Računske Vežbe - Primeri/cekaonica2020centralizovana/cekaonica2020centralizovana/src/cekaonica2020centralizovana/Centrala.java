/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cekaonica2020centralizovana;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Velja
 */
public class Centrala {

    //  Centrala
    private QueueConnection qc;
    private TopicConnection tc;
    QueueSession qs;
    TopicSession ts;
    
    QueueSender senderStampa;
    TopicPublisher publishRoto;
    TopicPublisher publishStigao;
    
    public Centrala() throws NamingException, JMSException
    {
        System.out.println("Pribavljanje contexta");
        InitialContext ictx = new InitialContext();
        
        Queue obavestiLekaraCentrala = (Queue)ictx.lookup("qObevestiCentralu");
        Queue stampa = (Queue) ictx.lookup("qStampa");
        Topic obavestiLekara = (Topic)ictx.lookup("tObevestiLekara");
        Queue stampaCentrala = (Queue) ictx.lookup("qStampaCentrala");
        QueueConnectionFactory qcf = (QueueConnectionFactory)ictx.lookup("qcfCekaonica2020");
        TopicConnectionFactory tcf = (TopicConnectionFactory)ictx.lookup("tcfCekaonica2020");
        
        ictx.close();
        
        System.out.println("Pribavljanje connection");
        qc = (QueueConnection) qcf.createQueueConnection();
        qs = (QueueSession) qc.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
        
        tc = (TopicConnection) tcf.createTopicConnection();
        ts = (TopicSession) tc.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);
        
        senderStampa = (QueueSender) qs.createSender(stampa);
        
        QueueReceiver receiverLekar = (QueueReceiver) qs.createReceiver(obavestiLekaraCentrala);
        receiverLekar.setMessageListener(new RedirectStampaMessageListener(this));
        QueueReceiver receiverStampa = (QueueReceiver) qs.createReceiver(stampaCentrala);
        receiverStampa.setMessageListener(new StampaMessageListener());
        
        qc.start();;
        tc.start();
        System.out.println(">>>Centrala<<<");
    }

    void ObjaviStampu(String lekar, String Pacijent) throws JMSException {
        
        TextMessage msg = qs.createTextMessage();
        msg.setText(Pacijent);
        
        senderStampa.send(msg);
        
        qs.commit();
        
    }

    void AzurirajRoto(String lekar, String Pacijent) throws JMSException {
        
        TextMessage msg = qs.createTextMessage();
        msg.setText(Pacijent);
        msg.setStringProperty("Lekar", lekar);
        
        publishRoto.send(msg);
        
        qs.commit();
    }

    private void Zatvori() throws JMSException {
        qc.close();
        tc.close();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            Centrala c = new Centrala();
            
            System.in.read();
            
            c.Zatvori();
        } catch (NamingException ex) {
            Logger.getLogger(Centrala.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(Centrala.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Centrala.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
