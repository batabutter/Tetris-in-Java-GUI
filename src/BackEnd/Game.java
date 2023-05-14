package BackEnd;
import javax.swing.SwingUtilities;

import FrontEnd.Board;
import FrontEnd.Player;

public class Game {
    private boolean gameOver;
    private Player p1;
    private Player p2;
    private Board board;
    private Board board2;
    
    public Game(Player p1) {
        gameOver = false;
        this.p1 = p1;
        board = p1.getBoard();
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
        PuzzlePiece piece;
        
        while (!(p1.gameOver())) {
            
            end = (int) System.currentTimeMillis();

            
            if ((end-start) % 16 == 0 && (end-start) != tempTime) {
                frameCounter++;
                
                tempTime = (end-start);
                piece = new PuzzlePiece(board.getXStart() + 4*30, board.getYStart(), board.getXStart(), board.getYStart(), board.nextPiece(), false);
                PuzzlePiece nextPiece = new PuzzlePiece(board.getXStart() + 4*30, board.getYStart(), board.getXStart(), board.getYStart(), -1, false);

                end = (int) System.currentTimeMillis();

                if (board.getCurrentPiece() == null) {
                    p1.createNewPiece(piece, nextPiece);
                    pieceSettled = false;
                }
                //p1.movePiece(board.getCurrentPiece());
                //System.out.println("Settled? "+board.pieceSettled());
                if (!p1.gameOver()) {

                //System.out.println("On frame >" +frameCounter);
                    p1.movePiece(piece);
                    //SwingUtilities.updateComponentTreeUI(board.getFrame());
                    if (board.getCurrentPiece() != null) {
                        if (board.getDropSpeed() == frameCounter) {
                            board.movePieceDown(board.getCurrentPiece(),true);
                        }
                    }

                    if (frameCounter == 60) {
                        //System.out.println("Temp count >" +tempCount);
                        //System.out.println("Temp >"+tempCount);
                    }

                    
                    //This can be adjusted later on as well
                    if (board.getCurrentPiece() != null) {
                        if (board.pieceSettled(board.getCurrentPiece())) {
                            settleCount++;
                        } else {
                            settleCount = 0;
                        }

                        if (settleCount % board.getSettledFrames() == 0 && settleCount != 0){
                            board.update();
                        }
                    }

                    if (frameCounter == 60) {
                        frameCounter = 0;
                    }

                }
                SwingUtilities.updateComponentTreeUI(board.getFrame());
            }
            
        }   
        
        return board.getScore();
    }

    public Player getPlayer1() {
        return p1;
    }

    public Player getPlayer2() {
        return p2;
    }
    
}