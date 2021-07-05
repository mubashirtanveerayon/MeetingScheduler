package database;

import guiwindow.GUIWindow;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import main.MainClass;
import notification.JNotification;
import translation.TranslateUrl;

public class WriteData {

    private final Database db = new Database();
    private final TranslateUrl turl=new TranslateUrl();
        
    public void write() {
        db.initDatabase();
        try {
            Thread.sleep(1000);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        if(!new File("delete_me_to_reset_data.db").exists()){
            JOptionPane.showMessageDialog(null, "Could not connect with database!","Error",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        boolean success=true;
        for (int i = 0; i < GUIWindow.COMPONENTS; i++) {
            for (int j = 0; j < GUIWindow.MAXIMUM_NUMBER_OF_MEETINGS; j++) {
                if (GUIWindow.timeField[i][j].isEnabled() && GUIWindow.urlField[i][j].isEnabled()) {
                    String time = GUIWindow.timeField[i][j].getText();
                    String url = GUIWindow.urlField[i][j].getText().trim();
                    String sql = "insert into " + GUIWindow.title[i] + " values (" + j + "," + retrieveHour(time) + "," + retrieveMinute(time) + ",\"" + turl.hash(url) + "\")";
                    Connection connect = db.establishConnection();
                    PreparedStatement ps = null;
                    try {
                        ps = connect.prepareStatement(sql);
                        ps.execute();
                    } catch (Exception ex) {
                        success=false;
                        System.out.println(ex);
                    } finally {
                        try {
                            ps.close();
                            connect.close();
                        } catch (Exception ex) {
                            success=false;
                            System.out.println(ex);
                        }
                    }
                }
            }
        }
        if(success){
            JNotification notification;
            try {
                notification = new JNotification(JNotification.DONE_MESSAGE);
                notification.send();
            } catch (Exception ex) {
                System.out.println(ex);
            }            
        }else{
            try{
                JNotification notification = new JNotification(JNotification.ERROR_MESSAGE);
                notification.send();
            }catch(Exception ex){
                System.out.println(ex);
            }
        }
        JOptionPane.showMessageDialog(null, "The application will keep running in the background if you have any meeting scheduled for today.");
        JOptionPane.showMessageDialog(null, "If you want to reset your data, delete the 'delete_me_to_reset_data.db' file and restart the app."
                +"\n You will be redirected to the window where you will be able to provide your meeting data again.");
        MainClass mc = new MainClass();
    }

    public String retrieveMinute(String text) {
        String data = text;
        int indexOfColon = data.indexOf(":");
        StringBuffer sb = new StringBuffer(data);
        return String.valueOf(sb.delete(0, indexOfColon + 1));
    }

    public String retrieveHour(String text) {
        String data = text;
        int indexOfColon = data.indexOf(":");
        StringBuffer sb = new StringBuffer(data);
        return String.valueOf(sb.delete(indexOfColon, data.length()));
    }
    
}
