package FrontEnd;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Point;
import BackEnd.PuzzlePiece;
import java.awt.*;

import javax.swing.*;

public class HumanPlayer extends Player{
    //Each player will be given a frame, and they will send that to the board.
    //Inner calculations are done here
    private JFrame frame;
    private int x;
    private int y;
    private Point temp;

    public HumanPlayer(JFrame frame) {
        super(frame);
        this.frame = frame;
        temp = null;
    }

}
