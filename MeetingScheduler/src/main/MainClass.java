package main;

import guiwindow.GUIWindow;
import java.io.File;
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
        }        
    }
}
