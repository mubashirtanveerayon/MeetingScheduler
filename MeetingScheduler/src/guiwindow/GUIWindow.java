package guiwindow;

import database.WriteData;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import resourceloader.ResourceLoader;

public class GUIWindow extends JFrame implements ChangeListener, ActionListener, KeyListener,MouseListener {

    private JTabbedPane tp = new JTabbedPane();
    public static JPanel[] panel = new JPanel[7];
    private JSpinner[] sp = new JSpinner[panel.length];
    private final JLabel[] spLabel = new JLabel[panel.length];
    private final JLabel[] urlLabel = new JLabel[panel.length];
    private final JLabel[] timeLabel = new JLabel[panel.length];
    public static JTextField[][] urlField = new JTextField[panel.length][panel.length];
    public static JTextField[][] timeField = new JTextField[panel.length][panel.length];
    private ResourceLoader rsc=new ResourceLoader();
    private JLabel[][] dotLabel=new JLabel[panel.length][panel.length];

    public static final String[] title = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    private JButton confirm = new JButton("Confirm");
    private JButton validate = new JButton("Validate");

    public WriteData wd = new WriteData();
    
    private final ImageIcon dot=new ImageIcon(rsc.load("res/green.png"));
    private final ImageIcon info=new ImageIcon(rsc.load("res/infoic.png"));
    
    private final JLabel infoLabel=new JLabel(info);
    
    public GUIWindow() {
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {

        this.setTitle("Meeting Scheduler");
        this.setBounds(400, 100, 520, 550);
        this.setDefaultCloseOperation(3);
        this.setLayout(null);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(18, 30, 49));

        tp.setBounds(60, 20, 390, 400);
        tp.setBackground(new Color(169, 177, 186));
        tp.setForeground(Color.black);
        
        infoLabel.setBounds(475,10,20,20);
        infoLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        infoLabel.addMouseListener(this);

        for (int i = 0; i < panel.length; i++) {
            panel[i] = new JPanel();
            panel[i].setLayout(null);
            panel[i].setBackground(new Color(51,51,55));

            sp[i] = new JSpinner(new SpinnerNumberModel(0, 0, 7, 1));
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
            for (int j = 0; j < panel.length; j++) {
                dotLabel[i][j]=new JLabel();
                dotLabel[i][j].setBounds(187, y+7, 15, 15);
                dotLabel[i][j].setToolTipText("Field Activated! Please provide valid information in the\n"
                        + "activated fields. Time must be provided in 24-hour format.");
                urlField[i][j] = new JTextField();
                timeField[i][j] = new JTextField();
                urlField[i][j].setBounds(215, y, 150, h);
                urlField[i][j].setEnabled(false);
                timeField[i][j].setEnabled(false);
                timeField[i][j].setBounds(25, y, 150, h);
                timeField[i][j].setHorizontalAlignment(JTextField.CENTER);
                timeField[i][j].setToolTipText("Hour and minute in 24-hour format");
                urlField[i][j].setToolTipText("Invite link");
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

        confirm.setBounds(100, 450, 150, 35);
        confirm.setFont(new Font("Arial", Font.PLAIN, 20));
        confirm.setEnabled(false);
        confirm.setFocusable(false);
        confirm.addActionListener(this);

        validate.setBounds(260, 450, 150, 35);
        validate.setFont(new Font("Arial", Font.PLAIN, 20));
        validate.addActionListener(this);
        validate.setFocusable(false);
        validate.setEnabled(false);

        this.add(infoLabel);
        this.add(validate);
        this.add(confirm);
        this.add(tp);
    }

    private boolean disposeFrame() {
        try {
            this.dispose();
        } catch (Exception ex) {
            return false;
        }
        return true;
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

    private int validLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch != 58) {
                length++;
            }
        }
        return length;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        confirm.setEnabled(false);
        for (int i = 0; i < sp.length; i++) {
            if (e.getSource() == sp[i]) {
                int n = Integer.parseInt(sp[i].getValue().toString());
                if (n == 0) {
                    validate.setEnabled(false);
                } else {
                    validate.setEnabled(true);
                }
                for (int j = 0; j < sp.length; j++) {
                    if (j >= n) {
                        urlField[i][j].setText("");
                        timeField[i][j].setText("");
                        urlField[i][j].setEnabled(false);
                        timeField[i][j].setEnabled(false);
                        timeField[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 0));
                        urlField[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 0));
                        dotLabel[i][j].setIcon(null);
                    }
                }
                for (int j = 0; j < n; j++) {
                    urlField[i][j].setEnabled(true);
                    timeField[i][j].setEnabled(true);
                    dotLabel[i][j].setIcon(dot);
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
                    disposeFrame();
                    wd.write();
                }
            };
            thread.start();
        }

        if (e.getSource() == validate) {
            validate.setEnabled(false);
            LoadingScreen lsc = new LoadingScreen();
            Thread thread = new Thread() {
                @Override
                public void run() {
                    lsc.initComponents();
                }
            };
            thread.start();

            Thread thread1 = new Thread() {
                @Override
                public void run() {
                    boolean isDataCorrect = true;
                    for (int i = 0; i < panel.length; i++) {
                        for (int j = 0; j < panel.length; j++) {
                            if (timeField[i][j].isEnabled() && urlField[i][j].isEnabled()) {
                                String time = timeField[i][j].getText();
                                String url = urlField[i][j].getText();
                                if (!(wd.isTimeValid(time) && wd.isUrlValid(url))) {
                                    isDataCorrect = false;
                                    lsc.frame.dispose();
                                    JOptionPane.showMessageDialog(null, "Invalid data detected! Please make sure that you are providing valid information.", "Error", JOptionPane.ERROR_MESSAGE);
                                    confirm.setEnabled(false);
                                    break;
                                }
                            }
                        }
                    }

                    if (isDataCorrect) {
                        confirm.setEnabled(true);
                    }
                    validate.setEnabled(true);
                    try {
                        lsc.frame.dispose();
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            };
            thread1.start();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        confirm.setEnabled(false);
        for (int i = 0; i < panel.length; i++) {
            for (int j = 0; j < panel.length; j++) {
                if (e.getSource() == timeField[i][j]) {
                    if (validLength(timeField[i][j].getText()) == 0) {
                        timeField[i][j].setText(null);
                    }
                    if (!isValidCharacter(e.getKeyChar()) || validLength(timeField[i][j].getText()) > 3) {
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
        if(e.getSource()==infoLabel){
            Information inf=new Information();
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

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}