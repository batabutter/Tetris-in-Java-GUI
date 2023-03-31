package FrontEnd;
import BackEnd.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.*;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Image;
import java.awt.Font;
import java.awt.geom.Line2D;
import java.awt.Rectangle;
import BackEnd.BoardGrid;

public class Board {
    PuzzlePiece currentPiece;
    final static public int boardHeight = 600;
    final static public int boardWidth = 300;
    final static public int squareDim = boardWidth / 10;
    private double dropSpeed;
    private BufferedImage pieces;
    private int startX;
    private int startY;
    private JFrame frame;
    private ArrayList<Line2D> allLines;
    private ArrayList<BufferedImage> filledCells;
    //Array board > 
    private BoardGrid arrBoard;
    private ArrayList<PuzzlePiece> boardPieces;

    public Board(int x, int y, JFrame frame) {
        startX = x;
        startY = y;
        dropSpeed = 1.5;
        this.frame = frame;
        allLines = new ArrayList<Line2D>();
        arrBoard = new BoardGrid(startX, startY);
        filledCells = new ArrayList<BufferedImage>();
        arrBoard = new BoardGrid(startX, startY);
    }
    
    public int getXStart(){
        return startX;
    }

    public int getYStart() {
        return startY;
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

    public void setCurrentPiece(PuzzlePiece temp) {
        currentPiece = temp;
    }

    public PuzzlePiece getCurrentPiece() {
        return currentPiece;
    }

    //This will cause a memory leak (maybe)

    public void update() {


        arrBoard.updateGrid(currentPiece);
        
        //clear the current piece of all of it's data to avoid any memory leaks
        currentPiece.getHitbox().clear();
    }


    public void add(PuzzlePiece piece) {
        currentPiece = piece;
        //For some reason, the piece wouldn't show unless I set it twice.
        //So yeah...
        piece.setX(piece.xStart() + 4*30);
        piece.setY(piece.yStart());
        frame.add(piece.getLabel());
    }

    public PuzzlePiece remove() {

        frame.remove(currentPiece.getLabel());
        PuzzlePiece temp = currentPiece;
        currentPiece = null;
        return temp;
    }

    //We most likely are going to have to rework some of these methods in order to account for different types of collisions

    public void movePieceRight(){
        PuzzlePiece piece = currentPiece;
        PuzzlePiece testColPiece = new PuzzlePiece(currentPiece.xStart(), currentPiece.yStart(), getXStart(), getYStart(), currentPiece.getPieceType());
        testColPiece.setX(currentPiece.getX() + squareDim);
        testColPiece.setY(currentPiece.getY());

        if ((piece.getX()+squareDim) + piece.getWidth() <= startX+Board.boardWidth && arrBoard.validLocationX(testColPiece))
            piece.setX(piece.getX()+squareDim);

    }

    public void movePieceLeft() {
        PuzzlePiece piece = currentPiece;
        PuzzlePiece testColPiece = new PuzzlePiece(currentPiece.xStart(), currentPiece.yStart(), getXStart(), getYStart(), currentPiece.getPieceType());
        testColPiece.setX(currentPiece.getX() - squareDim);
        testColPiece.setY(currentPiece.getY());
        if (piece.getX()-squareDim >= startX && arrBoard.validLocationX(testColPiece))
            piece.setX(piece.getX()-squareDim);
        
    }

    public void movePieceDown() {
        PuzzlePiece piece = currentPiece;

        if ((piece.getY()+squareDim)+piece.getHeight() <= startY+Board.boardHeight)
            piece.setY(piece.getY()+squareDim);

        //currentPiece.getHitbox().printGridLoc();

    }

    //Change to be more simplistic
    public boolean pieceSettled () {
        PuzzlePiece piece = currentPiece;

        PuzzlePiece testColPiece = new PuzzlePiece(piece.xStart(), piece.yStart(), getXStart(), getYStart(), currentPiece.getPieceType());
        testColPiece.setX(currentPiece.getX());
        testColPiece.setY(currentPiece.getY() + squareDim);
        return !arrBoard.validLocationY(testColPiece);

    }

    public int next(int start) {
        return start + squareDim;
    }

    public void rotatePiece() {
        PuzzlePiece piece = currentPiece;
        ImageIcon shape = piece.getShape();

        BufferedImage rotated = rotate(imageIconToBufferedImage(shape), 90.0);
        ImageIcon temp = new ImageIcon(rotated.getScaledInstance(rotated.getWidth(),rotated.getHeight(), java.awt.Image.SCALE_SMOOTH));

        piece.setShape(temp);
    }

    public static BufferedImage imageIconToBufferedImage(ImageIcon icon) {
        BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = bufferedImage.createGraphics();
        icon.paintIcon(null, graphics, 0, 0);
        graphics.dispose();
        return bufferedImage;
    }

    public static BufferedImage rotate(BufferedImage bimg, Double angle) {
	    double sin = Math.abs(Math.sin(Math.toRadians(angle))),
	           cos = Math.abs(Math.cos(Math.toRadians(angle)));
	    int w = bimg.getWidth();
	    int h = bimg.getHeight();
	    int neww = (int) Math.floor(w*cos + h*sin),
	        newh = (int) Math.floor(h*cos + w*sin);
	    BufferedImage rotated = new BufferedImage(neww, newh, bimg.getType());
	    Graphics2D graphic = rotated.createGraphics();
	    graphic.translate((neww-w)/2, (newh-h)/2);
	    graphic.rotate(Math.toRadians(angle), w/2, h/2);
	    graphic.drawRenderedImage(bimg, null);
	    graphic.dispose();
	    return rotated;
	}
    
}