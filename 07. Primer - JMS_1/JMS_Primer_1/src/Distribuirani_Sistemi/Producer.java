/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distribuirani_Sistemi;

import javax.naming.*;
import javax.jms.*;

/**
 *
 * @author Stefan
 */
public class Producer {

    private final Connection cnx;
    private final Session sess;
    private final MessageProducer qProducer;
    private final MessageProducer tProducer;
    
    public Producer() throws NamingException, JMSException
    {
        Context ictx = new InitialContext();
        ConnectionFactory cf = (ConnectionFactory) ictx.lookup("jms/__defaultConnectionFactory");
        Queue queue = (Queue) ictx.lookup("queue");
        Topic topic = (Topic) ictx.lookup("topic");
        ictx.close();
        
        cnx = cf.createConnection();
        sess = cnx.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        qProducer = sess.createProducer(queue);
        tProducer = sess.createProducer(topic);
    }
    public void Proizvedi(int n) throws JMSException
    {
        TextMessage msg = sess.createTextMessage();
        
        for(int i = 0; i < n;i++)
        {
            msg.setText("Porukica " + i);
            qProducer.send(msg);
        }
    }
    public void Proizvedi(String s) throws JMSException
    {
        TextMessage msg = sess.createTextMessage();
        
        msg.setText(s);
        tProducer.send(msg);
    }
    public void Zatvori() throws JMSException
    {
        cnx.close();
    }
    
    public static void main(String[] args) throws NamingException, JMSException
    {
        Producer p = new Producer();
        p.Proizvedi(10);
        p.Proizvedi("Hello bitch!");
        p.Zatvori();
    }
}
