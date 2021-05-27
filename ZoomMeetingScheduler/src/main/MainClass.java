package main;

import database.Database;
import guiwindow.GUIWindow;
import java.io.File;
import schedulemeeting.Scheduler;

public class MainClass {

    public static void main(String[] args) {
        if(isFirstLaunch()){
            GUIWindow guiwindow=new GUIWindow();
            Database db=new Database();
            db.initDatabase();
        }else{
            Scheduler schedule=new Scheduler();
        }
    }
    
    public static boolean isFirstLaunch(){
        return !new File("DELETE_ME_IF_NOT_FUNCTIONED_CORRECTLY.db").exists();
    }
    
}
