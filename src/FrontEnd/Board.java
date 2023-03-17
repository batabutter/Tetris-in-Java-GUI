package FrontEnd;
import BackEnd.*;


public class Board {
    PuzzlePiece currentPiece;
    //How much the piece will move down
    final int verticalMovement = 0;

    public void createNewPiece() {
        PuzzlePiece piece = new PuzzlePiece();

        while (!Game.pieceSettled(this)) {
            movePiece(0);
        }

    }
    public void setCurrentPiece(PuzzlePiece piece) {
        currentPiece = piece;
    }

    //x is the horizontal movement
    public void movePiece(int x) {

    }
}