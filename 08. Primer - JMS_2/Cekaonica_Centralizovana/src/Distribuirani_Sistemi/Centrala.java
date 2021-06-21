package Distribuirani_Sistemi;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.*;
import javax.naming.*;

public class Centrala {

    private final MessageProducer pZaSestre;
    private final MessageProducer pZaLekare;
    private final QueueConnection qc;
    private final TopicConnection tc;
    private final QueueSession qs;
    private final TopicSession ts;
    
    public Centrala() throws NamingException, JMSException
    {
        System.out.println("Centrala se pokrece");
        
        InitialContext context = new InitialContext();
        
        Topic tSestra = (Topic) context.lookup("tSestra");
        Queue qSestra = (Queue) context.lookup("qSestra");
        
        Topic tLekar = (Topic) context.lookup("tLekar");
        Queue qLekar = (Queue) context.lookup("qLekar");
        
        QueueConnectionFactory qcf = (QueueConnectionFactory) context.lookup("QCF_Cekaonica");
        TopicConnectionFactory tcf = (TopicConnectionFactory) context.lookup("TCF_Cekaonica");
        
        context.close();
        
        qc = qcf.createQueueConnection();
        tc = tcf.createTopicConnection();
        
        qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        ts = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        
        qc.start();
        tc.start();
        
        pZaSestre = ts.createProducer(tSestra);
        pZaLekare = ts.createProducer(tLekar);
        
        QueueReceiver rZaSestre = qs.createReceiver(qSestra);
        QueueReceiver rZaLekare = qs.createReceiver(qLekar);
        
        rZaSestre.setMessageListener(new MLCentralaSestre());
        rZaLekare.setMessageListener(new MLCentralaLekari());
        
        System.out.println("Centrala je inicijalizovana");
    }
    
    public void ProslediLekarima(Message msg) throws JMSException
    {
        TextMessage txt = (TextMessage) msg;
        System.out.println("Primljena poruka od Sestre: " + msg.getStringProperty("Sestra"));
        System.out.println("Poruka namenjena Lekaru: " + msg.getStringProperty("Lekar"));
        
        TextMessage forSend = ts.createTextMessage();
        forSend.setText(txt.getText());
        forSend.setStringProperty("Lekar", msg.getStringProperty("Lekar"));
        forSend.setStringProperty("Sestra", msg.getStringProperty("Sestra"));
        
        pZaLekare.send(forSend);
    }
    
    public void ProslediSestrama(Message msg) throws JMSException
    {
        TextMessage txt = (TextMessage) msg;
        System.out.println("Primljena poruka od Lekara: " + txt.getStringProperty("Lekar"));
        System.out.println("Poruka namenjena Sestri: " + txt.getStringProperty("Sestra"));
        
        TextMessage forSend = ts.createTextMessage();
        forSend.setText(txt.getText());
        forSend.setStringProperty("Lekar", txt.getStringProperty("Lekar"));
        forSend.setStringProperty("Sestra", txt.getStringProperty("Sestra"));
        
        pZaSestre.send(forSend);
    }
    
    public void Zatvori() throws JMSException
    {
        qc.close();
        tc.close();
    }

    private class MLCentralaSestre implements MessageListener {

        public MLCentralaSestre() {
        }

        @Override
        public void onMessage(Message msg) {
            try {
                ProslediLekarima(msg);
            } catch (JMSException ex) {
                Logger.getLogger(Centrala.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class MLCentralaLekari implements MessageListener {

        public MLCentralaLekari() {
        }

        @Override
        public void onMessage(Message msg) {
            try {
                ProslediSestrama(msg);
            } catch (JMSException ex) {
                Logger.getLogger(Centrala.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args) throws NamingException, JMSException, IOException
    {
        Centrala c = new Centrala();
        System.in.read();
        c.Zatvori();
    }
}
