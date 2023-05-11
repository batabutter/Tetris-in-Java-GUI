package BackEnd;
import FrontEnd.Board;
import java.util.ArrayList;
import java.awt.geom.Line2D;

public class Hitbox {
    private ArrayList<Line2D> points;
    private int pieceType;
    private PuzzlePiece piece;
    private int xStart;
    private int yStart;
    private int[][] gridLoc;
    private double xPivot;
    private double yPivot;
    private double[][] boundingBox;

    public Hitbox(int pieceType, PuzzlePiece piece, int xStart, int yStart) {
        //Always creates a hitbox starting at 0,0
        points = new ArrayList<Line2D>();
        this.pieceType = pieceType;
        this.piece= piece;
        this.xStart = xStart;
        this.yStart = yStart;
        int xCell = (piece.getX() - piece.xStart()) / Board.squareDim;
        int yCell = (piece.getY() - piece.yStart()) / Board.squareDim;
        this.gridLoc = new int[4][2];

        //Does this cause a memory leak?
        switch (pieceType) {
            case 0 :
                int[][] temp = {{xCell,yCell},
                                {xCell,yCell+1},
                                {xCell + 1,yCell},
                                {xCell + 1,yCell + 1}};
                this.gridLoc = temp;
                break;
            case 1: 
                int[][] temp2 = {{xCell,yCell},
                                {xCell+1,yCell},
                                {xCell+2,yCell},
                                {xCell+3,yCell}};
                this.gridLoc = temp2;
            break;

            case 2:
                int[][] temp3 = {{xCell,yCell},
                                {xCell+1,yCell},
                                {xCell+1,yCell+1},
                                {xCell+2,yCell + 1}};
                this.gridLoc = temp3;
            break;

            case 3:
                int[][] temp4 = {{xCell,yCell},
                                {xCell-1,yCell+1},
                                {xCell,yCell+1},
                                {xCell+1,yCell + 1}};
                this.gridLoc = temp4;
            break;

            case 4:
            //Orange
                int[][] temp5 = {{xCell,yCell},
                                {xCell-1,yCell+1},
                                {xCell-2,yCell+1},
                                {xCell,yCell+1}};
                this.gridLoc = temp5;
            break;

            case 5:
                int[][] temp6 = {{xCell,yCell},
                                {xCell,yCell+1},
                                {xCell+1,yCell+1},
                                {xCell+2,yCell+1}};
                this.gridLoc = temp6;
            break;

            case 6:
                int[][] temp7 = {{xCell,yCell},
                                {xCell+1,yCell},
                                {xCell,yCell+1},
                                {xCell-1,yCell + 1}};
                this.gridLoc = temp7;
            break;

        }

        //Change
        if (pieceType ==  1) {
            xPivot = 1.5;
            yPivot = 0.5;
        } else {
            xPivot = 1;
            yPivot = 0; 
        }
    }

    public void updateGridLoc() {
        int tempY = 0;
        int tempX = 0;
        tempY = (piece.getY() - yStart) / Board.squareDim;
        tempX = (piece.getX() - xStart) / Board.squareDim;
        
        //System.out.println("Temp y before > "+tempY);
        //System.out.println("Temp x before > "+tempX);
        //System.out.println("changing > "+gridLoc[0][1]);
        
        for (int i = 0; i < gridLoc.length; i++) {
            //System.out.println("Before > ("+gridLoc[i][0] +", "+gridLoc[i][1]+")");
                
        }
        
        tempX = tempX - gridLoc[0][0];
        tempY = tempY - gridLoc[0][1];
        //System.out.println("Tempx > "+tempX);
        //System.out.println("Temp y after > "+tempY);
        if (piece.getY() != 0) {
            for (int i = 0; i < gridLoc.length; i++) {
                
                //System.out.println("before > ("+gridLoc[i][0] +", "+gridLoc[i][1]+")");
                //System.out.println("Piece is at > ("+piece.getX()+", "+piece.getY()+")");
                //System.out.println("x and y start > ("+xStart+", "+yStart+")");
                
                
                gridLoc[i][0] = tempX + gridLoc[i][0];
                gridLoc[i][1] = gridLoc[i][1] + tempY;
                //System.out.println("after > ("+gridLoc[i][0] +", "+gridLoc[i][1]+")");
                
            }
        }
        //System.out.println("-----------------------");
    }
    
    public void printGridLoc() {
        int[][] temp = new int[20][10];

        System.out.println("----------------------------------------------------");
        for (int i = 0; i < gridLoc.length; i++) {
            int x = gridLoc[i][1];
            int y = gridLoc[i][0];
            System.out.println("at location > ("+gridLoc[i][0] +", "+gridLoc[i][1]+")");
            temp[x][y] = piece.getColor();
        }
        
        for (int k = 0; k < temp.length; k++) {

            for (int m = 0; m < temp[0].length; m++) {
                System.out.print(temp[k][m]+" ");
            }
            System.out.println("\n");
        }
        
        System.out.println("----------------------------------------------------");
    }

    public int[][] getGrid() {
        int[][] temp = new int[20][10];

        for (int i = 0; i < gridLoc.length; i++) {
            int x = gridLoc[i][1];
            int y = gridLoc[i][0];
            temp[x][y] = piece.getColor();
        }
        return temp;
    }

    public Hitbox(int xStart, int yStart, int pieceType) {
        this.pieceType = pieceType;
        points = new ArrayList<Line2D>();

    }

    public int[][] getGridLocations() {
        return gridLoc;
    }

    public void setGridLoc(int[][] locations) {
        gridLoc = locations;
    }

    //This should alwyas be the last value in gridLoc
    public int[] getBottomMostLocation() {
        return gridLoc[gridLoc.length-1];
    }

    public void clear() {
        points.clear();
        gridLoc = null;
    }

    public int getPieceType() {
        return pieceType;
    }

    public double getXPivot() {
        return xPivot;
    }

    public double getYPivot() {
        return yPivot;
    }
    
    public void setXPivot(double x) {
        xPivot = x;
    }

    public void setYPivot(double y) {
        yPivot = y;
    }

    private void calculateXPivot() {

    }

    private void calculateYPivot() {

    }

}
