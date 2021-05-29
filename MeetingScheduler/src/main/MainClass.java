package main;

import database.Database;
import guiwindow.GUIWindow;
import java.io.File;
import schedulemeeting.Scheduler;

public class MainClass {

    public static void main(String[] args) {
        MainClass mc=new MainClass();
    }
    
    public MainClass(){
        if(isFirstLaunch()){
            GUIWindow guiwindow=new GUIWindow();
            Database db=new Database();
            db.initDatabase();
        }else{
            Scheduler schedule=new Scheduler();
        }
    }
    
    public boolean isFirstLaunch(){
        return !new File("database.db").exists();
    }
    
}
