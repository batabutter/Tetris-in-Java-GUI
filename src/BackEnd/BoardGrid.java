package BackEnd;

public class BoardGrid {
    private int[][] board;

    public BoardGrid (int xStart, int yStart) {
        board = new int[20][10];
    }

    public int[][] getBoard() {
        return board;
    }

    public int nextRow(int row, int col) {
        return board[row][col+1];
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

        //Second: Create a way to determine how many lines are filled and where the locations are 



        //Third: Create a buffered image based on the grid data. Everytime a piece is placed. a buffered image is created for every line,
        //can have multiple per line





        //Fourth: Move the buffered images accordingly, creating the appropiate hitboxes for those buffered images, adding them to the list, and
        //updating their locations and displaying them to the JFrame
        //Also updating "allLines" to account for the new hitboxes created



        //Lastly, clear the current piece of all of it's data to avoid any memory leaks
        currentPiece.getHitbox().clear();
    }

    public void printGrid() {
        for (int i = 0; i < board.length; i++) {
            for (int k = 0; k < board[0].length; k++) {
               System.out.print(board[i][k]+" ");
            }
            System.out.println("\n");
        }
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
}