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
        if(System.getProperty("os.name").toLowerCase().contains("windows")){
            DATABASE_LOCATION=System.getProperty("user.home")+"\\Documents\\Meeting Scheduler\\";
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
