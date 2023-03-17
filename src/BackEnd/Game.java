package BackEnd;
import FrontEnd.Board;

public class Game {
    private boolean gameOver;
    private Board board;
    public Game() {
        gameOver = false;
        board = new Board();
    }

    public static boolean pieceSettled(Board board) {
        
        return false;
    }

    public boolean gameOver() {
        return gameOver;
    }

    public void createNewPiece() {
        board.createNewPiece();
    }
   
}