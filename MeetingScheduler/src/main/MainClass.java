package main;

import guiwindow.GUIWindow;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import mslinks.ShellLink;
import notification.JNotification;
import schedulemeeting.Scheduler;

public class MainClass{

    public static void main(String[] args) {
        MainClass mc = new MainClass();
    }

    public MainClass() {
        if(System.getProperty("java.class.path").contains(System.getenv("SystemDrive"))){
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
            String osName= System.getProperty("os.name");
            if(osName.equalsIgnoreCase("windows 10")){
                String dest=System.getProperty("user.home")+"\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\Meeting Scheduler.exe - Shortcut.lnk";
                File dst=new File(dest);
                if(!dst.exists()&&!new File("lib/lock").exists()){
                    int opt=JOptionPane.showConfirmDialog(null,"Would you like Meeting Scheduler to start on Windows startup?",
                            "Meeting Scheduler",JOptionPane.YES_NO_OPTION);
                    if(opt==JOptionPane.YES_OPTION){
                        try {
                            ShellLink.createLink("Meeting Scheduler.exe", dest);
                            JNotification notification = new JNotification(JNotification.DONE_MESSAGE);
                            notification.send();
                        } catch (Exception ex) {
                            System.out.println(ex);
                            JNotification notification;
                            try {
                                notification = new JNotification(JNotification.ERROR_MESSAGE);
                                notification.send();
                            } catch (Exception ex1) {
                                System.out.println(ex1);
                            }                            
                        }
                    }else{
                        BufferedWriter bw=null;
                        try {
                            bw = new BufferedWriter(new FileWriter(new File("lib/lock")));
                            bw.write("29-10-2005");
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }finally{
                            try{
                                bw.close();
                            }catch(Exception ex){
                                System.out.println(ex);
                            }
                        }
                    }
                }
            }
        }
    }
}
