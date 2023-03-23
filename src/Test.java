import FrontEnd.GameRunner;
import javax.swing.*;
import java.awt.Point;
import java.awt.*;
import FrontEnd.Player;
import FrontEnd.ComputerPlayer;
import BackEnd.Game;

public class Test extends Thread{
    static JFrame frame = new JFrame("Tetris");
    public static void main(String args[]) {
        Test thread = new Test();
        frame.setSize(1800,800);
        frame.setBackground(Color.black);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        thread.start();
        ComputerPlayer computer=  new ComputerPlayer(1200, 70, frame);

        //You would want to get both games, running at the same time
        //Whichever game ends first 

        //Game game = new Game(human);
        //game.start();

        Game game2 = new Game(computer);
        game2.start();

    }
    public void run(){
        Player human = new Player(300,70,frame);
        Game game = new Game(human);
        game.start();
    }
}