//version 1.4.0 -final
package main;

import guiwindow.GUIWindow;
import java.io.File;
import schedulemeeting.Scheduler;

public class MainClass{
    
    public static final String DATABASE_NAME="delete_me_to_reset_data.db";
    public static String DATABASE_LOCATION="";

    public static void main(String[] args) {
        MainClass mc = new MainClass();
    }

    public MainClass() {
		String osName=System.getProperty("os.name").toLowerCase();
        if(osName.contains("windows")||osName.contains("linux")){
            DATABASE_LOCATION=System.getProperty("user.home")+File.separator+"Documents"+File.separator+"Meeting Scheduler"+File.separator;
        }
        if(!new File(DATABASE_LOCATION+DATABASE_NAME).exists()){
            new File(DATABASE_LOCATION).mkdir();
            openWindow().start();
        }
        else{
            Scheduler scheduler = new Scheduler();
        }
    }
    
    private Thread openWindow(){
        Thread thread = new Thread(){
            public void run(){
                GUIWindow guiwindow = new GUIWindow();
                guiwindow.frame.setVisible(true);
            }
        };
        return thread;
    }
}
