package main;

import guiwindow.GUIWindow;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.JOptionPane;
import notification.JNotification;
import schedulemeeting.Scheduler;

public class MainClass{

    public static void main(String[] args) {
        MainClass mc = new MainClass();
    }

    public MainClass() {
        if (!new File("delete_me_to_reset_data.db").exists()) {
            GUIWindow guiwindow = new GUIWindow();
            guiwindow.frame.setVisible(true);
        } else {
            Scheduler schedule = new Scheduler();
            String osName= System.getProperty("os.name");
            if(osName.equalsIgnoreCase("windows 10")){
                File dst=new File(System.getProperty("user.home")+"\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\Meeting Scheduler.exe - Shortcut.lnk");
                if(!dst.exists()&&!new File("lib/lock").exists()){
                    int opt=JOptionPane.showConfirmDialog(null,"Would you like Meeting Scheduler to start on Windows startup?",
                            "Meeting Scheduler",JOptionPane.YES_NO_OPTION);
                    if(opt==JOptionPane.YES_OPTION){
                        File src=new File("lib\\MS.lnk");
                        try {
                            Files.copy(src.toPath(), dst.toPath());
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
