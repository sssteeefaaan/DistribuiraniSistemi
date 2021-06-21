
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author stefa
 */
public class Centrala {

    private final TopicConnectionFactory tcf;
    private final TopicConnectionFactory qcf;
    private final Queue qPrijava;
    private final Queue qOdgovor;
    private final Topic tIzmena;
    private final Queue qKupovina;
    private final Queue qKupiNabavi;
    private final Queue qNabavka;
    private final TopicConnection tc;
    private final QueueConnection qc;
    private final TopicSession ts;
    private final QueueSession qs;
    private HashMap<String, Proizvod> proizvodi;
    
    public Centrala() throws NamingException, JMSException
    {
        InitialContext ctx = new InitialContext();
        tcf = (TopicConnectionFactory) ctx.lookup("tcf");
        qcf = (TopicConnectionFactory) ctx.lookup("qcf");
       
        qPrijava = (Queue) ctx.lookup("qPrijava");
        qOdgovor = (Queue) ctx.lookup("qOdgovor");
        
        tIzmena = (Topic) ctx.lookup("tIzmena");
        
        qKupovina = (Queue) ctx.lookup("qKupovina");
        qKupiNabavi = (Queue) ctx.lookup("qKupiNabavi");
        qNabavka = (Queue) ctx.lookup("qNabavka");
        
        ctx.close();
        
        tc = (TopicConnection) tcf.createConnection();
        ts = (TopicSession) tc.createSession(false, Session.AUTO_ACKNOWLEDGE);
        qc = (QueueConnection) qcf.createConnection();
        qs = (QueueSession) qc.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        proizvodi = new HashMap<String, Proizvod>();
    }
    
    public void Start(HashMap<String, Proizvod> proz) throws JMSException
    {
        for(Entry<String, Proizvod> entry : proz.entrySet())
        {
            this.proizvodi.put(entry.getKey(), entry.getValue());
        }
        
        QueueReceiver prijavaReceiver = qs.createReceiver(qPrijava);
        QueueSender prijavaSender = qs.createSender(qOdgovor);
        prijavaReceiver.setMessageListener((Message msg) ->
        {
            try {
                final int id = msg.getIntProperty("ID");
                System.out.println("Korisnik [" + id + "] se prijavio na sistem");
                
                ObjectMessage newObjMessage = qs.createObjectMessage();
                newObjMessage.setObject(this.proizvodi);
                newObjMessage.setIntProperty("ID", id);
                
                prijavaSender.send(newObjMessage);
                
            } catch (JMSException ex) {
                Logger.getLogger(Centrala.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        TopicPublisher tIzmenaProizvod = ts.createPublisher(tIzmena);
        
        QueueReceiver kupovinaReceiver = qs.createReceiver(qKupovina);
        QueueSender kupovinaSender = qs.createSender(qKupiNabavi);
        kupovinaReceiver.setMessageListener((Message msg) ->
        {
            String sifra = "";
            try {
                sifra = msg.getStringProperty("Sifra");
            } catch (JMSException ex) {
                Logger.getLogger(Centrala.class.getName()).log(Level.SEVERE, null, ex);
            }
            Proizvod p = null;
            if((p = proizvodi.get(sifra)) != null)
            {
                int kol = p.getKolicina() - 1;
                try {
                    ObjectMessage newMessage = qs.createObjectMessage();
                    newMessage.setIntProperty("ID", msg.getIntProperty("ID"));
                    
                    if(kol >= 0)
                    {
                        p.setKolicina(kol);
                        newMessage.setStringProperty("Status", "Kupljeno");
                        
                        ObjectMessage proizvodInfo = ts.createObjectMessage();
                        proizvodInfo.setObject(p);
                        proizvodInfo.setStringProperty("Klijent", "Menadzer");
                        proizvodInfo.setStringProperty("Sifra", p.getSifra());
                        proizvodInfo.setIntProperty("Kolicina", p.getKolicina());
                        
                        tIzmenaProizvod.send(proizvodInfo);
                    }
                    else
                        newMessage.setStringProperty("Status", "Naruceno");
                    
                    newMessage.setObject(p);
                    
                    kupovinaSender.send(newMessage);
                } catch (JMSException ex) {
                        Logger.getLogger(Centrala.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        QueueReceiver nabavka = qs.createReceiver(qNabavka);
        nabavka.setMessageListener((Message msg) ->
        {
            try {
                String sifra = msg.getStringProperty("Sifra");
                int kolicina = msg.getIntProperty("Kolicina");
                
                Proizvod p = null;
                if((p = proizvodi.get(sifra)) == null)
                {
                    p = new Proizvod(sifra, msg.getStringProperty("Naziv"), 0);
                    proizvodi.put(sifra, p);
                }
                
                if(kolicina > 0)
                {
                    p.setKolicina(p.getKolicina() + kolicina);
                    ObjectMessage newMessage = ts.createObjectMessage();
                    newMessage.setObject(p);
                    newMessage.setStringProperty("Klijent", "Kupac");
                    newMessage.setStringProperty("Sifra", p.getSifra());
                    
                    tIzmenaProizvod.send(newMessage);
                }
            }
            catch (JMSException ex) {
                    Logger.getLogger(Centrala.class.getName()).log(Level.SEVERE, null, ex);
                }
        });
        
        qc.start();
        tc.start();
    }
}
