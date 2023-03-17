package FrontEnd;
import BackEnd.*;


public class Board {
    PuzzlePiece currentPiece;
    //How much the piece will move down
    final int verticalMovement = 0;
    private int startX;
    private int startY;

    //It will tell the board where it should create the piece
    public Board(int x, int y) {
        startX = x;
        startY = y;
    }

    //Will be given a start location to create the piece in
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
    //It will check i
    public void movePiece(int x) {
        
    }
}