package joiningmeeting;

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import resourceloader.ResourceLoader;
import screenscanner.ScreenScanner;


public class GoogleMeet {
    
    private static final ScreenScanner ssc=new ScreenScanner();
    private static final ResourceLoader rsc=new ResourceLoader();
    
    
    public void joinMeeting() {
        try {
            Robot robot = new Robot();
            robot.delay(7000);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.delay(20);
            robot.keyPress(KeyEvent.VK_D);
            robot.keyRelease(KeyEvent.VK_D);
            robot.delay(20);
            robot.keyPress(KeyEvent.VK_E);
            robot.keyRelease(KeyEvent.VK_E);
            robot.keyRelease(KeyEvent.VK_CONTROL);
           
            BufferedImage bi = ImageIO.read(rsc.load("res/join_now.png"));
            robot.mouseMove(0, 0);
            robot.delay(100);
            Point point = ssc.pointOnScreen(bi, 500);
            
            if (point != null) {
                robot.mouseMove(point.x, point.y);
                robot.delay(15);
                robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
            } else {
                System.out.println("Could not locate join button!");
            }
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
