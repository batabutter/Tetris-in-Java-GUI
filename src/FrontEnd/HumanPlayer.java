package FrontEnd;
import java.awt.event.KeyListener;

import javax.swing.*;

public class HumanPlayer extends Player{
    //Each player will be given a frame, and they will send that to the board.
    //Inner calculations are done here
    private JFrame frame;
    public HumanPlayer(JFrame frame) {
        super(new Board(100,100, frame));
        this.frame = frame;
    }

    class KeyboardControls implements KeyListener {

    }
}
