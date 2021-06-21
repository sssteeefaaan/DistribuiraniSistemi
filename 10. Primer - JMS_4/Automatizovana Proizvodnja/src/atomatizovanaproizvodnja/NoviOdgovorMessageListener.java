package atomatizovanaproizvodnja;

import javax.jms.*;

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
