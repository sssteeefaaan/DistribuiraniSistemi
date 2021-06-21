/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distribuirani_Sistemi;

import javax.jms.*;
import javax.naming.*;

public class Sender {
    
    public static void main(String[] args) throws NamingException, JMSException
    {
        Context ictx = new InitialContext();
        Queue queue = (Queue) ictx.lookup("queue");
        QueueConnectionFactory qcf = (QueueConnectionFactory) ictx.lookup("jms/__defaultConnectionFactory");
        ictx.close();
        
        QueueConnection qc = qcf.createQueueConnection();
        QueueSession qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        
        QueueSender qsend = qs.createSender(queue);
        
        TextMessage msg = qs.createTextMessage();
        for(int i = 0; i < 10; i++){
            msg.setText("Hello bitch [" + i + "]!");
            qsend.send(msg);
        }
        
        qc.close();
    }
    
}
