package atomatizovanaproizvodnja;

import javax.jms.Message;
import javax.jms.MessageListener;

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
