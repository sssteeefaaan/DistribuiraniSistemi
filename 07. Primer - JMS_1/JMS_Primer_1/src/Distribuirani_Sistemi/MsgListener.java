/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distribuirani_Sistemi;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author Stefan
 */
public class MsgListener implements MessageListener {

        private final String ID;

        public MsgListener(String id) {
            this.ID = id;
        }
        
        @Override
        public void onMessage(Message msg){
            TextMessage tmsg = (TextMessage)msg;
            try {
                System.out.println(this.ID + ": " + tmsg.getText());
            } catch (JMSException ex) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
