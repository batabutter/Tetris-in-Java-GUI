package FrontEnd;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GameOver {
    GameRunner runner;
    /*public static void main(String[] args) {
        new GameOver();
    }*/

    public GameOver(GameRunner runner) {
        this.runner = runner;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                Queue<String> queue = new LinkedList<>();
                queue.add("Game Over");
                queue.add("Score : " + runner.getScore());

                Inner pane = new Inner();
                pane.setMessages(queue);
                JButton MainMenu = new JButton("Main Menu");
                JFrame frame = new JFrame("GO");
                frame.add(MainMenu);
                frame.setSize(900,800);
                frame.getContentPane().setBackground(Color.black);
                //frame.setBackground(Color.black);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(pane);
                //frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
    public class Inner extends JPanel{

    	// TODO Auto-generated method stub
    	Queue<String> queue;
        String message;

        int xPos;

        public Inner() {
            //menu.addActionListener((ActionListener)this);
     
        	Timer timer = new Timer(20, new ActionListener() {
        		@Override
               
                public void actionPerformed(ActionEvent e) {
                    if (message == null) {
                        message = queue.remove();
                        xPos = getWidth();
                    }
                    xPos -= 4;
                    FontMetrics fm = getFontMetrics(getFont());
                    int stringWidth = fm.stringWidth(message);
                    if (xPos <= -stringWidth) {
                        queue.add(message);
                        xPos = getWidth();
                        message = queue.remove();
                    }
                    repaint();
                }
            });
            timer.start();
        }
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (message != null) {
            	Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(Color.black);
                g2d.fillRect(0,0,900,800);
                g2d.setBackground(Color.black);
                FontMetrics fm = g2d.getFontMetrics();
                g2d.setColor(Color.blue);
                Font stringFont = new Font(Font.DIALOG, Font.ITALIC, 100);
                //g2d.setFont(stringFont);
                g2d.setFont(new Font("Futura-Bold", Font.PLAIN, 100)); 
                //int yPos = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                int yPos = 200;
                g2d.drawString(message, xPos, yPos);
                g2d.setFont(new Font("Futura-Bold", Font.PLAIN, 50));
                g2d.setColor(Color.green);
                g2d.drawString("Score: " + runner.getScore(), 300, 410);
                //System.out.println("("+xPos + "," + yPos+")");
                g2d.dispose();
            }
        }

        protected void setMessages(Queue<String> queue) {
            this.queue = queue;
        }


    }

}

