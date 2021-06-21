
import java.util.HashMap;
import java.util.Scanner;
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
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
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
public class Kupac {

    private final Queue qPrijava;
    private final Queue qOdgovor;
    private final Queue qKupovina;
    private final TopicConnection tc;
    private final TopicSession ts;
    private final QueueConnection qc;
    private final QueueSession qs;
    private final int id;
    private QueueSender login;
    private QueueSender kupi;
    private final Queue qKupovinaOdg;
    private final Topic tIzmenaProizvod;
    
    public Kupac(int id) throws NamingException, JMSException
    {
        this.id = id;
        
        InitialContext ctx = new InitialContext();
        TopicConnectionFactory tcf = (TopicConnectionFactory) ctx.lookup("tcf");
        TopicConnectionFactory qcf = (TopicConnectionFactory) ctx.lookup("qcf");
       
        qPrijava = (Queue) ctx.lookup("qPrijava");
        qOdgovor = (Queue) ctx.lookup("qOdgovor");
        
        tIzmenaProizvod = (Topic) ctx.lookup("tIzmena");
        
        qKupovina = (Queue) ctx.lookup("qKupovina");
        qKupovinaOdg = (Queue) ctx.lookup("qKupiNabavi");
        
        ctx.close();
        
        tc = (TopicConnection) tcf.createConnection();
        ts = (TopicSession) tc.createSession(false, Session.AUTO_ACKNOWLEDGE);
        qc = (QueueConnection) qcf.createConnection();
        qs = (QueueSession) qc.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }
    
    public void Start() throws JMSException
    {
        login = qs.createSender(qPrijava);
        QueueReceiver answer = qs.createReceiver(qOdgovor, "ID = " + id);
        answer.setMessageListener((Message msg) ->
        {
            try
            {
                HashMap<String, Proizvod> proizvodi = (HashMap<String, Proizvod>)((ObjectMessage) msg).getObject();
                proizvodi.forEach((s, p) ->
                {
                    System.out.println("Proizvod:");
                    System.out.println("\tNaziv: " + p.getNaziv());
                    System.out.println("\tSifra: " + s);
                    System.out.println("\tKolicina: " + p.getKolicina());
                });
            }
            catch (JMSException ex) {
                Logger.getLogger(Kupac.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        kupi = qs.createSender(qKupovina);
        QueueReceiver kupiOdg = qs.createReceiver(qKupovinaOdg,"ID = " + id);
        kupiOdg.setMessageListener((Message msg) ->
        {
            try
            {
                String status = msg.getStringProperty("Status");
                Proizvod p = (Proizvod) ((ObjectMessage)msg).getObject();
                if(status.equals("Kupljeno"))
                    System.out.println("Kupovina proizvoda: '" + p.getNaziv() + " uspesna!");
                else
                {
                    System.out.println("Proizvod: " + p.getNaziv() + " nije na stanju!");
                    prijaviSeZaStatus(p.getSifra());
                }
            }
            catch (JMSException ex) {
                Logger.getLogger(Kupac.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        qc.start();
        tc.start();
    }

    private void prijaviSeZaStatus(String sifra) throws JMSException {
        TopicSubscriber dostupan = ts.createSubscriber(tIzmenaProizvod, "Sifra = '" + sifra + "' AND Klijent = 'Kupac'", true);
        dostupan.setMessageListener((Message newMsg) ->
        {
            try{
                Proizvod p = (Proizvod) ((ObjectMessage)newMsg).getObject();
                System.out.println("Proizvod nabavljen:");
                System.out.println("\tNaziv: " + p.getNaziv());
                System.out.println("\tSifra: " + p.getSifra());
                System.out.println("\tKolicina: " + p.getKolicina());
            }
            catch (JMSException ex) {
                Logger.getLogger(Kupac.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public void Login() throws JMSException
    {
        Message msg = qs.createMessage();
        msg.setIntProperty("ID", id);
        login.send(msg);
    }
    
    public void Kupovina(String sifra) throws JMSException
    {
        Message msg = qs.createMessage();
        msg.setIntProperty("ID", id);
        msg.setStringProperty("Sifra", sifra);
        kupi.send(msg);
    }
    
    public static void main(String[] args) throws NamingException, JMSException
    {
        Scanner s = new Scanner(System.in);
        System.out.print("Unesite svoj id: ");
        Kupac k = new Kupac (s.nextInt());
        
        k.Start();
        k.Login();
        
        System.out.println("Kupac uspesno logovan na sistem");
        System.out.println("Unesite sifru proizvoda koji zelite kupiti: ");
        
        String input = "";
        
        while(!input.equals("KRAJ"))
        {
            input = s.nextLine();
            k.Kupovina(input);
        }
    }
}
