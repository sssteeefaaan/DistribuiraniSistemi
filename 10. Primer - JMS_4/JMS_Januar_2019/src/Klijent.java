
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
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
 * @author Stefan
 */
public class Klijent {

    private Topic tLinija;
    private Topic tKvar;

    private TopicConnection tc;

    private TopicSession ts;

    private TopicPublisher linija;
    private TopicPublisher kvar;

    public Klijent() throws NamingException, JMSException {
        InitialContext context = new InitialContext();

        TopicConnectionFactory tcf = (TopicConnectionFactory) context.lookup("tcfJanuar2019");

        tLinija = (Topic) context.lookup("tLinijaJanuar2019");
        tKvar = (Topic) context.lookup("tKvarJanuar2019");

        context.close();

        tc = (TopicConnection) tcf.createConnection();

        ts = (TopicSession) tc.createSession(false, 1);//Session.AUTO_ACKNOWLEDGE
    }

    public void startBus() throws JMSException {
        linija = ts.createPublisher(tLinija);
        kvar = ts.createPublisher(tKvar);
        
        //tc.start();
    }

    public void Stigao(String bus, String linija, String stanica) throws JMSException {
        Message msg = ts.createMessage();
        msg.setStringProperty("Bus", bus);
        msg.setStringProperty("Linija", linija);
        msg.setStringProperty("Trenutno", stanica);

        this.linija.send(msg);
    }

    public void UKvaru(String bus, String linija, String najblizaStanica) throws JMSException {
        Message msg = ts.createMessage();
        msg.setStringProperty("Bus", bus);
        msg.setStringProperty("Linija", linija);
        msg.setStringProperty("Trenutno", najblizaStanica);

        kvar.send(msg);
    }

    public void startStanica(ArrayList<String> linije) throws JMSException {
        String parsed = "";
        for (String s : linije) {
            parsed += "'" + s + "', ";
        }
        parsed = parsed.substring(0, parsed.length() - 2);
        System.out.println(parsed);
        TopicSubscriber linijaSubscriber = ts.createSubscriber(tLinija, "Linija IN (" + parsed + ")", true);
        linijaSubscriber.setMessageListener(ml
                -> {
            try {
                String bus = ml.getStringProperty("Bus");
                String stanica = ml.getStringProperty("Trenutno");
                String linija = ml.getStringProperty("Linija");
                System.out.println("Autobus '" + bus + "' linije '" + linija + "' se trenutno nalazi na stanici '" + stanica + "'!");
            } catch (JMSException ex) {
                Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        TopicSubscriber kvarSubscriber = ts.createSubscriber(tKvar);
        kvarSubscriber.setMessageListener(ml
                -> {
            try {
                String bus = ml.getStringProperty("Bus");
                String stanica = ml.getStringProperty("Trenutno");
                String linija = ml.getStringProperty("Linija");

                System.out.println("Autobus '" + bus + "' linije '" + linija + "' se pokvario u blizini stanice '" + stanica + "'!");
            } catch (JMSException ex) {
                Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        tc.start();
    }

    public void odjava() throws JMSException {
        tc.close();
    }

    public static void main(String[] args) throws NamingException, JMSException {
        Klijent k = new Klijent();

        String option;
        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.println("Autobus/Stanica?");
            option = s.nextLine().trim().toUpperCase();

            if (option.equals("AUTOBUS")) {
                k.startBus();
                System.out.println("Autobus je povezan na sistem!");
                while(true)
                {
                    System.out.println("Stigao/Kvar/Exit?");
                    
                    option = s.nextLine().trim().toUpperCase();
                    
                    if(option.equals("EXIT"))
                        break;
                    
                    if(option.equals("KVAR"))
                        k.UKvaru(s.nextLine().trim(), s.nextLine().trim(), s.nextLine().trim());
                    
                    if(option.equals("STIGAO"))
                        k.Stigao(s.nextLine().trim(),s.nextLine().trim(), s.nextLine().trim());
                }
                break;
            }

            if (option.equals("STANICA")) {
                ArrayList<String> linije = new ArrayList();
                System.out.println("Unesite linije kojima pripada stanica/KRAJ za kraj:");
                while (!(option = s.nextLine().trim()).equals("KRAJ")) {
                    linije.add(option);
                }
                k.startStanica(linije);
                
                System.out.println("Stanica osluskuje autobuse na unetim linijama!");
                s.nextLine();
                break;
            }

            System.out.println("GRESKA!");
        }
        
        k.odjava();
    }
}
