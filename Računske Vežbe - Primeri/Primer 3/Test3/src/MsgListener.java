
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Velja
 */
public class MsgListener implements MessageListener {

    String id;

    public MsgListener() {
        id = "";
    }

    public MsgListener(String id) {
        this.id = id;
    }

    public void onMessage(Message msg) {
        TextMessage tmsg = (TextMessage) msg;
        try {
            System.out.println(id + ": " + tmsg.getText());
        } catch (JMSException jE) {
            jE.printStackTrace();
        }
    }
}
