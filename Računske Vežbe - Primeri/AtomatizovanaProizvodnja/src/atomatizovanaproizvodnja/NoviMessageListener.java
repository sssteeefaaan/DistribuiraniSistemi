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
public class NoviMessageListener implements MessageListener {

    Klijent k;
    public NoviMessageListener(Klijent aThis) {
        k = aThis;
    }

    @Override
    public void onMessage(Message msg) {
        k.ObradiNovog(msg);
    }
    
}
