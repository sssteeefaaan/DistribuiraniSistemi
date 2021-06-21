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
public class RedirectStampaMessageListener implements MessageListener {

    Centrala centrala;
    public RedirectStampaMessageListener(Centrala c) {
        centrala = c;
    }

    @Override
    public void onMessage(Message msg) {
        TextMessage txt = (TextMessage)msg;
        
        String lekar;
        try {
            lekar = txt.getStringProperty("Lekar");
            centrala.ObjaviStampu(lekar, txt.getText());
            centrala.AzurirajRoto(lekar, txt.getText());
        } catch (JMSException ex) {
            Logger.getLogger(RedirectStampaMessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
