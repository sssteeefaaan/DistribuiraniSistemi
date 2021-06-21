/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Velja
 */
import javax.jms.*; 
import javax.naming.*; 
public class Publisher 
{
 static Context ictx = null;
 public static void main(String[] args) throws Exception {
  ictx = new InitialContext();
  Topic topic = (Topic) ictx.lookup("topic");
  TopicConnectionFactory tcf = (TopicConnectionFactory) ictx.lookup("tcf");
  ictx.close();
     try (TopicConnection tc = tcf.createTopicConnection()) 
     {
         TopicSession ts = tc.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);
         TopicPublisher tpub = ts.createPublisher(topic);
         TextMessage msg = ts.createTextMessage();
         int i;
         for (i = 0; i < 10; i++) {
             msg.setText("Test number " + i);
             tpub.publish(msg);  }
         ts.commit();
     }
 }
}

