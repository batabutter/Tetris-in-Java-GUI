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
    private int[][] grid;

    public Board(int x, int y, JFrame frame) {
        startX = x;
        startY = y;
        dropSpeed = 1.5;
        this.frame = frame;
        allLines = new ArrayList<Line2D>();
        arrBoard = new BoardGrid(startX, startY);
        filledCells = new ArrayList<BufferedImage>();
        this.grid = new int[20][10];
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

    //This will cause a memory leak

    public void update() {

        ArrayList<Line2D> lines = currentPiece.getHitbox().getPoints();
        for (Line2D temp : lines) {
            allLines.add(new Line2D.Float((float)temp.getX1(), (float)temp.getY1(), (float)temp.getX2(), (float)temp.getY2()));
        }
        //First : Update the 2D Array > 
        int[][] temp = currentPiece.getHitbox().getGrid();
        for (int i = 0; i < temp.length; i++) {
            for (int k = 0; k < temp[0].length; k++) {
                if (temp[i][k] == 1) {
                    grid[i][k] = 1;
                }
            }
        }
        currentPiece.getHitbox().clear();
    }

    public void printGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int k = 0; k < grid[0].length; k++) {
               System.out.print(grid[i][k]+" ");
            }
            System.out.println("\n");
        }
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

    }

    public void movePieceLeft() {
        PuzzlePiece piece = currentPiece;

        if (piece.getX()-squareDim >= startX)
            piece.setX(piece.getX()-squareDim);
        
    }

    public void movePieceDown() {
        PuzzlePiece piece = currentPiece;

        if ((piece.getY()+20)+piece.getHeight() <= startY+Board.boardHeight)
            piece.setY(piece.getY()+squareDim);

    }

    public boolean pieceSettled () {
        PuzzlePiece piece = currentPiece;

        if (allLines.size() > 0) {
            Hitbox futureHit = new Hitbox(piece.getX()+1, piece.getY()+1, piece.getHitbox().getPieceType());
            ArrayList<Line2D> temp = futureHit.getPoints();

            for (int i = 0; i < temp.size(); i ++) {
                for (int k = 0; k < allLines.size(); k++) {
                    if (temp.get(i).intersectsLine(allLines.get(k))) {
                        
                        return true;
                    }
                }
            }
        }

        
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