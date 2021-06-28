package schedulemeeting;

import joiningmeeting.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import database.Database;
import guiwindow.GUIWindow;
import java.awt.Desktop;
import java.awt.Frame;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.net.URI;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import translation.TranslateUrl;

public class Scheduler extends GUIWindow{

    SimpleDateFormat d = new SimpleDateFormat("EEEEEEEEEEEE");
    SimpleDateFormat h = new SimpleDateFormat("HH");
    SimpleDateFormat m = new SimpleDateFormat("mm");
    Database db = new Database();
    Date dt = new Date();

    String day = getDay();
    int nofmeetings = db.getNumberOfMeetings(day);
    int[] hour = db.getHour(day);
    int[] minute = db.getMinute(day);
    String[] url = db.getMeetingUrl(day);
    boolean[] isScheduled=new boolean[nofmeetings];
   
    private TranslateUrl turl = new TranslateUrl();

    public Scheduler() {
        if (nofmeetings != 0) {
            frame.setBounds(500, 250, 400, 210);
            infoLabel.setLocation(355,145);
            panel2.add(infoLabel);
            panel2.add(resetLabel);
            cardLayout.show(container, "second");
            
            frame.setState(Frame.ICONIFIED);
            frame.setVisible(true);
        }else{
            System.exit(0);
        }

        //timer&timertask
        Timer[] timer = new Timer[nofmeetings];
        TimerTask[] task = new TimerTask[nofmeetings];
        Calendar[] calendar = new Calendar[nofmeetings];

        //loop
        for (int i = 0; i < nofmeetings; i++) {
            isScheduled[i]=false;
            task[i] = new TimerTask() {
                @Override
                public void run() {
                    try {
                        Desktop desk = Desktop.getDesktop();
                        for (int k = 0; k < nofmeetings; k++) {
                            if(isScheduled[k]){
                                isScheduled[k]=false;
                                String meetingUrl = turl.translate(url[k], false);
                                desk.browse(new URI(meetingUrl));
                                pressKeys(meetingUrl);
                                break;
                            }
                        }
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            };

            calendar[i] = Calendar.getInstance();
            timer[i] = new Timer();
            calendar[i].set(Calendar.HOUR_OF_DAY, hour[i]);
            calendar[i].set(Calendar.MINUTE, minute[i]);
            calendar[i].set(Calendar.SECOND, 0);
            calendar[i].set(Calendar.MILLISECOND, 0);
            try {
                if (dt.getTime() <= calendar[i].getTimeInMillis()+180000) {
                    isScheduled[i]=true;
                    timer[i].schedule(task[i], calendar[i].getTime());
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        checkScheduledMeeting().start();
    }

    private String getDay() {
        return d.format(dt).toLowerCase();
    }
    
    

    private synchronized void pressKeys(String meetingUrl) {
        try{
            Robot robot=new Robot();
            Thread.sleep(10000);
            robot.keyPress(KeyEvent.VK_F11);
            robot.keyRelease(KeyEvent.VK_F11);
        }catch(Exception ex){
            System.out.println(ex);
        }
        
        if(meetingUrl.contains("zoom")||meetingUrl.contains("google")||meetingUrl.contains("webex")){
            LaunchMeeting lm=new LaunchMeeting(meetingUrl);
        }
        
    }
    
    private Thread checkScheduledMeeting(){
        Thread thread=new Thread(){
            @Override 
            public void run(){
                while(true){
                    boolean shouldExit=true;
                    for(boolean scheduled:isScheduled){
                        if(scheduled){
                            shouldExit=false;
                            break;
                        }
                    }
                    if(shouldExit){
                        try{
                            Thread.sleep(150000);
                        }catch(Exception ex){
                            System.out.println(ex);
                        }
                        System.exit(0);
                    }
                }
            }
        };
        return thread;
    }

}
