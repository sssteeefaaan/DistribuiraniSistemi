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
public class Sestra {
    private QueueSender sender;
    private QueueConnection qc;
    private QueueSession qs;
    
    public Sestra() throws NamingException, JMSException
    {
        
        System.out.println("Pribavljanje contexta");
        InitialContext ictx = new InitialContext();
        
        Queue obavestiLekara = (Queue)ictx.lookup("qObevestiLekara");
        Queue stampa = (Queue) ictx.lookup("qStampa");
        QueueConnectionFactory qcf = (QueueConnectionFactory)ictx.lookup("qcfCekaonica2020");
        
        ictx.close();
        
        System.out.println("Pribavljanje connection");
        qc = (QueueConnection) qcf.createQueueConnection();
        qs = (QueueSession) qc.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
        
        sender = (QueueSender) qs.createSender(obavestiLekara);
        
        QueueReceiver receiver = (QueueReceiver) qs.createReceiver(stampa);
        receiver.setMessageListener(new StampaMessageListener());
        qc.start();
        System.out.println(">>>Sestra<<<");
    }
    
    public void ObavestiLekaraDaJePacijentStigao(String Lekar, String Pacijent) throws JMSException
    {
        TextMessage msg = qs.createTextMessage(Pacijent);
        //msg.setText(Pacijent);
        msg.setStringProperty("Lekar", Lekar);
        
        sender.send(msg);
        
        qs.commit();
    }
    
    public static void main(String[] args) throws NamingException, JMSException, IOException 
    {
        // TODO code application logic here
        Sestra s = new Sestra();
        
        Scanner in = new Scanner(System.in);
        
        System.out.println("Unesi pacijenta:");
            String pacijent = in.nextLine();//System.console().readLine();

        System.out.println("Unesi lekara:");
            String lekar = in.nextLine();//System.console().readLine();
            
        s.ObavestiLekaraDaJePacijentStigao(lekar, pacijent);
        
        System.in.read();
    }
}
