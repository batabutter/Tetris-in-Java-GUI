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
public class GameRunner extends JPanel implements ActionListener{
    private JFrame mainMenu;
    private JFrame board;
    private JFrame gameOverScreen;
    private JButton start;
    private boolean startGame = false;
    private int frameCounter;
    private int boardWidth = 300;
    private int boardHeight = 600;
    private int frameWidth = 900;
    private int frameHeight = 800; 
    private int squareDim = 30;
    Timer timer = null;

    ImageIcon background;
    public GameRunner() {

        mainMenu = new JFrame("Menu");
        //contentPane.setBackground(Color.black);
        mainMenuScreen(mainMenu);

        //If any needed animations or differnet displays are needed, you can just use this loop
        while (!startGame) {
            long startTime = System.currentTimeMillis();
            long elapsedTime = 0;

            while (elapsedTime < 16) {
                //perform db poll/check
                elapsedTime = (new Date()).getTime() - startTime;
            }
            frameCounter++;
            try {
                Thread.sleep(10);     
            } catch (Exception e) {
                System.out.println("Frame unprocessed");
            };

            if (frameCounter == 60)
                frameCounter = 0;
        }
        mainMenu.setVisible(false);
        mainMenu.setResizable(false);
        board.setResizable(false);
        int score = runGame(board);
        board.setVisible(false);

        gameOverScreen = new JFrame("Tetris");
        gameOverScreen(gameOverScreen, score);


        //frame.setVisible(false);
    }
    @Override
    
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        if(e.getActionCommand().equals("start")){
            board = new JFrame("Tetris");
            changeBoard(board);
            long startTime = System.currentTimeMillis();
            long elapsedTime = 0;

            double waitTime = 0;

            while (elapsedTime < waitTime*1000) {
                //can be implemented to change for like, a countdown or something
                elapsedTime = (new Date()).getTime() - startTime;
            }
            startGame = true;
            
            //The frame will not update for some reason until this method has finished
        }


    }

    public void changeBoard(JFrame frame) {
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

    public int runGame(JFrame frame) {
        Player human = new Player(250, 70, frame);

        Game game = new Game(human);
        return game.start();
    }

    public void mainMenuScreen(JFrame frame) {
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
        frame.setSize(frameWidth,frameHeight);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    public void drawGameOverGraphics(int startX, int startY, int boardWidth, int boardHeight, int squareDim, Graphics g) {
        /*g.setColor(Color.black);
        g.fillRect(startX, startY, boardWidth, boardHeight);

        g.setColor(Color.yellow);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Game Over", frameWidth/2, frameHeight/2);*/
        GameOver over = new GameOver();
     
    }
    public int retScore(int score){
        return score;
    }
    public void gameOverScreen(JFrame frame, int score) {
        frame.setSize(frameWidth,frameHeight);
        //frame.setBackground(Color.black);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        Dimension size = new Dimension(frameWidth,frameHeight);
        BufferedImage background = new BufferedImage(size.width, size.height, java.awt.Image.SCALE_SMOOTH);
        Graphics g = background.createGraphics();

        addHighScore(score);

        drawGameOverGraphics(250,70,boardWidth, boardHeight, squareDim, g);
        //GameOver over = new GameOver();
        JLabel backGroundImg = new JLabel();
        backGroundImg.setIcon(new ImageIcon(background.getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH)));;
        Image myImage = background.getScaledInstance(frameWidth, frameHeight, squareDim);



        //frame.setContentPane(new ImagePanel(myImage));
        //frame.setVisible(true);
    }

    private void addHighScore(int score) {

    }

    public static void main(String[] args) {
    	new GameRunner();
	}


}