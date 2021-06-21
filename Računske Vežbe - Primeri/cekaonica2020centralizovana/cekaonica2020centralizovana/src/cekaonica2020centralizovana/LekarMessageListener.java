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
public class LekarMessageListener implements MessageListener {

    Lekar lekar;
    public LekarMessageListener(Lekar aThis) {
        lekar = aThis;
    }

    @Override
    public void onMessage(Message msg) 
    {
            TextMessage txt = (TextMessage) msg;

        try {
            System.out.print(lekar.NazivLekara + " :: Primio pacijenta :: " + txt.getText());
            lekar.ObavestiSestru(txt.getText());
        } catch (JMSException ex) {
            Logger.getLogger(LekarMessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
