package FrontEnd;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;
import java.util.Timer;
public class MainMenu extends JPanel implements ActionListener{
    private JFrame frame;
    private JButton start;
    private JPanel contentPane;
    Timer timer = null;
    ImageIcon background;
    public MainMenu(){
        frame = new JFrame("Menu");
        timer = new Timer();
        contentPane = new JPanel();
        //contentPane.setBackground(Color.black);
        frame.add(contentPane);
        ImageIcon startButton = new ImageIcon("Images//button_start (1).png");
		start = new JButton("",startButton);
        start.setBorder(BorderFactory.createLineBorder(Color.green,4));
        start.setActionCommand("start");
        start.setBounds(550,600,276,112);
        frame.add(start);
        start.addActionListener(this);
        JLabel label;
        //frame.setBackground(Color.black);
        frame.getContentPane().setBackground(Color.black);
        ImageIcon logo = new ImageIcon("Images//tetris_logo.png");
        label = new JLabel(logo);
        frame.add(label);
        label.setBounds(525,50,label.getWidth(),label.getHeight());
        frame.setSize(1600,800);
        frame.setLocationRelativeTo(label);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    }
    @Override
    
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        if(e.getActionCommand().equals("start")){
            
        }
    }
    
    public static void main(String[] args) {
    	new MainMenu();
	}
}


