package BackEnd;
import FrontEnd.Board;

public class GameRunner {
    public static void playTetris() {
        HumanPlayer human = new HumanPlayer(new Board(0,0));
        ComputerPlayer computer = new ComputerPlayer(new Board(0,0));
        Game game = new Game(human, computer);
        

        while (!game.gameOver()) {
            game.createNewPiece();
        }
        
    }
}