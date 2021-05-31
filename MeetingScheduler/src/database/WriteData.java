package database;

import guiwindow.GUIWindow;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import main.MainClass;

public class WriteData {

    public Database db = new Database();

    public void write() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (GUIWindow.timeField[i][j].isEnabled() && GUIWindow.urlField[i][j].isEnabled()) {
                    String time = GUIWindow.timeField[i][j].getText();
                    String url = GUIWindow.urlField[i][j].getText();
                    String sql = "insert into " + GUIWindow.title[i] + " values (" + j + "," + retrieveHour(time) + "," + retrieveMinute(time) + ",'" + url + "')";
                    Connection connect = db.establishConnection();
                    PreparedStatement ps = null;
                    try {
                        ps = connect.prepareStatement(sql);
                        ps.execute();
                    } catch (Exception ex) {
                        System.out.println(ex);
                    } finally {
                        try {
                            ps.close();
                            connect.close();
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    }
                }
            }
        }
        System.out.println("All data has been written successfully!");
        JOptionPane.showMessageDialog(null, "All data has been saved successfully.");
        JOptionPane.showMessageDialog(null, "The application will keep running in the background.");
        JOptionPane.showMessageDialog(null, "If you want to reset your data, run the 'run_me_to_reset_data.bat' file and restart the app.\n"
                + "You will be redirected to the window where you will be able to provide your meeting data again.");
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
