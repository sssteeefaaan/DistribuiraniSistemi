/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distribuirani_Sistemi;

import java.io.IOException;
import javax.naming.*;
import javax.jms.*;

public class Subscriber {
    
    public static void main(String[] args) throws NamingException, JMSException, IOException
    {
        Context ictx = new InitialContext();
        Topic topic = (Topic) ictx.lookup("topic");
        TopicConnectionFactory tcf = (TopicConnectionFactory) ictx.lookup("jms/__defaultConnectionFactory");
        ictx.close();
        
        TopicConnection tc = tcf.createTopicConnection();
        TopicSession ts = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        
        TopicSubscriber subscriber = ts.createSubscriber(topic);
        
        tc.start();
        subscriber.setMessageListener(new MessageListenerSub());
        System.in.read();
        tc.close();
    }
}
