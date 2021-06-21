package Distribuirani_Sistemi;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.*;
import javax.jms.*;

public class Lekar {

    private final String ime;
    private Pacijent pacijent;
    private final QueueSender centrala;
    private final QueueConnection qc;
    private final QueueSession qs;
    private final TopicConnection tc;
    private final TopicSession ts;
    
    public Lekar(String ime) throws NamingException, JMSException
    {
        System.out.println("Lekar [" + ime + "] se kreira");
        
        this.ime = ime;
        this.pacijent = null;
        
        InitialContext context = new InitialContext();
        
        Queue qCentrala = (Queue) context.lookup("qLekar");
        Topic tCentrala = (Topic) context.lookup("tLekar");
        
        QueueConnectionFactory qcf = (QueueConnectionFactory) context.lookup("QCF_Cekaonica");
        TopicConnectionFactory tcf = (TopicConnectionFactory) context.lookup("TCF_Cekaonica");
        
        context.close();
        
        qc = qcf.createQueueConnection();
        qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        
        tc = tcf.createTopicConnection();
        ts = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        
        qc.start();
        tc.start();
        
        centrala = qs.createSender(qCentrala);
        TopicSubscriber centralaInfo = ts.createSubscriber(tCentrala, "Lekar = '" + ime + "'", true);
        centralaInfo.setMessageListener(new LekarML());
        
        System.out.println("Lekar [" + ime + "] inicijalizovan");
        
    }
    public void Prikazi(Message msg) throws JMSException
    {
        TextMessage txt = (TextMessage) msg;
        this.pacijent = new Pacijent(txt.getText(), txt.getStringProperty("Sestra"));
        System.out.println("Primljen pacijent: " + this.pacijent.ime);
        System.out.println("Od sestre: " + this.pacijent.sestra);
        
        Pregledaj();
    }
    
    public void Pregledaj() throws JMSException
    {
        TextMessage msg = qs.createTextMessage();
        msg.setText(this.pacijent.ime);
        msg.setStringProperty("Sestra", this.pacijent.sestra);
        msg.setStringProperty("Lekar", ime);
        centrala.send(msg);
        
        System.out.println("Pregledan pacijent: " + this.pacijent.ime);
        this.pacijent = null;
    }
    
    public void Zatvori() throws JMSException
    {
        qc.close();
        tc.close();
    }

    private class Pacijent {
        public String ime;
        public String sestra;
        
        public Pacijent(String ime, String sestra) {
            this.ime = ime;
            this.sestra = sestra;
        }
    }

    private class LekarML implements MessageListener {

        public LekarML() {
        }

        @Override
        public void onMessage(Message msg) {
            try {
                Prikazi(msg);
            } catch (JMSException ex) {
                Logger.getLogger(Lekar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args)
    {
       String temp;
       
       try{
           System.out.print("Unesite svoje ime: ");
           Scanner s = new Scanner(System.in);
           temp = s.nextLine();
           
           Lekar l = new Lekar(temp);
           s.nextLine();
           
           l.Zatvori();
       }
       catch(Exception e)
       {
           System.out.println(e.getMessage());
       }
    }
}
