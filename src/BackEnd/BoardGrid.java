package BackEnd;
import java.util.ArrayList;

public class BoardGrid {
    private int[][] board;
    private int height;
    private int width;
    private int shifts;
    private ArrayList<Integer> lines;
    private ArrayList<Integer> prevLinesCleared;

    private int[][][] wallKickDataJLSTZ = {
        {{0,0}, {-1, 0}, {-1, 1}, {0,-2}, {-1,-2}},
        {{0,0}, {1,0}, {1, -1}, {0, 2}, {1, 2}},
        {{0,0}, {1,0}, {1, -1}, {0, 2}, {1, 2}},
        {{0,0}, {-1,0}, {-1, 1}, {0, -2}, {-1, -2}},
        {{0,0}, {1,0}, {1, 1}, {0, -2}, {1, -2}},
        {{0,0}, {-1,0}, {-1, -1}, {0, 2}, {-1, 2}},
        {{0,0}, {-1,0}, {-1, -1}, {0, 2}, {-1, 2}},
        {{0,0}, {1,0}, {1, 1}, {0, -2}, {1, -2}}
    };

    private int[][][] wallKickDataI = {
        {{0,0}, {2, 0}, {-1, 0}, {2,-1}, {-1,2}},
        {{0,0}, {-2,0}, {1, 0}, {-2, 1}, {1, -2}},
        {{0,0}, {1,0}, {-2, 0}, {1, 2}, {-2, -1}},
        {{0,0}, {1,0}, {2, 0}, {-1, -2}, {2, 1}},
        {{0,0}, {-2,0}, {1, 0}, {-2, 1}, {1, -2}},
        {{0,0}, {2,0}, {-1, 0}, {2, -1}, {-1, 2}},
        {{0,0}, {-1,0}, {2, 0}, {-1, -2}, {2, 1}},
        {{0,0}, {1,0}, {-2, 0}, {3, 2}, {-2, -1}}
    };

    private int[] srsOffSet;

    public BoardGrid () {
        board = new int[20][10];
        height = 20;
        width = 10;
        shifts = 0;
        srsOffSet = null;
    }

    public int[][] getBoard() {
        return board;
    }

    public int nextRow(int row, int col) {
        return board[row][col+1];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void updateGrid(PuzzlePiece currentPiece) {

        //First : Update the 2D Array with the piece data> 
        int[][] temp = currentPiece.getHitbox().getGrid();
        for (int i = 0; i < temp.length; i++) {
            for (int k = 0; k < temp[0].length; k++) {
                if (temp[i][k] != 0) {
                    board[i][k] = temp[i][k];
                }
            }
        }

        //Second: Create a buffered image based on the grid data. Everytime a piece is placed. a buffered image is created for every line,
        //can have multiple per line


        //Third: Create a way to determine how many lines are filled and where the locations are
        lines = linesCleared();
        prevLinesCleared = getLinesCleared();

        for (int i = 0 ; i < lines.size(); i++) {
            moveGridDown(lines.get(i), lines.size());
            for (int k = i+1; k < lines.size(); k++) {
                lines.set(k, 1+lines.get(i));
            }
            
        }
        

        shifts = 0;
        currentPiece.getHitbox().clear();
    }

    public ArrayList<Integer> getLinesCleared() {
        int count = 1;
        ArrayList<Integer> linesInSucc = new ArrayList<Integer>();
        lines = linesCleared();
        //System.out.println("Checking the amount of lines cleared > "+lines.size());
        for (int i = 1; i < lines.size(); i++) {
            int current = lines.get(i);
            int prev = lines.get(i-1);
            if (prev != (current-1)) {
                linesInSucc.add(count);
                count = 0;
            }
                count++;
        }
        if (lines.size() > 0)
            linesInSucc.add(count);
        return linesInSucc;
    }

    public ArrayList<Integer> getPrevLinesCleared() {
        return prevLinesCleared;
    }

    public void moveGridDown(int row, int N) {
        System.out.println("Clearing row > "+row);
        for (int i = 0; i < board[0].length; i++) {
            board[row][i] = 0;
        }

        int count = 0; 
        for (int i = row - count ; i > 1; i--) {
            int[] temp = board[i];
            board[i] = board[i-1];
            board[i-1] = temp;
        }
        //System.out.println("Swap");
        //printGrid();
        count++;
        
    }

    public ArrayList<Integer> linesCleared() {
        int count = 0; 
        ArrayList<Integer> lines = new ArrayList<Integer>(); 
        for (int i = 0; i < board.length; i++) {
            for (int k = 0; k < board[0].length; k++) {
                if (board[i][k] != 0) 
                    count++;
            }
            if (count == 10)
                lines.add(i);
            count = 0;
        }

        return lines;
    }

    public void printGrid() {
        System.out.println("--------------------");
        for (int i = 0; i < board.length; i++) {
            for (int k = 0; k < board[0].length; k++) {
               System.out.print(board[i][k]+" ");
            }
            System.out.println("\n");
        }
        System.out.println("--------------------");
    }

    public boolean validLocationY(PuzzlePiece piece) {
        int[][] locations = piece.getHitbox().getGridLocations();
        for (int i = 0; i < locations.length; i++) {
            int x = locations[i][0];
            int y = locations[i][1];
            //System.out.println("at location > ("+locations[i][0] +", "+locations[i][1]);
            if (y == board.length){
                return false;
            } 

            if (board[y][x] != 0)
                return false;

        }

        //System.out.println("valid");

        return true;
    }

    public boolean validLocationX(PuzzlePiece piece) {
        int[][] locations = piece.getHitbox().getGridLocations();
        for (int i = 0; i < locations.length; i++) {
            int x = locations[i][0];
            int y = locations[i][1];
            if (x >= board[0].length){
                return false;
            } 

            if (board[y][x] != 0)
                return false;

        }

        //System.out.println("valid");

        return true;
    }

    public boolean validLocationX(int[][] arr) {
        int[][] locations = arr;
        for (int i = 0; i < locations.length; i++) {
            int x = locations[i][0];
            int y = locations[i][1];
            if (x >= board[0].length){
                return false;
            }

            if (y >= board.length)
                return false;

            if (board[y][x] != 0)
                return false;

        }

        //System.out.println("valid");

        return true;
    }

    public boolean gameOverCondition(PuzzlePiece piece) {

        if (validLocationX(piece) && validLocationY(piece))
            return false;


        return true;
    }


    public int[][] getCurrentState(PuzzlePiece currentPiece) {
        int[][] temp = currentPiece.getHitbox().getGrid();
        for (int i = 0; i < temp.length; i++) {
            for (int k = 0; k < temp[0].length; k++) {
                if (temp[i][k] == 1) {
                    board[i][k] = 1;
                }
            }
        }

        return temp;
    }

    public PuzzlePiece rotatePiece(PuzzlePiece piece, int timesRotated, boolean checkCol) {
        int xCell = piece.getX();
        int yCell = piece.getY();
        int xStart = piece.xStart();
        int yStart = piece.yStart();
        int pieceType = piece.getPieceType();
        PuzzlePiece testPiece = new PuzzlePiece(xCell, yCell, xStart, yStart, pieceType, false);
        int[][] locations = piece.getHitbox().getGridLocations();
        int[][] tempLocations = new int[4][2];
        int[][][] wallKickData;

        rotatePieceInArr(piece.getHitbox().getSpecialLocations(), pieceType, timesRotated, piece);
        testPiece.getHitbox().setSpecialConditions(piece.getHitbox().getSpecialLocations());
        int multi = -1;
        

        if (pieceType != 0) {

            if (pieceType == 1) {
                wallKickData = wallKickDataI;
                System.out.println("Here");
            } else {
                wallKickData = wallKickDataJLSTZ;
            }

                for (int i = 0; i < 5; i++) {
                    tempLocations = getTempLocations(locations);
                    rotatePieceInArr(tempLocations, pieceType, timesRotated, piece);
                    System.out.println("------------------------------");
                    int prevX = piece.getFarthestX();
                    System.out.println("Times rotated > "+timesRotated);

                    System.out.println("Trying > "+ i);

                    for (int m = 0; m < tempLocations.length; m++) {
                        for (int k = 0; k < tempLocations[0].length-1; k++) {
                            System.out.println("at location > ("+tempLocations[m][k]+", "+tempLocations[m][k+1]+") before wall kick");
                        }
                    }

                    for (int k = 0; k < tempLocations.length; k++) {
                        tempLocations[k][0] = tempLocations[k][0] - wallKickData[timesRotated][i][0];
                        tempLocations[k][1] = tempLocations[k][1] + wallKickData[timesRotated][i][1];
                    }

                    setSRSOffSet(wallKickData[timesRotated][i]);
                    
                    for (int m = 0; m < tempLocations.length; m++) {
                        for (int k = 0; k < tempLocations[0].length-1; k++) {
                            System.out.println("now at location > ("+tempLocations[m][k]+", "+tempLocations[m][k+1]+") after wall kick");
                        }
                    }

                    //testPiece.getHitbox().setGridLoc(tempLocations);
                    boolean cont = true;
                    if (checkCol) {
                        if (!validLocationX(tempLocations)) {
                            piece.setFarthestX(prevX);
                            cont = false;
                        }
                    }

                    prevX = piece.getFarthestX();
                    testPiece.getHitbox().setGridLoc(tempLocations);
                    
                    if (cont) {
                        
                        if (checkCol) {
                            
                            if (validLocationX(testPiece) && validLocationY(testPiece)) {
                                System.out.println("Valid");
                                return testPiece;
                            }
                        } else {
                            return testPiece;
                        }
                    }

                }
            
        }
        return null;
    }

    private int[][] getTempLocations(int[][] locations) {
        int[][] tempLocations = new int[4][2];
        for (int i = 0; i < locations.length; i++) {
            for (int k = 0; k < locations[0].length; k++) {
                tempLocations[i][k] = locations[i][k];
            }
        }
        
        return tempLocations;
    }

    public int[] getSRSOffset() {
        return srsOffSet;
    }

    public void setSRSOffSet(int[] arr) {
        srsOffSet = arr;
    }

    //Possibly change this to SRS in the future, but for now it's beyond the scope of the project

    private void rotatePieceInArr(int[][] locations, int pieceType, int timesRotated, PuzzlePiece piece) {
        int minX = 100;
        int maxX = 0;
        int maxY = 0;

        for (int k = 0; k < locations.length; k++) {
            int temp = locations[k][0];
            if (temp > maxX) {
                maxX = temp;
            }
            temp = locations[k][1];
            if (temp > maxY) {
                maxY = temp;
            }
        }

        int minY = 100;

        for (int k = 0; k < locations.length; k++) {
            int temp = locations[k][1];
            if (temp < minY) {
                minY = temp;
            }
        }

        int orgMinX = 100;
            for (int k = 0; k < locations.length; k++) {
                int temp = locations[k][0];
                if (temp < orgMinX) {
                    orgMinX = temp;
                }
            }

        for (int i = 0; i < locations.length; i++) {
            int temp = locations[i][0];
            locations[i][0] = locations[i][1];
            locations[i][1] = temp;
            locations[i][0] = locations[i][0] * -1;
        }

        /* 
        for (int n = 0; n < locations.length; n++) {
            System.out.println(n + ": "+"("+locations[n][0]+", "+locations[n][1]+")");
        }
        */

        int minY2 = 100;

        for (int k = 0; k < locations.length; k++) {
            int temp = locations[k][1];
            if (temp < minY2) {
                minY2 = temp;
            }
        }
        minX = 100;
            for (int k = 0; k < locations.length; k++) {
                int temp = locations[k][0];
                if (temp < minX) {
                    minX = temp;
                }
            }
        int offSet = 2;
        if (pieceType == 1){
            offSet = 2;
        }
        //If the order of the pieces in the array are ever changed, this code will break
        int tempMax = -1000;
        int tempMin = 100;

        for (int k = 0; k < locations.length; k++) {
            int temp = locations[k][0];
            if (temp > tempMax) {
                tempMax = temp;
            }
            if (temp < tempMin) {
                tempMin = temp;
            }
        }

        int newMaxDifference = tempMax-tempMin;
        int oldMaxDifference = maxX-orgMinX;
        int totalDiff = Math.abs(newMaxDifference - oldMaxDifference);
        if (totalDiff > 0) {
            if (newMaxDifference > oldMaxDifference)
                piece.setFarthestX(newMaxDifference);   
            else 
                piece.setFarthestX(oldMaxDifference); 
        }
        if ((piece.getFarthestX() != -1)) {
            if (newMaxDifference >= piece.getFarthestX())
                maxX = maxX + totalDiff;
        }
        for (int m = 0; m < locations.length; m++) {
            locations[m][0] = locations[m][0]+(maxX - minX)-offSet;
            locations[m][1] = locations[m][1]-(minY2-minY);
        }

    }
}