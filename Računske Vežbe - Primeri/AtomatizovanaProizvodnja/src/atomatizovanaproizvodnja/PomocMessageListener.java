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
public class PomocMessageListener implements MessageListener {

    String posao;
    Klijent k;
    public PomocMessageListener(String posao, Klijent aThis) {
        this.posao = posao;
        k=aThis;
    }

    @Override
    public void onMessage(Message msg) {
        k.ObradiPozivZaPomoc(posao, msg);
    }
    
}
