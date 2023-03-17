package BackEnd;
import FrontEnd.Board;

public class Player {
    private boolean automated;
    private Board board;
    Player(Board board) {
        this.board = board;
   }

   public Board getBoard(){
    return board;
   }

   
}
