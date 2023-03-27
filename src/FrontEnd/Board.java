package FrontEnd;
import BackEnd.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Image;
import java.awt.Font;

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

    //Change this to be an image
    //Keep in mind: scalesmooth could cause some issues
    //the background should onnly be added once

    public void setCurrentPiece(PuzzlePiece temp) {
        currentPiece = temp;
    }

    public PuzzlePiece getCurrentPiece() {
        return currentPiece;
    }

    //Change
    public void add(PuzzlePiece piece) {
        currentPiece = piece;
        piece.setX(startX);
        piece.setY(startY);
        //piece.getLabel().setBounds(startX, startY, piece.getWidth(), piece.getHeight());
        frame.add(piece.getLabel());
        //SwingUtilities.updateComponentTreeUI(frame);
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

    public void movePieceDown() {
        PuzzlePiece piece = currentPiece;

        if ((piece.getY()+20)+piece.getHeight() <= startY+Board.boardHeight)
            piece.setY(piece.getY()+squareDim);

        SwingUtilities.updateComponentTreeUI(frame);
    }

    public boolean pieceSettled () {
        PuzzlePiece piece = currentPiece;
        if (((piece.getY()+20)+piece.getHeight() <= startY+Board.boardHeight)) {
            return false;
        }
        return true;
    }

    public void rotatePiece() {
        PuzzlePiece piece = currentPiece;
        ImageIcon shape = piece.getShape();

        BufferedImage rotated = rotate(imageIconToBufferedImage(shape), 90.0);
        ImageIcon temp = new ImageIcon(rotated.getScaledInstance(rotated.getWidth(),rotated.getHeight(), java.awt.Image.SCALE_SMOOTH));

        piece.setShape(temp);
        System.out.println("Roating to ("+piece.getX()+", "+piece.getY()+")");
        //SwingUtilities.updateComponentTreeUI(frame);
    }

    public static BufferedImage imageIconToBufferedImage(ImageIcon icon) {
        BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = bufferedImage.createGraphics();
        icon.paintIcon(null, graphics, 0, 0);
        graphics.dispose();//from   w  ww.j a  va  2  s.  co m
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