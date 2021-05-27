package database;

import guiwindow.GUIWindow;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class Database {

    public Connection establishConnection() {
        Connection connect = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:DELETE_ME_IF_NOT_FUNCTIONED_CORRECTLY.db");
            System.out.println("Database connected!");
        } catch (Exception ex) {
            showError("establishConnection", ex);
        }
        return connect;
    }

    public void initDatabase() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 7; i++) {
                        exceutePreparedStatement("create table " + GUIWindow.title[i] + "(n integer,h integer,m integer, url varchar(100))");
                    }
                    System.out.println("Databse initialized successfully!");
                } catch (Exception ex) {
                    showError("initDatabase", ex);
                }
            }
        };
        thread.start();
    }

    public synchronized void exceutePreparedStatement(String statement) throws Exception {
        PreparedStatement ps = establishConnection().prepareStatement(statement);
        ps.execute();
    }

    public int getNumberOfMeetings(String table_name) {
        Connection connect = establishConnection();
        String statement = "select count(n) from " + table_name;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            ps = connect.prepareStatement(statement);
            rs = ps.executeQuery();
            count = rs.getInt(1);
        } catch (Exception ex) {
            showError("getNumberOfMeetings", ex);
        }
        return count;
    }

    public int[] getHour(String table_name) {
        Connection connect = establishConnection();
        int nofmeetings = getNumberOfMeetings(table_name);
        String statement = "select h from " + table_name;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int[] hour = new int[nofmeetings];
        try {
            ps = connect.prepareStatement(statement);
            rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                hour[i] = Integer.parseInt(rs.getString("h"));
                i++;
            }
        } catch (Exception ex) {
            showError("getHour", ex);
        }
        return hour;
    }

    public int[] getMinute(String table_name) {
        Connection connect = establishConnection();
        int nofmeetings = getNumberOfMeetings(table_name);
        String statement = "select m from " + table_name;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int[] minute = new int[nofmeetings];
        try {
            ps = connect.prepareStatement(statement);
            rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                minute[i] = Integer.parseInt(rs.getString("m"));
                i++;
            }
        } catch (Exception ex) {
            showError("getHour", ex);
        }
        return minute;
    }
    
    public String[] getMeetingUrl(String table_name){
        Connection connect = establishConnection();
        int nofmeetings = getNumberOfMeetings(table_name);
        String url[]=new String[nofmeetings];
        String statement = "select url from " + table_name;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int i=0;
        try{
            ps = connect.prepareStatement(statement);
            rs = ps.executeQuery();
            while(rs.next()){
                url[i]=rs.getString("url");
                i++;
            }
        }catch(Exception ex){
            showError("getMeetingUrl",ex);
        }
        
        return url;
    }

    public void showError(String func, Exception ex) {
        JOptionPane.showMessageDialog(null, "Exception in " + func + "()\n" + ex, "Error", 0);
    }

}
