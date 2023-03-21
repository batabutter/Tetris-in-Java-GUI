package FrontEnd;
import BackEnd.*;
import javax.swing.*;
import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics;


public class Board {
    PuzzlePiece currentPiece;
    //How much the piece will move down
    final int verticalMovement = 0;
    private int startX;
    private int startY;
    private JFrame frame;

    //It will tell the board where it should create the piece
    //Paintcomponenet here
    public Board(int x, int y, JFrame frame) {
        startX = x;
        startY = y;
        this.frame = frame;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void showBoard() {
        DrawBackGround temp = new DrawBackGround();
        frame.add(temp);
        frame.setVisible(true);
    }

    public void setCurrentPiece(PuzzlePiece piece) {
        currentPiece = piece;
    }

    class DrawBackGround extends JPanel {

        
        public void paintComponent(Graphics g) {
            g.fillRect(startX, startY, (int) startX+200, 400);
        }
    }
    
}