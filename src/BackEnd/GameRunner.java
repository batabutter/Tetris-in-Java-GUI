package BackEnd;

public class GameRunner {
    public static void playTetris() {
        Game game = new Game();

        while (!game.gameOver()) {
            game.createNewPiece();
        }
        
    }
}