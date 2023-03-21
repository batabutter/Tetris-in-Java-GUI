package FrontEnd;
import BackEnd.Game;
import BackEnd.PuzzlePiece;
import javax.swing.*;
import java.awt.Point;

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

   public Point movePiece(PuzzlePiece piece) {
    return null;
   }

   //Creates new piece at given location
   public void createNewPiece() {
    PuzzlePiece piece = new PuzzlePiece();
    Point temp;

    while (!Game.pieceSettled(board)) {
        temp = movePiece(piece);
    }

}
}
