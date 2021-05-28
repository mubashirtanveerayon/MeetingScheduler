package guiwindow;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;


public class LoadingScreen{
    
    public JFrame frame=new JFrame("Loading");
    private JLabel validLabel=new JLabel("Validating data. This may take a while.");
    private JLabel loadLabel=new JLabel("Validating time and url...");
    private JProgressBar bar=new JProgressBar(0,100);
       
    public void initComponents(){
        frame.setTitle("Loading");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setBounds(500, 280, 330, 150);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(230,230,230));
        
        validLabel.setBounds(15,15,320,20);
        validLabel.setFont(new Font("Arial",Font.PLAIN,17));
        validLabel.setForeground(Color.black);
        
        loadLabel.setBounds(15,50,280,20);
        loadLabel.setFont(new Font("Monospaced",Font.PLAIN,14));
        loadLabel.setForeground(Color.black);
        
        bar.setBounds(15,70,290,30);
        bar.setForeground(new Color(9,134,40));
        bar.setBackground(Color.white);
        
        frame.add(loadLabel);
        frame.add(bar);
        frame.add(validLabel);
        frame.setVisible(true);
        progress();
    }
    
    private void progress(){        
        int count=0;
        while(count<=100){
            try{
                Thread.sleep(100);
            }catch(Exception ex){
                
            }
            bar.setValue(count);
            count+=1;
        }       
    }    
}
