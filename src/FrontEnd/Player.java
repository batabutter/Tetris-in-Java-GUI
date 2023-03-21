package FrontEnd;
import javax.swing.*;

public class Player {
    private boolean automated;
    private Board board;
    private JFrame frame;
    
    Player(Board board) {
        this.board = board;
   }

   public Board getBoard(){
    return board;
   }

   
}
