package joiningmeeting;

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import resourceloader.ResourceLoader;
import screenscanner.ScreenScanner;

public class Common {
    
    private final ScreenScanner ssc=new ScreenScanner();
    private final ResourceLoader rsc=new ResourceLoader();
    private static final String KNOWLEDGEBASE_lOCATION="knowledgebase/";
    
    public void launchMeeting(){
 
        try{
            Robot r = new Robot();
            r.delay(1000);
            r.keyPress(KeyEvent.VK_LEFT);
            r.keyRelease(KeyEvent.VK_LEFT);
            r.delay(10);
            r.keyPress(10);
            r.keyRelease(10);
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }
    
    public synchronized void pressJoinButton(String img_name,int wait){
        try{
            Robot robot = new Robot();
            robot.mouseMove(0, 0);
            Thread.sleep(wait);
            int numberOfImages=0;
            while(rsc.load(KNOWLEDGEBASE_lOCATION+img_name+numberOfImages+".png")!=null){
                numberOfImages++;
            }           
            Point[] point=new Point[numberOfImages];
            for (int j = 0; j < numberOfImages; j++) {
                point[j]=ssc.pointOnScreen(ImageIO.read(rsc.load(KNOWLEDGEBASE_lOCATION+img_name+j+".png")), 500);
                if(point[j]!=null){
                    robot.mouseMove(point[j].x, point[j].y);
                    robot.delay(15);
                    robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
                    break;
                }
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
}
