package database;

import guiwindow.GUIWindow;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Database {
    
    public Connection establishConnection() {
        Connection connect = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:delete_me_to_reset_data.db");
            System.out.println("Database connected!");
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return connect;
    }

    public void initDatabase() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 7; i++) {
                    String sql = "create table " + GUIWindow.title[i] + "(n integer,h integer,m integer, url varchar(100))";
                    Connection connect = establishConnection();
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
        };
        thread.start();
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
            System.out.println(ex);
        } finally {
            try {
                ps.close();
                rs.close();
                connect.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
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
            System.out.println(ex);
        } finally {
            try {
                ps.close();
                rs.close();
                connect.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
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
            System.out.println(ex);
        } finally {
            try {
                ps.close();
                rs.close();
                connect.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        return minute;
    }

    public String[] getMeetingUrl(String table_name) {
        Connection connect = establishConnection();
        int nofmeetings = getNumberOfMeetings(table_name);
        String url[] = new String[nofmeetings];
        String statement = "select url from " + table_name;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int i = 0;
        try {
            ps = connect.prepareStatement(statement);
            rs = ps.executeQuery();
            while (rs.next()) {
                url[i] = rs.getString("url");
                i++;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                ps.close();
                rs.close();
                connect.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }

        return url;
    }
    
}
