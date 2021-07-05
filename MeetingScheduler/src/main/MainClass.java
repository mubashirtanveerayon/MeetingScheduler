//version 1.4.0 -final
package main;

import guiwindow.GUIWindow;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import notification.JNotification;
import schedulemeeting.Scheduler;

public class MainClass{

    public static void main(String[] args) {
        MainClass mc = new MainClass();
    }

    public MainClass() {
        if(System.getProperty("java.class.path").contains(System.getenv("SystemDrive"))&&System.getProperty("os.name").toLowerCase().contains("windows")){
            JNotification notification = new JNotification("Warning","You've installed the application where windows is installed!",JNotification.WARNING_ICON);
            notification.setBackgroundColor(new Color(120, 110, 50));
            notification.setWidth(500);
            notification.setTitleColor(Color.black);
            notification.setBodyColor(Color.black);
            notification.setBodyFont(new Font("Arial", Font.PLAIN, 18));
            notification.setBorderThickness(2);
            notification.setBorderColor(Color.black);
            notification.send();
        }
        if (!new File("delete_me_to_reset_data.db").exists()) {
            GUIWindow guiwindow = new GUIWindow();
            guiwindow.frame.setVisible(true);
        } else {
            Scheduler schedule = new Scheduler();            
        }
    }
}
