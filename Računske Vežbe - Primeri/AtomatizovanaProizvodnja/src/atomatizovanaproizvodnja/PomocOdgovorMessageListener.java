/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atomatizovanaproizvodnja;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author Velja
 */
public class PomocOdgovorMessageListener implements MessageListener {

    Klijent k;
    public PomocOdgovorMessageListener(Klijent aThis) {
        k = aThis;
    }

    @Override
    public void onMessage(Message msg) {
        k.Pomogao(msg);
    }
    
}
