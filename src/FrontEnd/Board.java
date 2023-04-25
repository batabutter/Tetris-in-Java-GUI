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
    private int dropSpeed;
    private int settledFrames;
    private int startX;
    private int startY;
    private JFrame frame;
    private ArrayList<JLabel> pieceImages;
    //Array board > 
    private BoardGrid arrBoard;
    private ArrayList<PuzzlePiece> boardPieces;
    private int timesRotated;
    private int score;
    private PuzzlePiece nextPiece;
    private PuzzlePiece hold;
    private PuzzlePiece projPiece;
    private JLabel visScore;
    private JLabel visLevel;
    private int level;
    private int holdCount;

    public Board(int x, int y, JFrame frame) {
        startX = x;
        startY = y;
        dropSpeed = 60;
        this.frame = frame;
        pieceImages = new ArrayList<JLabel>();
        arrBoard = new BoardGrid();
        timesRotated = 0;
        score = 0;
        level = 1;
        settledFrames = 30;
        nextPiece = null;
        hold = null;
        projPiece = null;
        holdCount = 0;

        visScore = new JLabel();
        visLevel = new JLabel();
        visScore.setText(String.valueOf(score));
        visLevel.setText("Level: "+String.valueOf(level));
        visScore.setFont(new Font("Serif", Font.PLAIN, 24));
        visLevel.setFont(new Font("Serif", Font.PLAIN, 24));
        visScore.setBounds(boardWidth + 90+startX, 350 + startY, 100, 100);
        visLevel.setBounds(boardWidth + 90+startX, 400 + startY, 100, 100);
        frame.add(visScore);
        frame.add(visLevel);
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

    public int getDropSpeed() {
        return dropSpeed;
    }

    public int getSettledFrames() {
        return settledFrames;
    }

    public int nextPiece() {
        if (nextPiece == null)
            return -1;
        
        return nextPiece.getPieceType();
    }

    public void setNextPiece(PuzzlePiece piece) {
        nextPiece = piece;
    }

    //This might also need to be changed depending on how Tetris changes the speed depending on the level
    public void setDropSpeed(int speed) {
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
        frame.remove(nextPiece.getLabel());
        holdCount = 0;
        timesRotated = 0;

        ArrayList<Integer> linesCleared = arrBoard.getPrevLinesCleared();

        for (int i = 0; i < linesCleared.size(); i++) {
            if (linesCleared.get(i) == 1) {
                addScore(100 * level);
                System.out.println("You cleared 1 line");
            } else if (linesCleared.get(i) == 2) {
                addScore(300 * level);
                System.out.println("You cleared 2 lines in a row");
            } else if (linesCleared.get(i) == 3) {
                addScore(500 * level);
                System.out.println("You cleared 3 lines in a row");
            } else if (linesCleared.get(i) == 4) {
                addScore(800 * level);
                System.out.println("You cleared 4 lines in a row");
            }
        }

        //clear the current piece of all of it's data to avoid any memory leaks
        remove();
        arrBoard.printGrid();
    }

    public void addScore(int x) {
        score = score + x;
        visScore.setText(String.valueOf(score));
    }

    public int getScore() {
        return score;
    }

    public void increaseLevel(int x) {
        level++;
        visLevel.setText("Level: "+String.valueOf(level));
    }

    public int getLevel() {
        return level;
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

    public void addProjPiece(PuzzlePiece piece) {
        //For some reason, the piece wouldn't show unless I set it twice.
        //So yeah...
        if (projPiece != null) {
            frame.remove(projPiece.getLabel());
            projPiece.getHitbox().clear();
        }
        projPiece = piece;
        projPiece.setX(piece.getX());
        projPiece.setY(piece.getY());

        for (int i = 0; i < timesRotated; i++) {
            rotatePiece(projPiece, false);
        }

        frame.add(projPiece.getLabel());
        //currentPiece.getHitbox().printGridLoc();
    }

    public void removeProjPiece() {
        if (this.projPiece != null) {
            frame.remove(projPiece.getLabel());
            projPiece.getHitbox().clear();
            projPiece = null;
        }

    }

    public void addNextPiece(PuzzlePiece piece) {
        if (nextPiece != null) {
            frame.remove(nextPiece.getLabel());
        }

        setNextPiece(piece);
        //For some reason, the piece wouldn't show unless I set it twice.
        //So yeah...
        nextPiece.setX(boardWidth + 90+startX);
        nextPiece.setY(140);
        frame.add(nextPiece.getLabel());
        //currentPiece.getHitbox().printGridLoc();
    }


    public void add(int numColor, int row, int col) {
        String color = ";";
        //System.out.println("Num color > "+numColor);

        switch(numColor) {
            case 1:
            color = "images/blackBlock.png";
            break;

            case 2:
            color = "images/yellowBlock.png";
            break; 

            case 3: 
            color = "images/tealBlock.png";
            break;

            case 4: 
            color = "images/redBlock.png";
            break;

            case 5: 
            color = "images/purpleBlock.png";
            break;

            case 6: 
            color = "images/orangeBlock.png";
            break;

            case 7: 
            color = "images/blueBlock.png";
            break;

            case 8: 
            color = "images/greenBlock.png";
            break;
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

        removeProjPiece();
        timesRotated = 0;

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
            if ((futureLocations[k][0] >= arrBoard.getWidth()) || (grid[futureLocations[k][1]][futureLocations[k][0]] != 0)) {
                cont = false;
                //System.out.println("Invalid!");
            }
        }

        if (cont)
            piece.setX(piece.getX()+squareDim);

    }

    public void movePieceLeft() {
        PuzzlePiece piece = currentPiece;

        int[][] futureLocations = new int[4][2];
        int[][] currentLocations = piece.getHitbox().getGridLocations();
        int[][] grid = arrBoard.getBoard();

        for (int i = 0 ; i < futureLocations.length; i++) {
            futureLocations[i][0] = currentLocations[i][0] - 1;
            futureLocations[i][1] = currentLocations[i][1];
        }
        /* 
        for (int n = 0; n < futureLocations.length; n++) {
            System.out.println(n + ": "+"("+futureLocations[n][0]+", "+futureLocations[n][1]+")");
        }
        */

        boolean cont = true;
        for (int k = 0; k < futureLocations.length; k++) {
            if ((futureLocations[k][0] < 0) || (grid[futureLocations[k][1]][futureLocations[k][0]] != 0)) {
                cont = false;
                //System.out.println("Invalid!");
            }
        }

        if (cont)
            piece.setX(piece.getX()-squareDim);
        
        //currentPiece.getHitbox().printGridLoc();
    }

    public void movePieceDown(PuzzlePiece piece) {

        if (!pieceSettled(piece)) {
            if ((piece.getY()+squareDim)+piece.getHeight() <= startY+Board.boardHeight) {
                piece.setY(piece.getY()+squareDim);
                addScore(1);
            }
        }
        //currentPiece.getHitbox().printGridLoc();
    }

    public void holdPiece() {
        if (holdCount == 0) {
            if (hold == null) {
                hold = new PuzzlePiece(startX, startY, getXStart(), getYStart(), currentPiece.getPieceType(), false);
                hold.setX(startX -180);
                hold.setY(140);
                remove();
                frame.add(hold.getLabel());
                PuzzlePiece newPiece = new PuzzlePiece(startX, startY, getXStart(), getYStart(), nextPiece(), false);
                add(new PuzzlePiece(startX, startY, getXStart(), getYStart(), nextPiece(), false));
                timesRotated = 0;
                addProjPiece(new PuzzlePiece(startX, startY, getXStart(), getYStart(), newPiece.getPieceType(), true));
                addNextPiece(new PuzzlePiece(startX, startY, getXStart(), getYStart(), -1, false));
            } else {
                int pieceTypeHold = hold.getPieceType();
                frame.remove(hold.getLabel());
                hold = new PuzzlePiece(startX, startY, getXStart(), getYStart(), currentPiece.getPieceType(), false);
                hold.setX(startX -180);
                hold.setY(140);
                frame.add(hold.getLabel());
                remove();
                timesRotated = 0;
                addProjPiece(new PuzzlePiece(startX, startY, getXStart(), getYStart(), hold.getPieceType(), true));
                add(new PuzzlePiece(startX, startY, getXStart(), getYStart(), pieceTypeHold, false));
            }
            SwingUtilities.updateComponentTreeUI(frame);
            holdCount++;
        }
    }

    public void settlePiece(PuzzlePiece piece) {
        while (!pieceSettled(piece)) {
            movePieceDown(piece);
            addScore(2);
        }

        update();
    }



    //Change to be more simplistic
    public boolean pieceSettled (PuzzlePiece piece) {

        if (piece != null) {
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

                if (grid[futureLocations[k][1]][futureLocations[k][0]] != 0) {
                    return true;
                }
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
        PuzzlePiece tempPiece = arrBoard.rotatePiece(currentPiece, timesRotated, true);

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

    public void rotatePiece(PuzzlePiece piece, boolean checkCol) {
            ImageIcon shape = piece.getShape();
            PuzzlePiece tempPiece = arrBoard.rotatePiece(piece, timesRotated, checkCol);

            BufferedImage rotated = rotate(imageIconToBufferedImage(shape), 90.0);
            ImageIcon temp = new ImageIcon(rotated.getScaledInstance(rotated.getWidth(),rotated.getHeight(), java.awt.Image.SCALE_SMOOTH));

            //This causes a memory leak, I need ot fix once I get it working
            piece.setShape(temp);
            piece.getHitbox().setGridLoc(tempPiece.getHitbox().getGridLocations());
            piece.getHitbox().setSpecialConditions(tempPiece.getHitbox().getSpecialLocations());

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