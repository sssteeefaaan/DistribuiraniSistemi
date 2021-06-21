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
 * @author stefa
 */
public class Receiver {
    
    public static void main(String[] args) throws NamingException, JMSException
    {
        Context ictx = new InitialContext();
        Queue queue = (Queue) ictx.lookup("queue");
        QueueConnectionFactory qcf = (QueueConnectionFactory) ictx.lookup("jms/__defaultConnectionFactory");
        ictx.close();
        
        QueueConnection qc = qcf.createQueueConnection();
        QueueSession qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        
        QueueReceiver receiver = qs.createReceiver(queue);
        TextMessage msg;
        
        qc.start();
        for(int i = 0; i < 10 ; i++)
        {
            msg = (TextMessage) receiver.receive();
            System.out.println("[" + Integer.toString(i) +"] Poruka primljena: " + msg.getText());
        }
        qc.close();
    }
    
}
