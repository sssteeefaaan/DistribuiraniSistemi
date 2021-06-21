package atomatizovanaproizvodnja;

import javax.jms.Message;
import javax.jms.MessageListener;

public class PomocMessageListener implements MessageListener {

    Klijent k;
    public PomocMessageListener(Klijent aThis) {
        k=aThis;
    }

    @Override
    public void onMessage(Message msg) {
        k.ObradiPozivZaPomoc(msg);
    }
    
}
