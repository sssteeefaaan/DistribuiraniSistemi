package atomatizovanaproizvodnja;

import javax.jms.Message;
import javax.jms.MessageListener;


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
