package FrontEnd;
import BackEnd.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Graphics;
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
        start.setBounds(300,600,276,112);
        frame.add(start);
        start.addActionListener(this);
        JLabel label;
        //frame.setBackground(Color.black);
        frame.getContentPane().setBackground(Color.black);
        ImageIcon logo = new ImageIcon("Images//tetris_logo.png");
        label = new JLabel(logo);
        frame.add(label);
        label.setBounds(525,50,label.getWidth(),label.getHeight());
        frame.setSize(900,800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    @Override
    
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        if(e.getActionCommand().equals("start")){
            changeBoard();
            runGame(frame);
        }


    }

    public void changeBoard() {
        int boardWidth = 300;
        int boardHeight = 600;
        int frameWidth = 900;
        int frameHeight = 800; 
        int squareDim = 30;
        frame.setSize(frameWidth,frameHeight);
        frame.setBackground(Color.black);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        Dimension size = new Dimension(frameWidth,frameHeight);
        BufferedImage background = new BufferedImage(size.width, size.height, java.awt.Image.SCALE_SMOOTH);
        Graphics g = background.createGraphics();

        drawBoard(250,70,boardWidth, boardHeight, squareDim, g);
        JLabel backGroundImg = new JLabel();
        backGroundImg.setIcon(new ImageIcon(background.getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH)));;
        Image myImage = background.getScaledInstance(frameWidth, frameHeight, squareDim);


        frame.setContentPane(new ImagePanel(myImage));
        frame.setVisible(true);
        

        //Most important piece of code in the entire project
        //frame.setLayout(null);

    }
    
    class ImagePanel extends JComponent {
        private Image image;
        public ImagePanel(Image image) {
            this.image = image;
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    }

    public void runGame(JFrame frame) {
        Player human = new Player(250, 70, frame);

        Game game = new Game(human);
        game.start();
    }

    public static void main(String[] args) {
    	new MainMenu();
	}

    public void drawBoard(int startX, int startY, int boardWidth, int boardHeight, int squareDim, Graphics g) {
        g.drawRect(startX-20, startY-20, (int) boardWidth + 2*(20), boardHeight + 2*(20));
        g.fillRect(startX, startY, boardWidth, boardHeight);
        
        g.setColor(new Color(112,112,112));
            
            
            for (int i = 0; i <= boardWidth; i = i+squareDim) {
                g.drawLine(startX+i, startY, startX+i, startY + boardHeight);
            }
            
            for (int i = 0; i <= boardHeight; i+= squareDim) {
                g.drawLine(startX, startY+i, startX+boardWidth, startY + i);
            }

            //In the future, variables should be created to designate where they are

        g.setColor(Color.yellow);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.drawString("Next:", boardWidth + 90+startX, 120);

        g.setColor(Color.gray);
        g.drawRect(boardWidth + 30 + startX, startY, 200, 200);

        g.setColor(Color.yellow);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.drawString("Score:", boardWidth + 90+startX, 350 + startY);

        g.setColor(Color.gray);
        g.drawRect(boardWidth + 30 + startX, startY+300, 200, 300);

        g.setColor(Color.yellow);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.drawString("Hold:", startX -180, 120);

        g.setColor(Color.gray);
        g.drawRect(startX -230, startY, 200, 200);

    }

}


