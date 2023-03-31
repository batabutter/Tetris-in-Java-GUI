package BackEnd;
import FrontEnd.Board;
import java.util.ArrayList;
import java.awt.geom.Line2D;

public class Hitbox {
    private ArrayList<Line2D> points;
    private int xCell;
    private int yCell;
    private int pieceType;
    private PuzzlePiece piece;
    private int xStart;
    private int yStart;
    private int[][] gridLoc;
    private int[][] specialConditions;
    private int[][] visPiece;

    public Hitbox(int pieceType, PuzzlePiece piece, int xStart, int yStart) {
        //Always creates a hitbox starting at 0,0
        points = new ArrayList<Line2D>();
        this.pieceType = pieceType;
        visPiece = new int[20][10];
        this.piece= piece;
        this.xStart = xStart;
        this.yStart = yStart;
        switch (pieceType){
            case 0: 
                gridLoc = new int[4][2];
                specialConditions = new int[4][2];

                gridLoc[0][0] = (piece.getX() - xStart) / Board.squareDim;
                gridLoc[0][1] = (piece.getY() - yStart) / Board.squareDim;
                specialConditions[0][0] = 0;
                specialConditions[0][1] = 0;  

                gridLoc[1][0] = (piece.getX() - xStart) / Board.squareDim;
                gridLoc[1][1] = (piece.getY() - yStart) / Board.squareDim+ 1;
                specialConditions[1][0] = 0;
                specialConditions[1][1] = 1;  

                gridLoc[2][0] = (piece.getX() - xStart) / Board.squareDim + 1;
                gridLoc[2][1] = (piece.getY() - yStart) / Board.squareDim;
                specialConditions[2][0] = 1;
                specialConditions[2][1] = 0;  

                gridLoc[3][0] = (piece.getX() - xStart) / Board.squareDim + 1;
                gridLoc[3][1] = (piece.getY() - yStart) / Board.squareDim + 1;
                specialConditions[3][0] = 1;
                specialConditions[3][1] = 1;  

                //Don't forget that we had it start at 0
                createHitbox(xStart,yStart);
                
                // ----------
                // |        |
                // |        |
                // |        |
                // |        |
                // ----------

                break;

                case 1: gridLoc = new int[4][2];
                    specialConditions = new int[4][2];

                    gridLoc[0][0] = (piece.getX() - xStart) / Board.squareDim;
                    gridLoc[0][1] = (piece.getY() - yStart) / Board.squareDim;
                    specialConditions[0][0] = 0;
                    specialConditions[0][1] = 0;  

                    gridLoc[1][0] = (piece.getX() - xStart) / Board.squareDim;
                    gridLoc[1][1] = (piece.getY() - yStart) / Board.squareDim + 1;
                    specialConditions[1][0] = 0;
                    specialConditions[1][1] = 1;  

                    gridLoc[2][0] = (piece.getX() - xStart) / Board.squareDim;
                    gridLoc[2][1] = (piece.getY() - yStart) / Board.squareDim + 2;
                    specialConditions[2][0] = 1;
                    specialConditions[2][1] = 0;  

                    gridLoc[3][0] = (piece.getX() - xStart) / Board.squareDim;
                    gridLoc[3][1] = (piece.getY() - yStart) / Board.squareDim + 3;
                    specialConditions[3][0] = 1;
                    specialConditions[3][1] = 1;  
                    
                    createHitbox(xStart,yStart);
        }
        printGridLoc();
    }

    public void updateGridLoc() {
        int tempY;
        int tempX;
        if (piece.getY() != 0) {
            for (int i = 0; i < gridLoc.length; i++) {
                tempY = (piece.getY()- yStart) / Board.squareDim;
                tempX = (piece.getX() - xStart) / Board.squareDim;
                gridLoc[i][0] = tempX + specialConditions[i][0];
                gridLoc[i][1] = tempY + specialConditions[i][1];
            }
        }
    }
    
    public void printGridLoc() {
        int[][] temp = new int[20][10];

        for (int i = 0; i < gridLoc.length; i++) {
            int x = gridLoc[i][1];
            int y = gridLoc[i][0];
            //System.out.println("at location > ("+gridLoc[i][0] +", "+gridLoc[i][1]);
            temp[x][y] = 1;
        }
        
        System.out.println("----------------------------------------------------");
        for (int k = 0; k < temp.length; k++) {

            for (int m = 0; m < temp[0].length; m++) {
                System.out.print(temp[k][m]+" ");
            }
            System.out.println("\n");
        }
        
        System.out.println("----------------------------------------------------");
    }

    public int[][] getVisPiece() {
        int[][] temp = new int[20][10];

        for (int i = 0; i < gridLoc.length; i++) {
            int x = gridLoc[i][1];
            int y = gridLoc[i][0];
            temp[x][y] = 1;
        }
        return visPiece;
    }

    public Hitbox(int xStart, int yStart, int pieceType) {
        xCell = xStart;
        yCell = yStart;
        this.pieceType = pieceType;
        points = new ArrayList<Line2D>();
        createHitbox(xCell, yCell);
    }

    public ArrayList<Line2D> getPoints() {
        return points;
    }

    private void createHitbox(int xCell, int yCell) {
        switch (pieceType){
            case 0: 
                points.add(new Line2D.Float(xCell, yCell, xCell+60, yCell));
                points.add(new Line2D.Float(xCell, yCell, xCell, yCell+60));
                points.add(new Line2D.Float(xCell, yCell+60, xCell+60, yCell+60));
                points.add(new Line2D.Float(xCell+60, yCell+60, xCell+60, yCell));

                // ----------
                // |        |
                // |        |
                // |        |
                // |        |
                // ----------

                break;

            case 1:
                points.add(new Line2D.Float(xCell, yCell, xCell+30, yCell));
                points.add(new Line2D.Float(xCell, yCell, xCell, yCell+120));
                points.add(new Line2D.Float(xCell, yCell+120, xCell+30, yCell+120));
                points.add(new Line2D.Float(xCell+30, yCell+120, xCell+30, yCell));
                break;
        }
    }

    public void updateLocation(int xCell, int yCell) {
        //xCell = piece.getX();
        //yCell = piece.getY();

        switch (pieceType) {
            case 0:
                points.get(0).setLine(xCell, yCell, xCell+60, yCell);
                points.get(1).setLine(xCell, yCell, xCell, yCell+60);
                points.get(2).setLine(xCell, yCell+60, xCell+60, yCell+60);
                points.get(3).setLine(xCell+60, yCell+60, xCell+60, yCell);
            break;
        }


    }

    public void rotateHitBox() {

    }

    private void changeXAndY(int[][] temp) {

    }

    public void clear() {
        points.clear();
        gridLoc = null;
    }

    public int getPieceType() {
        return pieceType;
    }




}
