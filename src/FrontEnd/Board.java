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
    final static public int squareDim = boardWidth / 10;
    private double dropSpeed;
    private int startX;
    private int startY;
    private JFrame frame;

    //It will tell the board where it should create the piece
    //Paintcomponenet here
    public Board(int x, int y, JFrame frame) {
        startX = x;
        startY = y;
        dropSpeed = 1.5;
        this.frame = frame;
    }

    public JFrame getFrame() {
        return frame;
    }

    public double getDropSpeed() {
        return dropSpeed;
    }

    //This might also need to be changed depending on how Tetris changes the speed depending on the level
    public void setDropSpeed(double speed) {
        dropSpeed = speed;
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
            
            g.fillRect(startX, startY, boardWidth, boardHeight);
            g.drawRect(startX-20, startY-20, (int) boardWidth + 2*(20), boardHeight + 2*(20));

            int squareDim = boardWidth / 10;
            g.setColor(new Color(112,112,112));
            
            for (int i = 0; i <= boardWidth; i= i+squareDim) {
                g.drawLine(startX+i, startY, startX+i, startY + boardHeight);
            }

            for (int i = 0; i <= boardHeight; i+= squareDim) {
                g.drawLine(startX, startY+i, startX+boardWidth, startY + i);
            }
        }
    }

    //We most likely are going to have to rework some of these methods in order to account for different types of collisions

    public void movePieceRight(){
        PuzzlePiece piece = currentPiece;

        if ((piece.getX()+20) + piece.getWidth() <= startX+Board.boardWidth)
            piece.setX(piece.getX()+squareDim);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void movePieceLeft() {
        PuzzlePiece piece = currentPiece;

        if (piece.getX()-squareDim >= startX)
            piece.setX(piece.getX()-squareDim);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    //Maybe change where this calculation is done
    public void movePieceDown() {
        PuzzlePiece piece = currentPiece;

        if ((piece.getY()+20)+piece.getHeight() <= startY+Board.boardHeight)
            piece.setY(piece.getY()+squareDim);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void rotatePiece() {
        PuzzlePiece piece = currentPiece;

        SwingUtilities.updateComponentTreeUI(frame);
    }
    
}