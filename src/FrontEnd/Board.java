package FrontEnd;
import BackEnd.*;
import javax.swing.*;
import java.awt.Point;


public class Board {
    PuzzlePiece currentPiece;
    //How much the piece will move down
    final int verticalMovement = 0;
    private int startX;
    private int startY;

    //It will tell the board where it should create the piece
    //Paintcomponenet here
    public Board(int x, int y, JFrame frame) {
        startX = x;
        startY = y;

    }

    public void setCurrentPiece(PuzzlePiece piece) {
        currentPiece = piece;
    }

    
}