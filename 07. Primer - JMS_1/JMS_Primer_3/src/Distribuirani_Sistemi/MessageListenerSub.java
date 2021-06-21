/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distribuirani_Sistemi;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.*;

public class MessageListenerSub implements MessageListener {

    public MessageListenerSub() {
    }

    @Override
    public void onMessage(Message msg) {
        try {
            System.out.println(((TextMessage) msg).getText());
        } catch (JMSException ex) {
            Logger.getLogger(MessageListenerSub.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
