package FrontEnd;
import javax.swing.*;

public class HumanPlayer extends Player{
    //Each player will be given a frame, and they will send that to the board.
    //Inner calculations are done here
    public HumanPlayer(JFrame frame) {
        super(new Board(0,0, frame));
    }
}
