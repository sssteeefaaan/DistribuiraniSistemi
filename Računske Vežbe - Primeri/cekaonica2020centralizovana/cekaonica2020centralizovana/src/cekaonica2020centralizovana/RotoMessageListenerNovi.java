/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cekaonica2020centralizovana;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author Velja
 */
public class RotoMessageListenerNovi implements MessageListener {

    String lekar;
    public RotoMessageListenerNovi(String l) {
        lekar = l;
    }

    @Override
    public void onMessage(Message msg) {
        
        TextMessage txt = (TextMessage)msg;
        try {
            String pacijent = txt.getText();
        
            System.out.println("Novi pacijent: "+txt);
        } catch (JMSException ex) {
            Logger.getLogger(RotoMessageListenerNovi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
