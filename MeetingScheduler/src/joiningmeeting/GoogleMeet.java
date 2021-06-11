package joiningmeeting;

import java.awt.Robot;
import java.awt.event.KeyEvent;

public class GoogleMeet {

    public void disconnectMedia() {
        try {
            Robot robot = new Robot();
            robot.delay(800);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.delay(20);
            robot.keyPress(KeyEvent.VK_D);
            robot.keyRelease(KeyEvent.VK_D);
            robot.delay(20);
            robot.keyPress(KeyEvent.VK_E);
            robot.keyRelease(KeyEvent.VK_E);
            robot.keyRelease(KeyEvent.VK_CONTROL);                                   
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
