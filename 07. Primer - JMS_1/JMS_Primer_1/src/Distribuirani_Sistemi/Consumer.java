/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distribuirani_Sistemi;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.*;
import javax.jms.*;
/**
 *
 * @author Stefan
 */
public class Consumer {

    private final Connection cnx;
    private final Session sess;
    private static int num = 0;
    private int id;
    
    public Consumer() throws NamingException, JMSException
    {
        Context ictx = new InitialContext();
        Queue queue=null;
        if(num==0)
            queue = (Queue) ictx.lookup("queue");
        Topic topic = (Topic) ictx.lookup("topic");
        ConnectionFactory cf = (ConnectionFactory) ictx.lookup("jms/__defaultConnectionFactory");
        ictx.close();
        
        cnx = cf.createConnection();
        sess = cnx.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer recv = null;
        if(queue!=null)
          recv= sess.createConsumer(queue);
        MessageConsumer subs = sess.createConsumer(topic);
        
        id = num++;
        
        if(recv!=null)
            recv.setMessageListener(new MsgListener("Queue [" + Integer.toString(this.id) +"]"));
        subs.setMessageListener(new MsgListener("Topic [" + Integer.toString(this.id) +"]"));
        
        System.out.println(num);
        
        cnx.start();
    }
    public void Zatvori() throws JMSException
    {
        cnx.close();
    }
    
    public static void main(String[] args) throws IOException
    {
        try {
            Consumer c1 = new Consumer(), c2 = new Consumer();
            
            System.in.read();
            
            c1.Zatvori();
            c2.Zatvori();
        } catch (NamingException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
