package joiningmeeting;

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import resourceloader.ResourceLoader;
import screenscanner.ScreenScanner;

public class LaunchMeeting {
    
    private final ScreenScanner ssc=new ScreenScanner();
    private final ResourceLoader rsc=new ResourceLoader();
    private static final String KNOWLEDGEBASE_LOCATION="knowledgebase/";
    
    private String refName="";
    
    public LaunchMeeting(String url){        
        if(url.contains("zoom")){            
            refName="initzoom";
            try {
                waitTillPageLoads();
            } catch (Exception ex) {
                System.out.println(ex);
            }
            launchMeeting();
        }else if(url.contains("webex")){           
            refName="initwebex";
            try {
                waitTillPageLoads();
            } catch (Exception ex) {
                System.out.println(ex);
            }
            launchMeeting();
            pressJoinButton("webexjoin",1500);
        }else if(url.contains("google")){             
            refName = "meetjoin";
            try {
                waitTillPageLoads();
            } catch (Exception ex) {
                System.out.println(ex);
            }
            disconnectMedia();
            pressJoinButton(refName,50);
        }        
        
    }
    
    private void waitTillPageLoads() throws Exception{
        boolean pageLoaded=false;
        int tried=0;
        int numberOfRefs=numberOfImages(refName);        
        while(!pageLoaded&&tried<=150){  
            Point[] point=new Point[numberOfRefs];
            for (int i = 0; i < point.length; i++) {
                point[i] = ssc.pointOnScreen(ImageIO.read(rsc.load(KNOWLEDGEBASE_LOCATION+refName+i+".png")), 100);
                Thread.sleep(400);
                if(point[i]!=null){ 
                    Robot r=new Robot();
                    if(i==0||refName.contains("meet")){
                        r.mouseMove(point[i].x-200,point[i].y);
                        r.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
                        r.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
                    }else{
                        r.mouseMove(point[i].x,point[i].y);
                        r.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
                        r.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
                    }
                    pageLoaded=true;
                    System.out.println("gg");
                    break;
                }
            }
            Thread.sleep(1500);
            tried++;
        }
    }
    
    public void launchMeeting(){
 
        try{
            Robot r = new Robot();
            r.delay(500);
            r.keyPress(KeyEvent.VK_LEFT);
            r.keyRelease(KeyEvent.VK_LEFT);
            r.delay(10);
            r.keyPress(10);
            r.keyRelease(10);
            System.out.println("hh");
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }
    
    public synchronized void pressJoinButton(String img_name,int wait){
        try{
            Robot robot = new Robot();
            Thread.sleep(wait);
            int numberOfImages=numberOfImages(img_name);
            Point[] point=new Point[numberOfImages];
            boolean mousePressed=false;
            int tried=0;
            while(!mousePressed&&tried<=200){
                for (int j = 0; j < numberOfImages; j++) {
                    point[j] = ssc.pointOnScreen(ImageIO.read(rsc.load(KNOWLEDGEBASE_LOCATION + img_name + j + ".png")), 500);
                    if (point[j] != null) {
                        robot.mouseMove(point[j].x, point[j].y);
                        robot.delay(15);
                        robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
                        mousePressed=true;
                        break;
                    }
                }
                tried++;
                Thread.sleep(500);
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    //googlemeet
    public void disconnectMedia() {
        try {
            Robot robot = new Robot();
            robot.delay(800);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.delay(120);
            robot.keyPress(KeyEvent.VK_D);
            robot.keyRelease(KeyEvent.VK_D);
            robot.delay(120);
            robot.keyPress(KeyEvent.VK_E);
            robot.keyRelease(KeyEvent.VK_E);
            robot.keyRelease(KeyEvent.VK_CONTROL);                                   
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    private int numberOfImages(String imageName){
        int numberOfImages = 0;
        while (rsc.load(KNOWLEDGEBASE_LOCATION + imageName + numberOfImages + ".png") != null) {
            numberOfImages++;
        }
        return numberOfImages; 
    }
    
}
