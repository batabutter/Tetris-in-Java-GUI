package FrontEnd;
import BackEnd.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;


public class Board {
    PuzzlePiece currentPiece;
    //How much the piece will move down
    final int verticalMovement = 0;
    final static public int boardHeight = 600;
    final static public int boardWidth = 300;
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
            
            g.fillRect(startX, startY, (int) startX+boardWidth, boardHeight);
            g.setColor(new Color(176,174,173));
            g.drawRect(startX-20, startY-20, (int) startX+boardWidth + 2*(20), boardHeight + 2*(20));
        }
    }

    public void movePieceRight(){
        PuzzlePiece piece = currentPiece;

        if (piece.getX()+20 <= Board.boardWidth)
            piece.setX(piece.getX()+20);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void movePieceLeft() {
        PuzzlePiece piece = currentPiece;

        if (piece.getX()-20 >= startX)
            piece.setX(piece.getX()-20);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    //Maybe change where this calculation is done
    public void movePieceDown() {
        PuzzlePiece piece = currentPiece;

        if (piece.getY()+20 <= Board.boardHeight)
            piece.setY(piece.getY()+20);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void rotatePiece() {
        PuzzlePiece piece = currentPiece;

        SwingUtilities.updateComponentTreeUI(frame);
    }
    
}