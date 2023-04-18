package BackEnd;
import FrontEnd.Board;
import FrontEnd.Player;

public class Game {
    private boolean gameOver;
    private Player p1;
    private Player p2;
    private Board board;
    private Board board2;
    
    public Game(Player p1, Player p2) {
        gameOver = false;
        this.p1 = p1;
        this.p2 = p2;
        board = p1.getBoard();
        board2 = p2.getBoard();
    }

    //This method will be given a current version of whatever board
    //It will then anaylize the board to see if it is completely "settled"
    //If it is, it will return a boolean
    public static boolean pieceSettled(Board board) {
        
        return false;
    }

    public boolean gameOver() {
        return gameOver;
    }

    public int start() {
        int start;
        int end;
        start = (int) System.currentTimeMillis();
        int frameCounter = 0;
        int settleCount = 0;
        boolean pieceSettled = true;
        int tempCount = 0;
        int tempTime = 0;

        while (!(p1.gameOver() && p2.gameOver())) {
            end = (int) System.currentTimeMillis();

            
            if ((end-start) % 16 == 0 && (end-start) != tempTime) {
                frameCounter++;
                tempTime = (end-start);
                PuzzlePiece piece = new PuzzlePiece(board.getXStart() + 4*30, board.getYStart(), board.getXStart(), board.getYStart(), -1);
                PuzzlePiece nextPiece = new PuzzlePiece(board.getXStart() + 4*30, board.getYStart(), board.getXStart(), board.getYStart(), -1);
                end = (int) System.currentTimeMillis();

                if (pieceSettled) {
                    p1.createNewPiece(piece, nextPiece);
                    pieceSettled = false;
                }
                //p1.movePiece(board.getCurrentPiece());
                //System.out.println("Settled? "+board.pieceSettled());

                //System.out.println("On frame >" +frameCounter);
                p1.movePiece(piece);


                if (frameCounter == 60) {
                    tempCount++;
                    //System.out.println("Temp count >" +tempCount);
                    //System.out.println("Temp >"+tempCount);
                    if (tempCount == 1) {
                        tempCount = 0;
                        board.movePieceDown();

                    }
                }
                
                //This can be adjusted later on as well
                if (board.pieceSettled()) {
                    settleCount++;
                } else {
                    settleCount = 0;
                }

                //System.out.println("Settlecount >" +settleCount);

                if (settleCount % 30 == 0 && settleCount != 0){
                    board.update();
                    pieceSettled = true;
                }
                
                if (frameCounter == 60) {
                    frameCounter = 0;
                }
                //board.getBoardGrid().printGrid();

                //p2.createNewPiece()
            }
        }   

        return 0;
    }

    public Player getPlayer1() {
        return p1;
    }

    public Player getPlayer2() {
        return p2;
    }
    
}