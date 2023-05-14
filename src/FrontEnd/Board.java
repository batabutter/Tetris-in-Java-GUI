package FrontEnd;
import BackEnd.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.Font;
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

    public void setDropSpeed(double speed) {
        dropSpeed = (int) speed;
    }

    public PuzzlePiece getCurrentPiece() {
        return currentPiece;
    }

    public void update() {
        arrBoard.updateGrid(currentPiece);
        removePieceImages();
        updateImagesOfBoard();
        holdCount = 0;
        timesRotated = 0;

        ArrayList<Integer> linesCleared = arrBoard.getPrevLinesCleared();

        for (int i = 0; i < linesCleared.size(); i++) {
            if (linesCleared.get(i) == 1) {
                addScore(100 * level);
            } else if (linesCleared.get(i) == 2) {
                addScore(300 * level);
            } else if (linesCleared.get(i) == 3) {
                addScore(500 * level);
            } else if (linesCleared.get(i) == 4) {
                addScore(800 * level);
            }
           numberOfLinesClearedOnLevel = numberOfLinesClearedOnLevel + linesCleared.get(i);

           if (numberOfLinesClearedOnLevel >=10) {
            numberOfLinesClearedOnLevel = 0;
            increaseLevel();
            if (level < 14) {
                setDropSpeed(Math.pow((0.8-((level-1)*0.007)), level-1) * 60);
                System.out.println("Drop speed is now > "+getDropSpeed());
            }
           }

        }
        remove();
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

        //System.out.println("Piece type > "+piece.getPieceType());
        addPiece(piece);
        //currentPiece.getHitbox().printGridLoc();
    }

    public void addProjPiece(PuzzlePiece piece) {
        if (projPiece != null) {
            removeProjPiece();
        }
        projPiece = piece;
        int[][] tempLocations = currentPiece.getHitbox().getGridLocations();
        int[][] newPieceLocations = new int[4][2];
        for (int i = 0; i < tempLocations.length; i++){
            newPieceLocations[i][0] = tempLocations[i][0];
            newPieceLocations[i][1] = tempLocations[i][1];
        }
        projPiece.getHitbox().setGridLoc(newPieceLocations);
        projPiece.setX(piece.getX());
        projPiece.setY(piece.getY());
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
        nextPiece.setXLabel(boardWidth + 90+startX);
        nextPiece.setYLabel(140);
        frame.add(nextPiece.getLabel());
    }

    public void add(int numColor, int row, int col, boolean addToPieceLabels, PuzzlePiece piece) {
        String color = ";";
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

    public void movePieceRight(){
        PuzzlePiece piece = currentPiece;

        int[][] futureLocations = new int[4][2];
        int[][] currentLocations = piece.getHitbox().getGridLocations();
        int[][] grid = arrBoard.getBoard();

        for (int i = 0 ; i < futureLocations.length; i++) {
            futureLocations[i][0] = currentLocations[i][0] + 1;
            futureLocations[i][1] = currentLocations[i][1];
        }

        boolean cont = true;
        for (int k = 0; k < futureLocations.length; k++) {
            if ((futureLocations[k][0] >= arrBoard.getWidth()) || (grid[futureLocations[k][1]][futureLocations[k][0]] != 0)) {
                cont = false;
            }
        }

        if (cont) {
            removePiece(piece);
            piece.setX(piece.getX()+squareDim);
            addPiece(piece);
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
        boolean cont = true;
        for (int k = 0; k < futureLocations.length; k++) {
            if ((futureLocations[k][0] < 0) || (grid[futureLocations[k][1]][futureLocations[k][0]] != 0)) {
                cont = false;
            }
        }

        if (cont) {
            removePiece(piece);
            piece.setX(piece.getX()-squareDim);
            addPiece(piece);
        }
    }

    public void movePieceDown(PuzzlePiece piece, boolean displayPiece) {
        int[][] futureLocations = new int[4][2];
        int[][] currentLocations = piece.getHitbox().getGridLocations();
        int[][] grid = arrBoard.getBoard();


        for (int i = 0 ; i < futureLocations.length; i++) {
            futureLocations[i][0] = currentLocations[i][0];
            futureLocations[i][1] = currentLocations[i][1]+1;
        }
        boolean cont = true;
        for (int k = 0; k < futureLocations.length; k++) {
            if ((futureLocations[k][1] >= arrBoard.getHeight() || (grid[futureLocations[k][1]][futureLocations[k][0]] != 0))) {
                cont = false;
            }
        }

        if (cont) {
            if (displayPiece) {
                removePiece(piece);
                piece.setY(piece.getY()+squareDim);
                addPiece(piece);
            } else {
                piece.setY(piece.getY()+squareDim);
            }
        }
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
            holdCount++;
        }
    }

    public void settlePiece(PuzzlePiece piece, boolean update) {
        while (!pieceSettled(piece)) {
            movePieceDown(piece, false);
            if (update)
                addScore(2);
        }
        addPiece(piece);

        if (update)
            update();
    }

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
            removePiece(piece);
            piece.getHitbox().setGridLoc(tempPiece.getHitbox().getGridLocations());

            piece.setX(tempPiece.getX());
            piece.setY(tempPiece.getY());

            if (timesRotated == 4)
                timesRotated = 0;

            addPiece(piece);
        }

    }    
}