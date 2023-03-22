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

    public void setCurrentPiece(PuzzlePiece temp) {
        currentPiece = temp;
    }

    public PuzzlePiece getCurrentPiece() {
        return currentPiece;
    }

    //Change
    public void add(PuzzlePiece piece) {
        currentPiece = piece;
        JLabel temp = piece.getLabel();
        piece.setX(startX);
        piece.setY(startY);
        frame.add(temp);
        System.out.println(temp.getX());
    }

    public PuzzlePiece remove() {

        frame.remove(currentPiece.getLabel());
        PuzzlePiece temp = currentPiece;
        currentPiece = null;
        return temp;
    }

    class DrawBackGround extends JPanel {

        
        public void paintComponent(Graphics g) {
            g.fillRect(startX, startY, (int) startX+300, 600);
        }
    }
    
}