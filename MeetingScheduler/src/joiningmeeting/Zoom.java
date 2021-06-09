package joiningmeeting;

import java.awt.Robot;
import java.awt.event.KeyEvent;

public class Zoom {
    
    public void joinMeeting(){
 
        try{
            Robot r = new Robot();
            r.delay(7000);
            r.keyPress(KeyEvent.VK_LEFT);
            r.keyRelease(KeyEvent.VK_LEFT);
            r.delay(10);
            r.keyPress(10);
            r.keyRelease(10);
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }
    
}
