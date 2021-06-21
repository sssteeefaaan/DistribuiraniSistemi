package Distribuirani_Sistemi;

import javax.naming.*;
import javax.jms.*;

public class Publisher {
    public static void main(String[] args) throws NamingException, JMSException
    {
        Context ictx = new InitialContext();
        Topic topic = (Topic) ictx.lookup("topic");
        TopicConnectionFactory tcf = (TopicConnectionFactory) ictx.lookup("jms/__defaultConnectionFactory");
        ictx.close();
        
        TopicConnection tc = tcf.createTopicConnection();
        TopicSession ts = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        
        TopicPublisher tp = ts.createPublisher(topic);
        
        TextMessage msg = ts.createTextMessage();
        for(int i = 0; i < 10; i++)
        {
            msg.setText("[Published] Hello " + i);
            tp.publish(msg);
            
            msg.setText("[Sent] Hello " + i);
            tp.send(msg);
        }
        tc.close();
    }
}
