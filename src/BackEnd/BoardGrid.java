package BackEnd;
import java.util.ArrayList;

public class BoardGrid {
    private int[][] board;
    private int height;
    private int width;
    private ArrayList<Integer> lines;
    private ArrayList<Integer> prevLinesCleared;

    private int[][][] wallKickDataJLSTZ = {
        {{0,0}, {-1, 0}, {-1, 1}, {0,-2}, {-1,-2}, {0,0}, {1,0}, {1, -1}, {0, 2}, {1, 2}},

        {{0,0}, {1,0}, {1, -1}, {0, 2}, {1, 2}, {0,0}, {-1,0}, {-1, 1}, {0, -2}, {-1, -2}},

        {{0,0}, {1,0}, {1, 1}, {0, -2}, {1, -2},{0,0}, {-1,0}, {-1, -1}, {0, 2}, {-1, 2}},

        {{0,0}, {-1,0}, {-1, -1}, {0, 2}, {-1, 2},{0,0}, {1,0}, {1, 1}, {0, -2}, {1, -2}},
    };

    private int[][][] wallKickDataI = {
        {{0,0}, {-2, 0}, {1, 0}, {-2,-1}, {1,2}, {0,0}, {2, 0}, {-1, 0}, {2,1}, {-1,-2}},

        {{0,0}, {1,0}, {-2, 0}, {1, -2}, {-2, 1}, {0,0}, {1, 0}, {-2, 0}, {1,-2}, {-2,1}},

        {{0,0}, {-2,0}, {1, 0}, {-2, -1}, {1, 2}, {0,0}, {-2, 0}, {1, 0}, {-2,-1}, {1,2}},

        {{0,0}, {-1,0}, {2, 0}, {-1, 2}, {2, -1}, {0,0}, {-1, 0}, {2, 0}, {-1,2}, {2,-1}}
    };

    private int[][] rightRotationMatrix = {
        {-1,-1}, {-1, 1}, {-1, -1}, {-1,1}
    };

    private int[][] leftRotationMatrix = {
        {1,-1}, {-1, -1}, {1, -1}, {-1,-1}
    };

    private int[] srsOffSet;

    public BoardGrid () {
        //board = new int[20][10];
        
        int[][] temp  = {{0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0}};
        board = temp;
        


        height = 20;
        width = 10;
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

        int[][] temp = currentPiece.getHitbox().getGrid();
        for (int i = 0; i < temp.length; i++) {
            for (int k = 0; k < temp[0].length; k++) {
                if (temp[i][k] != 0) {
                    board[i][k] = temp[i][k];
                }
            }
        }

        lines = linesCleared();
        prevLinesCleared = getLinesCleared();

        for (int i = 0 ; i < lines.size(); i++) {
            moveGridDown(lines.get(i), lines.size());
        }
        
        currentPiece.getHitbox().clear();
    }

    public ArrayList<Integer> getLinesCleared() {
        int count = 1;
        ArrayList<Integer> linesInSucc = new ArrayList<Integer>();
        lines = linesCleared();
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
        for (int i = 0; i < board[0].length; i++) {
            board[row][i] = 0;
        }

        int count = 0; 
        for (int i = row - count ; i > 1; i--) {
            int[] temp = board[i];
            board[i] = board[i-1];
            board[i-1] = temp;
        }
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
            if (count == 10) {
                lines.add(i);
            }
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

            if (y == board.length){
                return false;
            } 

            if (board[y][x] != 0)
                return false;

        }

        return true;
    }

    public boolean validLocationX(PuzzlePiece piece) {
        int[][] locations = piece.getHitbox().getGridLocations();
        for (int i = 0; i < locations.length; i++) {
            int x = locations[i][0];
            int y = locations[i][1];
            if (x >= board[0].length || x < 0){
                return false;
            }

            if (y >= board.length || y < 0) {
                return false;
            }

            if (board[y][x] != 0)
                return false;

        }

        return true;
    }

    public boolean validLocationX(int[][] arr) {
        int[][] locations = arr;
        for (int i = 0; i < locations.length; i++) {
            int x = locations[i][0];
            int y = locations[i][1];
            if (x >= board[0].length || x < 0){
                return false;
            }

            if (y >= board.length || y < 0)
                return false;

            if (board[y][x] != 0)
                return false;

        }

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
        double xPivot = piece.getHitbox().getXPivot();
        double yPivot = piece.getHitbox().getYPivot();

        int direction = 1;
        

        if (pieceType != 0) {
            int sideMulti = 1;

            if (pieceType == 1) {
                wallKickData = wallKickDataI;
                sideMulti = -1;

            } else {
                wallKickData = wallKickDataJLSTZ;
            }

                for (int i = 0; i < 10; i++) {
                    tempLocations = getTempLocations(locations);

                    if (i < 5) {
                        rotatePieceInArr(tempLocations, pieceType, timesRotated, xPivot, yPivot, "right"); 
                    } else {
                        rotatePieceInArr(tempLocations, pieceType, timesRotated, xPivot, yPivot, "left");
                    }

                    int prevX = piece.getFarthestX();

                    for (int k = 0; k < tempLocations.length; k++) {
                        tempLocations[k][0] = tempLocations[k][0] + sideMulti * wallKickData[timesRotated][i][0];
                        tempLocations[k][1] = tempLocations[k][1] + wallKickData[timesRotated][i][1];
                    }

                    setSRSOffSet(wallKickData[timesRotated][i]);
                    
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
                        if (validLocationX(testPiece) && validLocationY(testPiece)) {
                            int farthestLeft = 100;
                            int farthestTop = 100;
                            int[][] offSetLocations = testPiece.getHitbox().getGridLocations();
                            locations = getTempLocations(testPiece.getHitbox().getGridLocations());

                            for (int m = 0; m < locations.length; m++) {
                                int temp = offSetLocations[m][0];
                                int tempY = offSetLocations[m][1];
                                if (temp < farthestLeft) {
                                    farthestLeft = temp;
                                }
                                if (tempY < farthestTop) {
                                    farthestTop = tempY;
                                }
                            }

                            int index = 0;
                            boolean farthestLeftTopExists = false;
                            for (int m = 0; m < locations.length; m++) {
                                if (locations[m][0] == farthestLeft && locations[m][1] == farthestTop) {
                                    index = m;
                                    farthestLeftTopExists = true;
                                }
                                if (!farthestLeftTopExists && locations[m][1] == farthestTop) {
                                    index = m;
                                }
                            }
                                

                            int[] tempLocation = {locations[0][0], locations[0][1]};
                            locations[0] = locations[index];
                            locations[index] = tempLocation;

                                
                            if (direction == 1) {
                                piece.getHitbox().setXPivot(rightRotationMatrix[timesRotated][0] * yPivot);
                                piece.getHitbox().setYPivot(rightRotationMatrix[timesRotated][1] * xPivot);
                            } else {
                                piece.getHitbox().setXPivot(leftRotationMatrix[timesRotated][0] * yPivot);
                                piece.getHitbox().setYPivot(leftRotationMatrix[timesRotated][1] * xPivot);
                            }
                                    
                                

                            testPiece.setX(locations[0][0]*30 + 250);
                            testPiece.setY(locations[0][1]*30 + 70);
                            testPiece.getHitbox().setGridLoc(locations);
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

    private void rotatePieceInArr(int[][] locations, int pieceType, int timesRotated, double xPivot, double yPivot, String dir) {
        int farthestX = -1;
        int smallestX = 100;
        int farthestY = -1;
        int direction = 1;

        if (dir.equals("left")) {
            direction = -1;
        }

        for (int i = 0; i < locations.length; i++) {
            int tempX = locations[i][0];
            int tempXSmall = locations[i][0];
            int tempY = locations[i][1];
            if (tempX > farthestX) {
                farthestX = tempX;
            }
            if (tempY > farthestY) {
                farthestY = tempY;
            }
            if (tempXSmall < smallestX) {
                smallestX = tempXSmall;
            }

        }

        double alpha = 0;
        double beta = 0;

        alpha = smallestX + xPivot;
        beta = farthestY + yPivot;

        if (pieceType != 1 && timesRotated == 2) {
            beta = beta - 1;
        }
        if (pieceType != 1 && timesRotated == 3) {
            alpha = alpha + 1;
        }

        double theta = direction * (Math.PI / 2.0);

        for (int i = 0; i < locations.length; i++) {
            double x = locations[i][0];
            double y = locations[i][1];
            int xRotated = (int) (Math.round(alpha+(x-alpha)*Math.cos(theta)-(y-beta)*Math.sin(theta)));
            int yRotated = (int) (Math.round(beta+(x-alpha)*Math.sin(theta)+(y-beta)*Math.cos(theta)));
            locations[i][0] = xRotated;
            locations[i][1] = yRotated;
        }

    }

}