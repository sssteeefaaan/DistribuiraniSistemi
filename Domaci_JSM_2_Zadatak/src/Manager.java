
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
public class Manager {

    private final Queue qPrijava;
    private final Queue qOdgovor;
    private final Topic tIzmena;
    private final Queue qNabavka;
    private final TopicConnection tc;
    private final TopicSession ts;
    private final QueueConnection qc;
    private final QueueSession qs;
    private final int id;
    private QueueSender login;
    private QueueSender dobavi;
    
    public Manager(int id) throws NamingException, JMSException
    {
        this.id = id;
        
        InitialContext ctx = new InitialContext();
        TopicConnectionFactory tcf = (TopicConnectionFactory) ctx.lookup("tcf");
        TopicConnectionFactory qcf = (TopicConnectionFactory) ctx.lookup("qcf");
       
        qPrijava = (Queue) ctx.lookup("qPrijava");
        qOdgovor = (Queue) ctx.lookup("qOdgovor");
        tIzmena = (Topic) ctx.lookup("tIzmena");
        qNabavka = (Queue) ctx.lookup("qNabavka");
        
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
        
        dobavi = qs.createSender(qNabavka);
        
        qc.start();
        tc.start();
    }
    
    public void Login() throws JMSException
    {
        Message msg = qs.createMessage();
        msg.setIntProperty("ID", id);
        login.send(msg);
    }

    private void DodajProizvod(Proizvod p) throws JMSException {
        ObjectMessage msg = qs.createObjectMessage();
        msg.setObject(p);
        dobavi.send(msg);
    }
    
    public void PodesiAlarm(String sifra, int kolicina) throws JMSException
    {
        ts.createSubscriber(tIzmena, "Klijent = Menadzer AND Sifra = '" + sifra + "' AND Kolicina < " + Integer.toString(kolicina), true).setMessageListener((Message msg) ->
        {
            try {
                Proizvod p = (Proizvod) ((ObjectMessage)msg).getObject();
                System.out.println("Proizvod '" + p.getNaziv() + "' sa sifrom '" + p.getSifra() + "' je nestao!");
                System.out.println("Da li zelite nabaviti ovaj proizvod? DA/ne");
                Scanner s = new Scanner(System.in);
                if(s.nextLine().toLowerCase().equals("da"))
                {
                    System.out.print("Kolicina: ");
                    DodajProizvod(new Proizvod(p.getNaziv(), sifra, s.nextInt()));
                }
            } catch (JMSException ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public static void main(String[] args) throws NamingException, JMSException
    {
        Scanner s = new Scanner(System.in);
        System.out.print("Unesite svoj id: ");
        Manager m = new Manager(s.nextInt());
        
        m.Start();
        m.Login();
        
        System.out.println("Menadzer uspesno logovan na sistem");
        s.nextLine();
        System.out.println("Unesite sifru proizvoda za koji zelite da podesite alarm: ");
        s.nextLine();
        String sifra = s.nextLine();
        System.out.println("Unesite kolicinu proizvoda za alarm: ");
        int kolicina = s.nextInt();
        m.PodesiAlarm(sifra, kolicina);
        
        while(true);
    }
}
