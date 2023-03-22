import FrontEnd.GameRunner;
import javax.swing.*;
import java.awt.Point;
import FrontEnd.Player;
import BackEnd.Game;

public class Test {
    public static void main(String args[]) {
        JFrame frame = new JFrame("Tetris");
        frame.setSize(1000,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Player human = new Player(frame);

        //You would want to get both games, running at the same time
        //Whichever game ends first 
        Game game = new Game(human);
        game.start();

    }
}