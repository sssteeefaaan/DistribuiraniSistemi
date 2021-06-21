/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cekaonica2020necentralizovana;

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
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Velja
 */
public class Lekar {
    private QueueSender sender;
    private QueueReceiver receiver;
    private QueueConnection qc;
    private QueueSession qs;
    private Queue obavestiLekara;
    
    public Lekar() throws NamingException, JMSException
    {
        InitialContext ictx = new InitialContext();
        
        obavestiLekara = (Queue)ictx.lookup("qObevestiLekara");
        Queue stampa = (Queue) ictx.lookup("qStampa");
        QueueConnectionFactory qcf = (QueueConnectionFactory)ictx.lookup("qcfCekaonica2020");
        
        ictx.close();
        
        qc = (QueueConnection) qcf.createQueueConnection();
        qs = (QueueSession) qc.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
        
        sender = (QueueSender) qs.createSender(stampa);
    }
    
    public void KreniSaRadom(String lekar) throws JMSException
    {
        ObavestiSestru(lekar);
        
        receiver = (QueueReceiver) qs.createReceiver(obavestiLekara);//, "Lekar = '"+ lekar +"'");
        qc.start();
        //while(true)
        {
            TextMessage msg = (TextMessage) receiver.receive();

            System.out.print(lekar + " :: Primo pacijenta :: " + msg.getText());

            ObavestiSestru(msg.getText());
        }
    }
    
    public void ObavestiSestru(String Pacijent) throws JMSException
    {
        TextMessage msg = qs.createTextMessage();
        msg.setText(Pacijent);
        
        sender.send(msg);
        
        qs.commit();
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
    }
}
