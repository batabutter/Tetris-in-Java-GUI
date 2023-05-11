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
    private int numberOfLinesClearedOnLevel;
    //CurrentPiece Data

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
        numberOfLinesClearedOnLevel = 0;
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
    public void setDropSpeed(double speed) {
        dropSpeed = (int) speed;
    }



    public PuzzlePiece getCurrentPiece() {
        return currentPiece;
    }

    //This will cause a memory leak (maybe)

    public void update() {
        arrBoard.updateGrid(currentPiece);
        removePieceImages();
        updateImagesOfBoard();
        holdCount = 0;
        timesRotated = 0;
        int tempCleared = 0;


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
           numberOfLinesClearedOnLevel = numberOfLinesClearedOnLevel + linesCleared.get(i);

           if (numberOfLinesClearedOnLevel >= level * 10) {
            numberOfLinesClearedOnLevel = 0;
            increaseLevel();
            if (level < 14) {
                setDropSpeed(Math.pow((0.8-((level-1)*0.007)), level-1) * 60);
                System.out.println("Drop speed is now > "+getDropSpeed());
            }
           }

        }

        //clear the current piece of all of it's data to avoid any memory leaks
        remove();
        //arrBoard.printGrid();
    }

    public void addScore(int x) {
        score = score + x;
        visScore.setText(String.valueOf(score));
    }

    public int getScore() {
        return score;
    }

    public void increaseLevel() {
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
                    add(grid[i][k], i, k, false, currentPiece);
                }
            }
        }

    }

    public void add(PuzzlePiece piece) {
        currentPiece = piece;
        //For some reason, the piece wouldn't show unless I set it twice.
        //So yeah...
        if (piece.getPieceType() != 1) {
            piece.setY(piece.yStart());
        } else {
            piece.setY(piece.yStart() + squareDim);
        }
        
        if (piece.getPieceType() == 3 || piece.getPieceType() == 6) {
            piece.setX(piece.xStart() + 4*30);
        } else if (piece.getPieceType() == 4) {
            piece.setX(piece.xStart() + 5*30);
        } else {
            piece.setX(piece.xStart() + 3*30);
        }

        System.out.println("Piece type > "+piece.getPieceType());
        addPiece(piece);
        currentPiece.getHitbox().printGridLoc();
    }

    public void addProjPiece(PuzzlePiece piece) {
        //For some reason, the piece wouldn't show unless I set it twice.
        //So yeah...
        System.out.println("Proj piece > ");
        if (projPiece != null) {
            System.out.println("Removing projPiece");
            removeProjPiece();
        }

        System.out.println("piece is at > "+piece.getX()+", "+piece.getY());
        projPiece = piece;
        for (int i = 0; i < timesRotated; i++) {
            rotatePiece(projPiece, false);
        }
        projPiece.setX(piece.getX());
        projPiece.setY(piece.getY());
        System.out.println("proj piece is at > "+projPiece.getX()+", "+projPiece.getY());
        addPiece(projPiece);

        //addPiece(projPiece);
        projPiece.getHitbox().printGridLoc();
    }

    public void removeProjPiece() {
        if (this.projPiece != null) {
            removePiece(projPiece);
            projPiece.getHitbox().clear();
            projPiece = null;
        }

    }

    public PuzzlePiece getProjPiece() {
        return projPiece
;    }

    public void addNextPiece(PuzzlePiece piece) {
        if (nextPiece != null) {
            frame.remove(nextPiece.getLabel());
        }

        setNextPiece(piece);
        //For some reason, the piece wouldn't show unless I set it twice.
        //So yeah...
        nextPiece.setXLabel(boardWidth + 90+startX);
        nextPiece.setYLabel(140);
        frame.add(nextPiece.getLabel());
        //currentPiece.getHitbox().printGridLoc();
    }


    public void add(int numColor, int row, int col, boolean addToPieceLabels, PuzzlePiece piece) {
        String color = ";";
        //System.out.println("Num color > "+numColor);
        ArrayList<JLabel> currentPieceLabels = piece.getPieceImages();

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

            case 9: 
            color = "images/yellowTrans.png";
            break;

            case 10: 
            color = "images/tealTrans.png";
            break;

            case 11: 
            color = "images/redTrans.png";
            break;
            
            case 12: 
            color = "images/purpleTrans.png";
            break;

            case 13: 
            color = "images/orangeTrans.png";
            break;

            case 14: 
            color = "images/blueTrans.png";
            break;

            case 15: 
            color = "images/greenTrans.png";
            break;
        }
        int newX = startX + (squareDim * col);
        int newY = startY + (squareDim * row);
    
        JLabel temp = new JLabel(new ImageIcon(color));
        Dimension size = temp.getPreferredSize();
        temp.setBounds(newX, newY, size.width, size.height);
        frame.add(temp);

        if (addToPieceLabels) {
            currentPieceLabels.add(temp);
        } else {
            pieceImages.add(temp);
        }
    }

    public PuzzlePiece remove() {

        PuzzlePiece temp = currentPiece;
        currentPiece.getHitbox().clear();
        removePiece(currentPiece);
        currentPiece = null;

        removePiece(projPiece);
        timesRotated = 0;

        return temp;
    }

    public void removePiece(PuzzlePiece piece) {
        if (piece != null) {
            ArrayList<JLabel> currentPieceLabels = piece.getPieceImages();
            for (int i = 0; i < currentPieceLabels.size(); i++) {
                frame.remove(currentPieceLabels.get(i));
            }
            currentPieceLabels.clear();
        }

    }

    public void addPiece(PuzzlePiece piece) {
        int[][] locations = piece.getHitbox().getGridLocations();   
        for (int i = 0; i < locations.length; i++) {
            int row = locations[i][1];
            int col = locations[i][0];

            add(piece.getColor(), row, col, true, piece);
        }
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

        if (cont) {
            removePiece(piece);
            piece.setX(piece.getX()+squareDim);
            addPiece(piece);
            //SwingUtilities.updateComponentTreeUI(frame);
        }

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
            }
        }

        if (cont) {
            removePiece(piece);
            //System.out.println("Piece is at > "+piece.getX()+", "+piece.getY());
            piece.setX(piece.getX()-squareDim);
            //System.out.println("Piece is now at > "+piece.getX()+", "+piece.getY());
            addPiece(piece);
            //SwingUtilities.updateComponentTreeUI(frame);
        }
        
        
        //currentPiece.getHitbox().printGridLoc();
    }

    public void movePieceDown(PuzzlePiece piece) {
        int[][] futureLocations = new int[4][2];
        int[][] currentLocations = piece.getHitbox().getGridLocations();

        for (int i = 0 ; i < futureLocations.length; i++) {
            futureLocations[i][0] = currentLocations[i][0];
            futureLocations[i][1] = currentLocations[i][1]+1;
        }
        
        /* 
        for (int n = 0; n < futureLocations.length; n++) {
            System.out.println(n + ": "+"("+futureLocations[n][0]+", "+futureLocations[n][1]+")");
        }
        */

        boolean cont = true;
        for (int k = 0; k < futureLocations.length; k++) {
            if ((futureLocations[k][1] >= arrBoard.getHeight())) {
                cont = false;
            }
        }

        if (cont) {
            removePiece(piece);
            piece.setY(piece.getY()+squareDim);
            addPiece(piece);
            //SwingUtilities.updateComponentTreeUI(frame);
        }
        //piece.getHitbox().printGridLoc();
    }

    public void holdPiece() {
        if (holdCount == 0) {
            if (hold == null) {
                hold = new PuzzlePiece(startX, startY, getXStart(), getYStart(), currentPiece.getPieceType(), false);
                hold.setXLabel(startX -180);
                hold.setYLabel(140);
                remove();
                frame.add(hold.getLabel());

                PuzzlePiece newPiece = new PuzzlePiece(startX, startY, getXStart(), getYStart(), nextPiece(), false);
                add(newPiece);
                timesRotated = 0;

                addProjPiece(new PuzzlePiece(newPiece.getX(), newPiece.getY(), startX, startY, newPiece.getPieceType(), true));
                addNextPiece(new PuzzlePiece(startX, startY, getXStart(), getYStart(), -1, false));
            } else {
                int pieceTypeHold = hold.getPieceType();
                frame.remove(hold.getLabel());
                hold = new PuzzlePiece(startX, startY, getXStart(), getYStart(), currentPiece.getPieceType(), false);
                hold.setXLabel(startX -180);
                hold.setYLabel(140);
                frame.add(hold.getLabel());
                remove();
                timesRotated = 0;

                PuzzlePiece newPiece = new PuzzlePiece(startX, startY, getXStart(), getYStart(), pieceTypeHold, false);
                add(newPiece);
                timesRotated = 0;

                addProjPiece(new PuzzlePiece(newPiece.getX(), newPiece.getY(), startX, startY, pieceTypeHold, true));

            }
            //SwingUtilities.updateComponentTreeUI(frame);
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
            //This causes a memory leak, I need ot fix once I get it working
            removePiece(piece);
            piece.getHitbox().setGridLoc(tempPiece.getHitbox().getGridLocations());
            //currentPiece.getHitbox().printGridLoc();
            
            piece.setX(tempPiece.getX());
            piece.setY(tempPiece.getY());

            if (timesRotated == 4)
                timesRotated = 0;

            addPiece(piece);
            //SwingUtilities.updateComponentTreeUI(frame);
            
            //currentPiece.getHitbox().printGridLoc();

            
            /* 
            System.out.println("Board > ");
            currentPiece.getHitbox().printGridLoc();
            System.out.println("-----------------------------------------------------------");
            */
        }

    }

    public void rotatePiece(PuzzlePiece piece, boolean checkCol) {
            System.out.println("Rotating the projPiece > ");
            PuzzlePiece tempPiece = arrBoard.rotatePiece(piece, timesRotated, checkCol);
            if (tempPiece != null) {
                //This causes a memory leak, I need ot fix once I get it working
                System.out.println("Test piece at > "+"("+tempPiece.getX()+", "+tempPiece.getY()+")");
                System.out.println("piece at > "+"("+piece.getX()+", "+piece.getY()+")");
                piece.getHitbox().setGridLoc(tempPiece.getHitbox().getGridLocations());
                System.out.println("Before > ");
                int[][] locations = piece.getHitbox().getGridLocations();
                for (int m = 0; m < locations.length; m++) {
                    for (int k = 0; k < locations[0].length-1; k++) {
                        System.out.println("now at location > ("+locations[m][k]+", "+locations[m][k+1]+") after projRotation");
                    }
                }

                //piece.getHitbox().printGridLoc();
                System.out.println("after > ");
                piece.getHitbox().printGridLoc();
            }
    }
    
}