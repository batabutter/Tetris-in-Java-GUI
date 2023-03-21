package BackEnd;
import FrontEnd.Board;
import FrontEnd.Player;

public class Game {
    private boolean gameOver;
    private Player p;
    private Board board;
    public Game(Player player) {
        gameOver = false;
        p = player;
        board = player.getBoard();
    }

    //This method will be given a current version of whatever board
    //It will then anaylize the board to see if it is completely "settled"
    //If it is, it will return a boolean
    public static boolean pieceSettled(Board board) {
        
        return false;
    }

    public boolean gameOver() {
        return gameOver;
    }

    public int start() {

        while (!gameOver()) {
            p.createNewPiece();
        }   

        return 0;
    }

    public Player getPlayer() {
        return p;
    }
    
}