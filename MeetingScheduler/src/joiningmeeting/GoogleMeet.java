package joiningmeeting;

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import resourceloader.ResourceLoader;
import screenscanner.ScreenScanner;


public class GoogleMeet {
    
    private final ScreenScanner ssc=new ScreenScanner();
    private final ResourceLoader rsc=new ResourceLoader();

    public void joinMeeting() {
        try {
            Robot robot = new Robot();
            robot.delay(6500);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.delay(20);
            robot.keyPress(KeyEvent.VK_D);
            robot.keyRelease(KeyEvent.VK_D);
            robot.delay(20);
            robot.keyPress(KeyEvent.VK_E);
            robot.keyRelease(KeyEvent.VK_E);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.mouseMove(0, 0);
            
            String imgName="res/join";
            int i=0,numberOfImages=0;
            while(rsc.load(imgName+i+".png")!=null){
                numberOfImages++;
                i++;
            }           
            Point[] point=new Point[numberOfImages];
            for (int j = 0; j < numberOfImages; j++) {
                point[j]=ssc.pointOnScreen(ImageIO.read(rsc.load(imgName+j+".png")), 500);
                if(point[j]!=null){
                    robot.mouseMove(point[j].x, point[j].y);
                    robot.delay(15);
                    robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
                    break;
                }
            }
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
