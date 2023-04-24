import javax.swing.*;
import java.awt.*;
import FrontEnd.Player;
import BackEnd.Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Font;

public class Test extends Thread{
    static JFrame frame = new JFrame("Tetris");
    public Test() {
        //Test thread = new Test();
        //frame.setPreferredSize(new Dimension(1600,800));
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
        frame.setLayout(null);

        runGame(frame);
    }

    public void runGame(JFrame frame) {
        Player human = new Player(250, 70, frame);

        Game game = new Game(human);
        game.start();
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

    public static void main(String args[]) {
        Test t = new Test();
    }
}