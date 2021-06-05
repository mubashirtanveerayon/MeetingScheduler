package main;

import database.Database;
import guiwindow.GUIWindow;
import java.io.File;
import javax.swing.JOptionPane;
import schedulemeeting.Scheduler;

public class MainClass{

    public static void main(String[] args) {
        MainClass mc = new MainClass();
    }

    public MainClass() {
        if (isFirstLaunch()) {
            Database db = new Database();
            /*db.initDatabase();
            if (isFirstLaunch()) {
                JOptionPane.showMessageDialog(null, "Could not connect with database!", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }*/
            GUIWindow guiwindow = new GUIWindow();
            guiwindow.frame.setVisible(true);
        } else {
            Scheduler schedule = new Scheduler();
        }        
    }

    public boolean isFirstLaunch() {
        try {
            Thread.sleep(200);
        } catch (Exception ex) {

        }
        return !new File("delete_me_to_reset_data.db").exists();
    }

}
