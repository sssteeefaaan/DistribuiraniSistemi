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

public class Receiver {

    static Context ictx = null;

    public static void main(String[] args) throws Exception {
        ictx = new InitialContext();
        Queue queue = (Queue) ictx.lookup("queue");
        QueueConnectionFactory qcf = (QueueConnectionFactory) ictx.lookup("jms/__defaultConnectionFactory");
        ictx.close();
        QueueConnection qc = qcf.createQueueConnection();
        QueueSession qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        QueueReceiver qrec = qs.createReceiver(queue);
        TextMessage msg;
        qc.start();
        int i;
        for (i = 0; i < 10; i++) {
            msg = (TextMessage) qrec.receive();
            System.out.println("Msg received: " + msg.getText());
        }
        qc.close();
    }
}
