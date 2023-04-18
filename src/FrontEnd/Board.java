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
    private int startX;
    private int startY;
    private JFrame frame;
    private ArrayList<JLabel> pieceImages;
    //Array board > 
    private BoardGrid arrBoard;
    private ArrayList<PuzzlePiece> boardPieces;
    private int timesRotated;

    public Board(int x, int y, JFrame frame) {
        startX = x;
        startY = y;
        dropSpeed = 1.5;
        this.frame = frame;
        pieceImages = new ArrayList<JLabel>();
        arrBoard = new BoardGrid();
        timesRotated = 0;
    }

    public BoardGrid getBoardGrid() {
        return arrBoard;
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
        frame.remove(currentPiece.getLabel());
        currentPiece = temp;
        frame.add(currentPiece.getLabel());
    }

    public PuzzlePiece getCurrentPiece() {
        return currentPiece;
    }

    //This will cause a memory leak (maybe)

    public void update() {
        arrBoard.updateGrid(currentPiece);
        removePieceImages();
        updateImagesOfBoard();

        //clear the current piece of all of it's data to avoid any memory leaks
        remove();
    }

    private void updateImagesOfBoard() {
        //Create a way here to make split pieces based off of the pieceLabels
        //Then alter the pieceImages
        int[][] grid = arrBoard.getBoard();
        for (int i  = 0; i < grid.length; i++) {
            for (int k = 0; k < grid[0].length; k++) {
                if (grid[i][k] != 0) {
                    add(grid[i][k], i, k);
                }
            }
        }

    }

    public void add(PuzzlePiece piece) {
        currentPiece = piece;
        //For some reason, the piece wouldn't show unless I set it twice.
        //So yeah...
        piece.setX(piece.xStart() + 4*30);
        piece.setY(piece.yStart());
        frame.add(piece.getLabel());
        //currentPiece.getHitbox().printGridLoc();
    }

    public void add(int numColor, int row, int col) {
        String color = ";";
        if (numColor == 1) {
            color = "images/blackBlock.png";
        }

        int newX = startX + (squareDim * col);
        int newY = startY + (squareDim * row);
    
        JLabel temp = new JLabel(new ImageIcon(color));
        Dimension size = temp.getPreferredSize();
        temp.setBounds(newX, newY, size.width, size.height);
        pieceImages.add(temp);
        frame.add(temp);
    }

    public PuzzlePiece remove() {

        frame.remove(currentPiece.getLabel());
        PuzzlePiece temp = currentPiece;
        currentPiece.getHitbox().clear();
        currentPiece = null;
        return temp;
    }

    public void removePieceImages() {
        for (int i =0 ; i < pieceImages.size(); i++) {
            frame.remove(pieceImages.get(i));
        }

        pieceImages.clear();

    }

    //We most likely are going to have to rework some of these methods in order to account for different types of collisions

    public void movePieceRight(){
        PuzzlePiece piece = currentPiece;

        int[][] futureLocations = new int[4][2];
        int[][] currentLocations = piece.getHitbox().getGridLocations();
        int[][] grid = arrBoard.getBoard();

        for (int i = 0 ; i < futureLocations.length; i++) {
            futureLocations[i][0] = currentLocations[i][0] + 1;
            futureLocations[i][1] = currentLocations[i][1];
        }
        /* 
        for (int n = 0; n < futureLocations.length; n++) {
            System.out.println(n + ": "+"("+futureLocations[n][0]+", "+futureLocations[n][1]+")");
        }
        */

        boolean cont = true;
        for (int k = 0; k < futureLocations.length; k++) {
            if ((futureLocations[k][0] >= arrBoard.getWidth()) || (grid[futureLocations[k][1]][futureLocations[k][0]] == 1)) {
                cont = false;
                //System.out.println("Invalid!");
            }
        }

        if (cont)
            piece.setX(piece.getX()+squareDim);

    }

    public void movePieceLeft() {
        PuzzlePiece piece = currentPiece;
        PuzzlePiece testColPiece = new PuzzlePiece(currentPiece.xStart(), currentPiece.yStart(), getXStart(), getYStart(), currentPiece.getPieceType());
        testColPiece.setX(currentPiece.getX() - squareDim);
        testColPiece.setY(currentPiece.getY());
        if (piece.getX()-squareDim >= startX && arrBoard.validLocationX(testColPiece))
            piece.setX(piece.getX()-squareDim);
        
        //currentPiece.getHitbox().printGridLoc();
    }

    public void movePieceDown() {
        PuzzlePiece piece = currentPiece;

        if (!pieceSettled())
            if ((piece.getY()+squareDim)+piece.getHeight() <= startY+Board.boardHeight)
                piece.setY(piece.getY()+squareDim);

        //currentPiece.getHitbox().printGridLoc();
        

    }

    //Change to be more simplistic
    public boolean pieceSettled () {
        PuzzlePiece piece = currentPiece;

        int[][] futureLocations = new int[4][2];
        int[][] currentLocations = piece.getHitbox().getGridLocations();
        int[][] grid = arrBoard.getBoard();

        for (int i = 0 ; i < futureLocations.length; i++) {
            futureLocations[i][0] = currentLocations[i][0];
            futureLocations[i][1] = currentLocations[i][1] + 1;
        }
        
        for (int k = 0; k < futureLocations.length; k++) {
            if (futureLocations[k][1] >= arrBoard.getHeight()) {
                return true;
            }

            if (grid[futureLocations[k][1]][futureLocations[k][0]] == 1) {
                return true;
            }
        }
        return false;

    }

    public void setTimesRotated(int n) {
        timesRotated = n;
    }

    public int next(int start) {
        return start + squareDim;
    }

    public void rotatePiece() {
        PuzzlePiece piece = currentPiece;
        PuzzlePiece tempPiece = arrBoard.rotatePiece(currentPiece, timesRotated);

        if (tempPiece != null) {
            timesRotated++;
            ImageIcon shape = piece.getShape();

            BufferedImage rotated = rotate(imageIconToBufferedImage(shape), 90.0);
            ImageIcon temp = new ImageIcon(rotated.getScaledInstance(rotated.getWidth(),rotated.getHeight(), java.awt.Image.SCALE_SMOOTH));

            //This causes a memory leak, I need ot fix once I get it working
            piece.setShape(temp);
            piece.getHitbox().setGridLoc(tempPiece.getHitbox().getGridLocations());
            piece.getHitbox().setSpecialConditions(tempPiece.getHitbox().getSpecialLocations());
            
            /* 
            System.out.println("Board > ");
            currentPiece.getHitbox().printGridLoc();
            System.out.println("-----------------------------------------------------------");
            
            */
        }

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