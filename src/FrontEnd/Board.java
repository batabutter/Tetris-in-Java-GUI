package FrontEnd;
import BackEnd.*;
import javax.swing.*;
import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics;


public class Board {
    JLabel currentPiece;
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

    public void setCurrentPiece(JLabel temp) {
        currentPiece = temp;
    }

    //Change
    public void add(PuzzlePiece piece) {
        JLabel temp = new JLabel(piece.getShape());
        currentPiece = temp;
        temp.setBounds(piece.getX(),piece.getY(), piece.getShape().getIconWidth(), piece.getShape().getIconHeight());
        frame.add(temp);

    }

    public void remove() {

        frame.remove(currentPiece);
        currentPiece = null;
    }

    class DrawBackGround extends JPanel {

        
        public void paintComponent(Graphics g) {
            g.fillRect(startX, startY, (int) startX+200, 400);
        }
    }
    
}