
import java.util.ArrayList;
import javax.jms.Message;
import javax.jms.MessageListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Stefan
 */
public class SortiranjeListener implements MessageListener {

    ArrayList<Integer> Left = null;
    ArrayList<Integer> Right = null;
    public SortiranjeListener(Klijent aThis, ArrayList<Integer> Left, ArrayList<Integer> Right) {
    }

    @Override
    public void onMessage(Message msg) {
    }
    
}
