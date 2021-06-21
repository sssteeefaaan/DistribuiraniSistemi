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

public class Subscriber {

    static Context ictx = null;

    public static void main(String[] args) throws Exception {
        ictx = new InitialContext();
        Topic topic = (Topic) ictx.lookup("topic");
        TopicConnectionFactory tcf = (TopicConnectionFactory) ictx.lookup("tcf");
        ictx.close();
        TopicConnection tc = tcf.createTopicConnection();
        TopicSession ts = tc.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);
        TopicSubscriber tsub = ts.createSubscriber(topic);
        tsub.setMessageListener(new MsgListener("2020 04 12"));
        tc.start();
        System.in.read();
        tc.close();
    }
}


