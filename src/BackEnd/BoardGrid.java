package BackEnd;
import java.util.ArrayList;

public class BoardGrid {
    private int[][] board;
    private int height;
    private int width;
    private int shifts;

    public BoardGrid () {
        board = new int[20][10];
        height = 20;
        width = 10;
         shifts = 0;
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
                if (temp[i][k] == 1) {
                    board[i][k] = 1;
                }
            }
        }

        //Second: Create a buffered image based on the grid data. Everytime a piece is placed. a buffered image is created for every line,
        //can have multiple per line


        //Third: Create a way to determine how many lines are filled and where the locations are
        ArrayList<Integer> lines = linesCleared();

        for (int i = 0 ; i < lines.size(); i++) {
            moveGridDown(lines.get(i), lines.size());
            shifts++;
            for (int k = i+1; k < lines.size(); k++) {
                lines.set(k, shifts+lines.get(i));
            }
            
        }
        


        //Fourth: Adjust the positions of the buffered images based on the grid data


        //Lastly, clear the current piece of all of it's data to avoid any memory leaks
        printGrid();
        shifts = 0;
        currentPiece.getHitbox().clear();
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
        System.out.println("Swap");
        printGrid();
        count++;
        
        

    }

    public ArrayList<Integer> linesCleared() {
        int count = 0; 
        ArrayList<Integer> lines = new ArrayList<Integer>(); 
        for (int i = 0; i < board.length; i++) {
            for (int k = 0; k < board[0].length; k++) {
                if (board[i][k] == 1) 
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

            if (board[y][x] == 1)
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
            if (x == board[0].length){
                return false;
            } 

            if (board[y][x] == 1)
                return false;

        }

        //System.out.println("valid");

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

    public PuzzlePiece rotatePiece(PuzzlePiece piece, int timesRotated) {
        int xCell = piece.getX();
        int yCell = piece.getY();
        int xStart = piece.xStart();
        int yStart = piece.yStart();
        int pieceType = piece.getPieceType();
        PuzzlePiece testPiece = new PuzzlePiece(xCell, yCell, xStart, yStart, pieceType);
        int[][] locations = piece.getHitbox().getGridLocations();  
        
        /* 
        for (int n = 0; n < locations.length; n++) {
            System.out.println(n + ": "+"("+locations[n][0]+", "+locations[n][1]+")");
        }
        */
        rotatePieceInArr(locations, pieceType, timesRotated, piece);
        testPiece.getHitbox().setGridLoc(locations);
        rotatePieceInArr(piece.getHitbox().getSpecialLocations(), pieceType, timesRotated, piece);
        testPiece.getHitbox().setSpecialConditions(piece.getHitbox().getSpecialLocations());

        if (validLocationX(testPiece) && validLocationY(testPiece)) {
            return testPiece;
        }

        return null;
    }

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