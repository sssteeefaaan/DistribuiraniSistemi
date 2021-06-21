package Distribuirani_Sistemi;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.*;
import javax.naming.*;

public class Sestra {

    private final String ime;
    private final QueueConnection qc;
    private final QueueSession qs;
    private final QueueSender centrala;
    private final TopicConnection tc;
    private final TopicSession ts;
    
    public Sestra(String ime) throws NamingException, JMSException
    {
        System.out.println("Sestra [" + ime + "] se kreira");
        
        this.ime = ime;
        InitialContext context = new InitialContext();
        
        Topic tCentrala = (Topic) context.lookup("tSestra");
        Queue qCentrala = (Queue) context.lookup("qSestra");
        
        QueueConnectionFactory qcf = (QueueConnectionFactory) context.lookup("QCF_Cekaonica");
        TopicConnectionFactory tcf = (TopicConnectionFactory) context.lookup("TCF_Cekaonica");
        
        context.close();
        
        qc = qcf.createQueueConnection();
        qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        
        tc = tcf.createTopicConnection();
        ts = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        
        qc.start();
        tc.start();
        
        centrala = qs.createSender(qCentrala);
        TopicSubscriber centralaPrijem = ts.createSubscriber(tCentrala, "Sestra = '" + ime + "'", true);
        centralaPrijem.setMessageListener(new MLSestra());
        
        System.out.println("Sestra [" + ime + "] kreirana");
    }
    
    public void UputiPacijenta(String lekar, String pacijent) throws JMSException
    {
        TextMessage msg = qs.createTextMessage();
        msg.setText(pacijent);
        msg.setStringProperty("Lekar", lekar);
        msg.setStringProperty("Sestra", this.ime);
        
        centrala.send(msg);
    }        
    
    public void Prikazi(Message msg) throws JMSException
    {
        TextMessage txtMsg = (TextMessage) msg;
        System.out.println("Pacijent: " + txtMsg.getText());
        System.out.println("Pregledan od strane lekara: " + txtMsg.getStringProperty("Lekar"));
    }
    
    public void Zatvori() throws JMSException
    {
        qc.close();
        ts.close();
    }

    private class MLSestra implements MessageListener {

        public MLSestra() {
        }

        @Override
        public void onMessage(Message msg){
            try {
                Prikazi(msg);
            } catch (JMSException ex) {
                Logger.getLogger(Sestra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args)
    {
        String temp1, temp2;
        
        try{
            System.out.print("Unesite svoje ime: ");
            Scanner s = new Scanner(System.in);
            temp1 = s.nextLine();
            
            Sestra sestra = new Sestra(temp1);
            while(true){
                
                temp1 = s.nextLine();
                
                if(temp1.equals("quit"))
                    break;
                
                temp2 = s.nextLine();
                
                sestra.UputiPacijenta(temp2.trim(), temp1.trim());
            }
            
            sestra.Zatvori();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
