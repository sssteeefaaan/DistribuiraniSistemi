/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cekaonica2020centralizovana;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Velja
 */
public class Roto {

    private Topic obavestiLekara;
    private final Topic obavestiRoto;
    private final TopicSession ts;
    
    public Roto() throws NamingException, JMSException
    {
        InitialContext ictx = new InitialContext();
        
        obavestiLekara = (Topic)ictx.lookup("tObevestiLekara");
        obavestiRoto = (Topic) ictx.lookup("tObavestiRoto");
        TopicConnectionFactory tcf = (TopicConnectionFactory)ictx.lookup("tcfCekaonica2020");
        
        ictx.close();
        
        
        TopicConnection tc = (TopicConnection) tcf.createTopicConnection();
        ts = (TopicSession) tc.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);
    }
    
    public void SlusajZa(String lekar) throws JMSException
    {
        
        TopicSubscriber subObvL = (TopicSubscriber) ts.createSubscriber(obavestiLekara, "Lekar = '"+ lekar +"'", true);
        subObvL.setMessageListener(new RotoMessageListenerNovi(lekar));
        
        TopicSubscriber subZavr = (TopicSubscriber) ts.createSubscriber(obavestiRoto, "Lekar = '"+ lekar +"'", true);
        subZavr.setMessageListener(new RotoMessageListenerZavrsio(lekar));
        
    }
}
