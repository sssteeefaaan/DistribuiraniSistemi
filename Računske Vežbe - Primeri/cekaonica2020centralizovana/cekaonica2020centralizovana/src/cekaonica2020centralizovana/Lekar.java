/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cekaonica2020centralizovana;

import java.io.IOException;
import java.util.Scanner;
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
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Velja
 */
public class Lekar {
    private QueueSender sender;
    private TopicSubscriber receiver;
    private QueueConnection qc;
    private TopicConnection tc;
    private QueueSession qs;
    private TopicSession ts;
    private Topic obavestiLekara;
    String NazivLekara;
    
    public Lekar() throws NamingException, JMSException
    {
        InitialContext ictx = new InitialContext();
        
        obavestiLekara = (Topic)ictx.lookup("tObevestiLekara");
        Queue stampa = (Queue) ictx.lookup("qStampaCentrala");
        QueueConnectionFactory qcf = (QueueConnectionFactory)ictx.lookup("qcfCekaonica2020");
        TopicConnectionFactory tcf = (TopicConnectionFactory)ictx.lookup("tcfCekaonica2020");
        
        ictx.close();
        
        qc = (QueueConnection) qcf.createQueueConnection();
        qs = (QueueSession) qc.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
        
        sender = (QueueSender) qs.createSender(stampa);
        
        tc = (TopicConnection) tcf.createTopicConnection();
        ts = (TopicSession) tc.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);
        
    }
    
    public void KreniSaRadom(String lekar) throws JMSException
    {
        NazivLekara = lekar;
        ObavestiSestru(lekar);
        
        receiver = (TopicSubscriber) ts.createSubscriber(obavestiLekara, "Lekar = '"+ lekar +"'", true);//, "Lekar = '"+ lekar +"'");
        
        receiver.setMessageListener(new LekarMessageListener(this));
        
        qc.start();
        tc.start();
    }
    
    public void ObavestiSestru(String Pacijent) throws JMSException
    {
        TextMessage msg = qs.createTextMessage();
        msg.setText(Pacijent);
        msg.setStringProperty("Lekar", NazivLekara);
        
        sender.send(msg);
        
        qs.commit();
    }

    private void Zatvori() throws JMSException {
        qc.close();
        tc.close();
    }
    
    public static void main(String[] args) throws NamingException, JMSException, IOException 
    {
        Scanner in = new Scanner(System.in);

        System.out.println("Unesi lekara:");
        String lekar = in.nextLine();//System.console().readLine();
            
        Lekar l = new Lekar();
        
        System.out.println("poceo da radi: "+lekar);
        l.KreniSaRadom(lekar);
        
        System.in.read();
        
        l.Zatvori();
    }
}
