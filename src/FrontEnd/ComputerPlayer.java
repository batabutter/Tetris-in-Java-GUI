package FrontEnd;
import javax.swing.*;

public class ComputerPlayer extends Player{
    public ComputerPlayer(JFrame frame) {

        super(new Board(0,0,frame));
    }
}