package FrontEnd;
import javax.swing.*;

import BackEnd.Game;

public class GameRunner {
    public static void playTetris() {
        //This is the frame work for how the game will be structured
        //My goal is to integrate MainMenu last.

        JFrame frame = new JFrame("Tetris");
        frame.setSize(800,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Player human = new Player(100,100,frame);

        //You would want to get both games, running at the same time
        //Whichever game ends first 
        Game game = new Game(human);
        
        //This will be changed to run both games at once using threads.
        //The next thread should be run in this class
        int score; 
        while (!game.gameOver()) {
            score = game.start();
        }
        

    }
}