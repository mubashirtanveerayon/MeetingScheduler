package schedulemeeting;

import joiningmeeting.Zoom;
import joiningmeeting.GoogleMeet;
import java.text.SimpleDateFormat;
import java.util.Date;
import database.Database;
import guiwindow.GUIWindow;
import java.awt.Desktop;
import java.awt.Frame;
import java.net.URI;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import translation.TranslateUrl;

public class Scheduler extends GUIWindow {

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
    int index = -1;
    
    private TranslateUrl turl = new TranslateUrl();
    private Zoom zoom=new Zoom();
    private GoogleMeet gm=new GoogleMeet();

    public Scheduler() {

        if (nofmeetings != 0) {
            infoLabel.setLocation(355,135);
            panel2.add(infoLabel);
            cardLayout.show(container, "second");
            frame.setBounds(500, 250, 400, 200);
            frame.setState(Frame.ICONIFIED);
            frame.setVisible(true);
        }

        //timer&timertask
        Timer[] timer = new Timer[nofmeetings];
        TimerTask[] task = new TimerTask[nofmeetings];
        Calendar[] calendar = new Calendar[nofmeetings];

        //loop
        for (int i = 0; i < nofmeetings; i++) {

            task[i] = new TimerTask() {
                @Override
                public void run() {
                    try {
                        Desktop desk = Desktop.getDesktop();
                        String meetingUrl=getUrl();
                        desk.browse(new URI(meetingUrl));                        
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
                if (getHourOfDay() <= hour[i] && getMinuteOfHour() <= minute[i]) {
                    timer[i].schedule(task[i], calendar[i].getTime());
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    private String getUrl() {
        index++;
        return turl.translate(url[index], false);
    }

    private String getDay() {
        return d.format(dt).toLowerCase();
    }

    private int getHourOfDay() {
        return Integer.parseInt(h.format(dt));
    }

    private int getMinuteOfHour() {
        return Integer.parseInt(m.format(dt));
    }

    private void pressKeys(String meetingUrl) throws Exception{
        
    }

    

}
