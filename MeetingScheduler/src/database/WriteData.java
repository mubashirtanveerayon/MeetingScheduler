package database;

import guiwindow.GUIWindow;
import java.net.URL;
import javax.swing.JOptionPane;

public class WriteData {

    public Database db = new Database();

    public void write() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 7; i++) {
                        for (int j = 0; j < 7; j++) {
                            if(GUIWindow.timeField[i][j].isEnabled()&&GUIWindow.urlField[i][j].isEnabled()){
                                String time = GUIWindow.timeField[i][j].getText();
                                String url = GUIWindow.urlField[i][j].getText();
                                db.exceutePreparedStatement("insert into " + GUIWindow.title[i] + " values (" + j + "," + retrieveHour(time) + "," + retrieveMinute(time) + ",'" + url + "')");
                            }
                        }
                    }
                    System.out.println("Data has been written successfully!");
                    JOptionPane.showMessageDialog(null, "All data has been saved successfully. Restart the application for changes to take effect.");
                    JOptionPane.showMessageDialog(null, "After restarting no graphical user interface(GUI) will show up. The process will keep running in the background.");
                    JOptionPane.showMessageDialog(null, "If you want to reset your data, delete the 'DELETE_ME_TO_RESET_DATA.db' file and restart the application.\n"
                            + "You will be redirected to the window where you will be able to provide your meeting data again.");
                    System.exit(0);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Exception in write()\n"+ex, "Error", 0);
                }
            }
        };
        thread.start();
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
    
    public boolean isTimeValid(String text){
        
        try{
            int h = Integer.parseInt(retrieveHour(text));
            int m = Integer.parseInt(retrieveMinute(text));
            if(h<0||h>24||m<0||m>=60){
                return false;
            }
        }catch(Exception ex){
            return false;
        }
        
        return true;
    }
    
    public boolean isUrlValid(String url){
        try{
            new URL(url).openStream().close();
        }catch(Exception ex){
            return false;
        }
        return true;
    }
}