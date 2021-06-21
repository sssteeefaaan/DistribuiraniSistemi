
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.*;
import javax.naming.*;

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

    private int id;
    private Queue qSortiraj;
    private Queue qSortiran;
    private QueueConnection qc;
    private QueueSession qs;
    private QueueSender sender;
    private MessageProducer sortirajProducer;
    private MessageProducer sortiranProducer;

    public Klijent(int id) throws NamingException, JMSException {
        InitialContext context = new InitialContext();

        QueueConnectionFactory qcf = (QueueConnectionFactory) context.lookup("qcfOktobar2020");

        qSortiraj = (Queue) context.lookup("qSortirajOktobar2020");
        qSortiran = (Queue) context.lookup("qSortiranOktobar2020");

        context.close();

        qc = (QueueConnection) qcf.createConnection();
        qs = (QueueSession) qc.createSession(false, Session.AUTO_ACKNOWLEDGE); //1
    }

    public void start() throws JMSException {
        sortirajProducer = qs.createProducer(qSortiraj);
        sortiranProducer = qs.createProducer(qSortiran);

        MessageConsumer sortirajConsumer = qs.createConsumer(qSortiraj);
        sortirajConsumer.setMessageListener(ml
                -> {
            try {
                ObjectMessage incoming = (ObjectMessage) ml;
                ArrayList<Integer> niz = (ArrayList<Integer>) incoming.getObject();
                int ID = incoming.getIntProperty("IDTrazio");

                System.out.println("Primljen niz za soriranje od [" + Integer.toString(ID) + "]: ");
                niz.forEach(el -> System.out.print(Integer.toString(el) + " "));

                if (niz.size() > 1) {

                    ArrayList<Integer> Left = new ArrayList();
                    ArrayList<Integer> Right = new ArrayList();
                    int middle = niz.size() / 2;

                    for (int i = 0; i < niz.size(); i++) {
                        if (i < middle) {
                            Left.add(niz.get(i));
                        } else {
                            Right.add(niz.get(i));
                        }
                    }

                    ObjectMessage lmsg = qs.createObjectMessage(),
                            rmsg = qs.createObjectMessage();

                    lmsg.setIntProperty("IDTrazio", this.id);
                    rmsg.setIntProperty("IDTrazio", this.id);

                    lmsg.setObject(Left);
                    rmsg.setObject(Right);

                    sortirajProducer.send(lmsg);
                    sortirajProducer.send(rmsg);

                    Left = Right = null;
                    
                    MessageConsumer sortiranConsumer = qs.createConsumer(qSortiran, "IDTrazio = " + this.id);
                    sortiranConsumer.setMessageListener(new SortiranjeListener(this, Left, Right));

                } else {
                    ObjectMessage one = qs.createObjectMessage();

                    one.setIntProperty("IDPomogao", this.id);
                    one.setIntProperty("IDTrazio", ID);
                    one.setObject(niz);

                    sortiranProducer.send(one);
                }
            } catch (JMSException ex) {
                Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
