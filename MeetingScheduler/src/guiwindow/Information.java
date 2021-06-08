package guiwindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import resourceloader.ResourceLoader;

public class Information extends JFrame implements MouseListener{

    ImageIcon ico,img;
    JLabel inf1,ver,earlyrelease,txt1,txt2;
    public JLabel linkLabel=new JLabel("Source Code");
    public JLabel linkStyle=new JLabel();
    private ResourceLoader rsc=new ResourceLoader();
    
    
    public Information(){
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setBackground(Color.DARK_GRAY);       
        ico = new ImageIcon(rsc.load("res/info.png"));
        this.setIconImage(ico.getImage());
        this.setBounds(550,320,300,190);
        this.setUndecorated(false);
        this.setResizable(false);
        this.setTitle("Information");
        this.addMouseListener(this);
       
        img = new ImageIcon(rsc.load("res/info1.png"));
        inf1 = new JLabel(img);
        inf1.setBounds(25,20,40,40);
        this.add(inf1);
        
        ver = new JLabel();
        ver.setText("Version 1.1");
        ver.setForeground(Color.white);
        ver.setFont(new Font("Aerial",Font.ITALIC,15));
        ver.setBounds(170,117,80,20);
        this.add(ver);
        
        txt1 = new JLabel("Created and developed");
        txt1.setForeground(Color.white);
        txt1.setFont(new Font("System",Font.PLAIN,17));
        txt1.setBounds(70,27,180,20);
        this.add(txt1);
        
        txt2 = new JLabel("by Ayon. All rights reserved.");
        txt2.setForeground(Color.white);
        txt2.setFont(new Font("System",Font.PLAIN,16));
        txt2.setBounds(60,50,200,20);
        this.add(txt2);
        
        earlyrelease = new JLabel("Under Development");
        earlyrelease.setForeground(Color.white);
        earlyrelease.setFont(new Font("Aerial",Font.PLAIN,15));
        earlyrelease.setBounds(15,117,130,20);
        this.add(earlyrelease);
       
        linkLabel.setFont(new Font("Monospaced",Font.PLAIN,17));
        linkLabel.setBounds(85,85,115,15);
        linkLabel.setForeground(new Color(88,180,255));
        linkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        linkLabel.addMouseListener(this);
        this.add(linkLabel);
        
        linkStyle.setBounds(84,99,115,1);
        linkStyle.setOpaque(true);
        linkStyle.setBackground(Color.DARK_GRAY);
        linkStyle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        linkStyle.addMouseListener(this);
        this.add(linkStyle);        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==linkLabel||e.getSource()==linkStyle){
            Desktop d=Desktop.getDesktop();
            try{
                d.browse(new URI("https://github.com/mubashirtanveerayon/MeetingScheduler.git"));
            }catch(Exception ex){
                
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
        if(e.getSource()==linkLabel||e.getSource()==linkStyle){
            linkStyle.setBackground(new Color(88,180,255));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource()==linkLabel||e.getSource()==linkStyle){
            linkStyle.setBackground(Color.DARK_GRAY);
        }
    }
}
