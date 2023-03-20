package BackEnd;
import FrontEnd.Board;

public class Game {
    private boolean gameOver;
    private Board board;
    private Player p1;
    private Player p2;
    public Game(Player player1, Player player2) {
        gameOver = false;
        p1 = player1;
        p2 = player2;
    }

    public static boolean pieceSettled(Board board) {
        
        return false;
    }

    public boolean gameOver() {
        return gameOver;
    }

    public void createNewPiece() {
        p1.getBoard().createNewPiece();
        p2.getBoard().createNewPiece();
    }
    
}