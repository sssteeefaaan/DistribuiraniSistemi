/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atomatizovanaproizvodnja;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.QueueConnection;

/**
 *
 * @author Velja
 */
public class NoviOdgovorMessageListener implements MessageListener {

    Klijent k;
    public NoviOdgovorMessageListener(Klijent aThis) {
        k = aThis;
    }

    @Override
    public void onMessage(Message msg) {
        k.ObradiNoviOdgovor(msg);
    }
    
}
