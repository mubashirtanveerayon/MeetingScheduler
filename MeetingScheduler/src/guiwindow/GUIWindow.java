package guiwindow;

import database.WriteData;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.URL;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import main.MainClass;
import notification.JNotification;
import resourceloader.ResourceLoader;

public class GUIWindow implements ChangeListener, ActionListener, KeyListener, MouseListener,WindowListener {

    public static final int COMPONENTS = 7;
    public static final int MAXIMUM_NUMBER_OF_MEETINGS = 8;
    
    public static long[][] timeArray=new long[COMPONENTS][MAXIMUM_NUMBER_OF_MEETINGS];
    public static String[][] inviteUrl=new String[COMPONENTS][MAXIMUM_NUMBER_OF_MEETINGS];
    
    private JTabbedPane tp = new JTabbedPane();
    public JPanel[] panel = new JPanel[COMPONENTS];
    private JSpinner[] sp = new JSpinner[COMPONENTS];
    
    private final JLabel[] spLabel = new JLabel[COMPONENTS];
    private final JLabel[] urlLabel = new JLabel[COMPONENTS];
    private final JLabel[] timeLabel = new JLabel[COMPONENTS];
    
    public static JTextField[][] urlField = new JTextField[COMPONENTS][MAXIMUM_NUMBER_OF_MEETINGS];
    public static JTextField[][] timeField = new JTextField[COMPONENTS][MAXIMUM_NUMBER_OF_MEETINGS];
    
    private ResourceLoader rsc = new ResourceLoader();
    
    private JLabel[][] dotLabel = new JLabel[COMPONENTS][MAXIMUM_NUMBER_OF_MEETINGS];
    
    public JPanel panel1=new JPanel(null);
    public JPanel panel2=new JPanel(null);
    
    public CardLayout cardLayout=new CardLayout();
    
    public Container container;
    
    private final JLabel closeLabel1=new JLabel("Closeing this window will");
    private final JLabel closeLabel2=new JLabel("force the application");
    private final JLabel closeLabel3=new JLabel("to shut down!");
    
    public static final String[] title = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    private JButton confirm = new JButton("Confirm");
    private JButton validate = new JButton("Validate");

    public WriteData wd = new WriteData();

    private final ImageIcon green = new ImageIcon(rsc.load("res/green.png"));
    private final ImageIcon info = new ImageIcon(rsc.load("res/infoic.png"));
    private final ImageIcon red = new ImageIcon(rsc.load("res/red.png"));
    private final ImageIcon icon = new ImageIcon(rsc.load("res/icon.png"));
    public JFrame frame=new JFrame("Meeting Scheduler");
    
    public final JLabel infoLabel = new JLabel(info);
    
    public final Information inf = new Information();
    
    public final JLabel resetLabel = new JLabel("Reset database?");

    public GUIWindow() {
        initComponents();
    }

    private void initComponents() {
        
        container=frame.getContentPane();
       
        frame.setBounds(400, 100, 520, 550);
        frame.setDefaultCloseOperation(0);
        container.setLayout(cardLayout);
        frame.setResizable(false);
        frame.setIconImage(icon.getImage());
        frame.addWindowListener(this);

        tp.setBounds(60, 10, 390, 440);
        tp.setBackground(new Color(169, 177, 186));
        tp.setForeground(Color.black);

        infoLabel.setBounds(475, 10, 20, 20);
        infoLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        infoLabel.addMouseListener(this);

        for (int i = 0; i < COMPONENTS; i++) {
            panel[i] = new JPanel();
            panel[i].setLayout(null);
            panel[i].setBackground(new Color(50, 50, 50));

            sp[i] = new JSpinner(new SpinnerNumberModel(0, 0, MAXIMUM_NUMBER_OF_MEETINGS, 1));
            ((DefaultEditor) sp[i].getEditor()).getTextField().setEditable(false);
            sp[i].setFont(new Font("Arial", Font.BOLD, 15));
            sp[i].setBounds(235, 15, 40, 25);
            sp[i].addChangeListener(this);

            spLabel[i] = new JLabel("Number of meetings : ");
            spLabel[i].setBounds(80, 15, 170, 25);
            spLabel[i].setFont(new Font("Arial", Font.PLAIN, 16));
            spLabel[i].setForeground(Color.white);

            urlLabel[i] = new JLabel("Meeting URL :");
            urlLabel[i].setBounds(240, 45, 150, 25);
            urlLabel[i].setFont(new Font("Arial", Font.PLAIN, 17));
            urlLabel[i].setForeground(Color.white);

            timeLabel[i] = new JLabel("Time : (HH:mm)");
            timeLabel[i].setBounds(45, 45, 180, 20);
            timeLabel[i].setFont(new Font("Arial", Font.PLAIN, 17));
            timeLabel[i].setForeground(Color.white);

            int y = 70, h = 30;
            for (int j = 0; j < MAXIMUM_NUMBER_OF_MEETINGS; j++) {
                dotLabel[i][j] = new JLabel();
                dotLabel[i][j].setBounds(187, y + 7, 15, 15);
                urlField[i][j] = new JTextField();
                timeField[i][j] = new JTextField();
                timeField[i][j].setTransferHandler(null);
                urlField[i][j].setBounds(215, y, 150, h);
                urlField[i][j].setEnabled(false);
                timeField[i][j].setEnabled(false);
                timeField[i][j].setBounds(25, y, 150, h);
                timeField[i][j].setHorizontalAlignment(JTextField.CENTER);
                timeField[i][j].setToolTipText("Hour and minute in 24-hour format");
                urlField[i][j].setToolTipText("Invite link");
                timeField[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 0));
                urlField[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 0));
                timeField[i][j].addKeyListener(this);
                urlField[i][j].addKeyListener(this);
                panel[i].add(urlField[i][j]);
                panel[i].add(timeField[i][j]);
                panel[i].add(dotLabel[i][j]);
                y += h + 10;
            }
            panel[i].add(timeLabel[i]);
            panel[i].add(urlLabel[i]);
            panel[i].add(spLabel[i]);
            panel[i].add(sp[i]);
            tp.addTab(title[i], panel[i]);
        }

        confirm.setBounds(100, 460, 150, 35);
        confirm.setFont(new Font("Arial", Font.PLAIN, 20));
        confirm.setEnabled(false);
        confirm.setFocusable(false);
        confirm.setBackground(null);
        confirm.setForeground(Color.white);
        confirm.addActionListener(this);
        confirm.addMouseListener(this);

        validate.setBounds(260, 460, 150, 35);
        validate.setFont(new Font("Arial", Font.PLAIN, 20));
        validate.addActionListener(this);
        validate.addMouseListener(this);
        validate.setBackground(null);
        validate.setForeground(Color.white);
        validate.setFocusable(false);
        
        closeLabel1.setForeground(Color.white);
        closeLabel1.setFont(new Font("Monospaced",Font.PLAIN,25));
        closeLabel1.setBounds(8,15,510,50);
        
        closeLabel2.setForeground(Color.white);
        closeLabel2.setFont(new Font("Monospaced",Font.PLAIN,25));
        closeLabel2.setBounds(35,55,510,50);
        
        closeLabel3.setForeground(Color.white);
        closeLabel3.setFont(new Font("Monospaced",Font.PLAIN,25));
        closeLabel3.setBounds(100,95,510,50);
               
        resetLabel.setBounds(120, 145, 150, 20);
        resetLabel.setFont(new Font("Monospaced", Font.PLAIN, 17));
        resetLabel.setForeground(new Color(88, 180, 255));
        resetLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        resetLabel.addMouseListener(this);

        panel1.add(infoLabel);
        panel2.add(closeLabel1);
        panel2.add(closeLabel2);
        panel2.add(closeLabel3);
        panel1.add(validate);
        panel1.add(confirm);
        panel1.add(tp);
        
        panel1.setBackground(Color.black);
        panel2.setBackground(Color.black);
        
        container.add(panel1,"first");
        container.add(panel2,"second");
    }

    private boolean isValidCharacter(char ch) {
        char[] validchar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', KeyEvent.VK_BACK_SPACE, KeyEvent.VK_DELETE};
        for (int i = 0; i < validchar.length; i++) {
            if (ch == validchar[i]) {
                return true;
            }
        }
        return false;
    }

    private int getValidLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != 58) {
                length++;
            }
        }
        return length;
    }

    private void validateData() {
        confirm.setEnabled(false);
        Thread thread = new Thread() {
            @Override
            public void run() {
                boolean allDataCorrect = true;
                JOptionPane.showMessageDialog(null, "Please be patient while the application is validating your data."
                        + "\n Make sure that you have a stable internet connection.");
                boolean dataProvided = false;
                for (int i = 0; i < COMPONENTS; i++) {
                    for (int j = 0; j < MAXIMUM_NUMBER_OF_MEETINGS; j++) {
                        if (timeField[i][j].isEnabled() && urlField[i][j].isEnabled()) {
                            dataProvided = true;                            
                            String time = timeField[i][j].getText();
                            timeArray[i][j]=getTime(wd.retrieveHour(time),wd.retrieveMinute(time));
                            String url = urlField[i][j].getText();
                            inviteUrl[i][j] = url;
                            if (isTimeValid(time)&& isUrlValid(url)) {
                                dotLabel[i][j].setIcon(green);
                                dotLabel[i][j].setToolTipText("Field Activated! Please provide valid information in the\n"
                                        + "activated fields. Time must be provided in 24-hour format.");
                            } else {
                                allDataCorrect = false;
                                dotLabel[i][j].setIcon(red);
                                dotLabel[i][j].setToolTipText("Invalid information provieded!");
                            }
                        }    
                    }
                }
                for (int i = 0; i < COMPONENTS; i++) {
                    for (int j = 0; j < MAXIMUM_NUMBER_OF_MEETINGS; j++) {
                        for (int k = j + 1; k < MAXIMUM_NUMBER_OF_MEETINGS; k++) {
                            if (timeArray[i][k] != 0 && (timeArray[i][k] < timeArray[i][j])) {
                                swapValues(i, j, k);
                            }
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, "Data validation complete!");
                if (allDataCorrect && dataProvided) {
                    confirm.setEnabled(true);
                } else if (!dataProvided) {
                    confirm.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "You haven't provided any meeting information!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    confirm.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "Invalid data detected! Please provide valid information in the red-marked fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        thread.start();
    }
    
    private void swapValues(int index,int i,int j){
        long temp = timeArray[index][i];
        timeArray[index][i]=timeArray[index][j];
        timeArray[index][j]=temp;
        String tempTime = timeField[index][i].getText();
        timeField[index][i].setText(timeField[index][j].getText());
        timeField[index][j].setText(tempTime);
        String tempUrl = urlField[index][i].getText();
        urlField[index][i].setText(urlField[index][j].getText());
        urlField[index][j].setText(tempUrl);
    }

    public boolean isTimeValid(String text) {
        try {
            int h = Integer.parseInt(wd.retrieveHour(text));
            int m = Integer.parseInt(wd.retrieveMinute(text));
            if (h < 0 || h > 24 || m < 0 || m >= 60) {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    private long getTime(String hour,String minute){
        int h=Integer.parseInt(hour);
        int m=Integer.parseInt(minute);
        Calendar time=Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY,h);
        time.set(Calendar.MINUTE,m);
        time.set(Calendar.SECOND,0);
        time.set(Calendar.MILLISECOND,m);
        return time.getTimeInMillis();
    }

    public boolean isUrlValid(String url) {
        try {
            new URL(url).openStream().close();
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        confirm.setEnabled(false);
        for (int i = 0; i < COMPONENTS; i++) {
            if (e.getSource() == sp[i]) {
                int n = Integer.parseInt(sp[i].getValue().toString());
                for (int j = 0; j < MAXIMUM_NUMBER_OF_MEETINGS; j++) {
                    if (j >= n) {
                        urlField[i][j].setText("");
                        timeField[i][j].setText("");
                        urlField[i][j].setEnabled(false);
                        timeField[i][j].setEnabled(false);
                        timeField[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 0));
                        urlField[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 0));
                        dotLabel[i][j].setIcon(null);
                        dotLabel[i][j].setToolTipText(null);
                    }
                }
                for (int j = 0; j < n; j++) {
                    urlField[i][j].setEnabled(true);
                    timeField[i][j].setEnabled(true);
                    if (dotLabel[i][j].getIcon() == null) {
                        dotLabel[i][j].setIcon(green);
                        dotLabel[i][j].setToolTipText("Field Activated! Please provide valid information in the\n"
                                + "activated fields. Time must be provided in 24-hour format.");
                    }
                    timeField[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 2));
                    urlField[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 2));
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirm) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    frame.setVisible(false);
                    wd.write();
                }
            };
            thread.start();
        }

        if (e.getSource() == validate) {
            validateData();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        confirm.setEnabled(false);
        for (int i = 0; i < COMPONENTS; i++) {
            for (int j = 0; j < MAXIMUM_NUMBER_OF_MEETINGS; j++) {
                if (e.getSource() == timeField[i][j]) {
                    if (getValidLength(timeField[i][j].getText()) == 0) {
                        timeField[i][j].setText(null);
                    }
                    if (!isValidCharacter(e.getKeyChar()) || getValidLength(timeField[i][j].getText()) > 3) {
                        e.consume();
                    }
                    if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE && timeField[i][j].getText().length() == 2) {
                        timeField[i][j].setText(timeField[i][j].getText() + ":");
                    }
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == infoLabel) {
            if(inf.isVisible()){
                inf.setVisible(false);
            }else{
                inf.setVisible(true);
            }
        }
        if(e.getSource()==resetLabel){
            int opt=JOptionPane.showConfirmDialog(null,"Are you sure, you want to reset the database?","Reset database",JOptionPane.YES_NO_OPTION);
            if(opt==JOptionPane.YES_OPTION){
                if(new File("delete_me_to_reset_data.db").delete()){
                    JNotification notification = new JNotification("Success","Successfully resetted database!",JNotification.DONE_MESSAGE);
                    notification.setBackgroundColor(new Color(30, 255, 30));
                    notification.setTitleColor(new Color(50, 50, 50));
                    notification.setBodyColor(new Color(0, 0, 0));
                    notification.setBodyFont(new Font("Monospaced", Font.PLAIN, 18));
                    notification.setBorderColor(Color.black);
                    notification.setBorderThickness(2);
                    notification.send();
                    int res = JOptionPane.showConfirmDialog(null, "For changes to take effect you'll need to restart Meeting Scheduler."
                            + " Do you want to restart now?","Restart",JOptionPane.YES_NO_OPTION);
                    if(res==JOptionPane.YES_OPTION){
                        frame.setVisible(false);
                        try{
                            Thread.sleep(2000);
                        }catch(Exception ex){
                            System.out.println(ex);
                        }
                        MainClass mc = new MainClass();
                    }
                }else{
                    try{
                        JNotification notification=new JNotification(JNotification.ERROR_MESSAGE);
                        notification.send();
                    }catch(Exception ex){
                        System.out.println(ex);
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == confirm) {
            confirm.setBackground(Color.white);
            confirm.setForeground(Color.black);
        }
        if (e.getSource() == validate) {
            validate.setBackground(Color.white);
            validate.setForeground(Color.black);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == confirm) {
            confirm.setBackground(null);
            confirm.setForeground(Color.white);
        }
        if (e.getSource() == validate) {
            validate.setBackground(null);
            validate.setForeground(Color.white);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        int opt=JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the application?","Meeting Scheduler",JOptionPane.YES_NO_OPTION);
        if(opt==JOptionPane.YES_OPTION){
            System.exit(0);
        }
        
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
