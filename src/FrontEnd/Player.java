package FrontEnd;
import BackEnd.PuzzlePiece;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Player {
    private Board board;
    //private JFrame frame;
    private JFrame frame;
    private boolean keyAdded;
    private int moveDir;
    private boolean gameOver;

    public Player(int xStart, int yStart, JFrame frame) {
        this.board = new Board(xStart,yStart, frame);
        this.frame = frame;
        keyAdded = false;
        moveDir = 0;
        gameOver = false;
   }

   public Board getBoard(){
    return board;
   }

   public void movePiece(PuzzlePiece piece) {
    
    KeyboardControls k = new KeyboardControls();
    if (!keyAdded) {
        frame.addKeyListener(k);
        keyAdded = true;
    }
    if (moveDir == 1) {
        board.movePieceDown(board.getCurrentPiece(), true);
        board.addScore(2);
    } else if (moveDir == 2) {
        board.movePieceRight();
    } else if (moveDir == 3) {
        board.movePieceLeft();
    } else if (moveDir ==4) {
        board.rotatePiece();
    } else if (moveDir == 5) {
        board.settlePiece(board.getCurrentPiece(), true);
    } else if (moveDir == 6) {
        board.holdPiece();
    }
    if (moveDir != 0) {
         if (board.getCurrentPiece() != null) {
            projPiece(board.getCurrentPiece());
        }
         
    }
    moveDir = 0;
   }

   public void setGameOver(boolean gameOver) {
    this.gameOver = gameOver;
   }

   public boolean gameOver() {
    return gameOver;
   }

   //Creates new piece at given location
   public void createNewPiece(PuzzlePiece piece, PuzzlePiece nextPiece) {
        //Maybe change to be more accurate, but in my opinion, this looks nicer  
        if (!gameOver){
            board.add(piece);
            board.addNextPiece(nextPiece);
            projPiece(board.getCurrentPiece());
        }
        setGameOver(board.getBoardGrid().gameOverCondition(piece));


   }

   public void projPiece(PuzzlePiece piece) {
    PuzzlePiece projPiece = new PuzzlePiece(piece.getX(), piece.getY(), board.getXStart(), board.getYStart(), piece.getPieceType(), true);
    board.addProjPiece(projPiece);
    //System.out.println("ProjPiece called");
    board.settlePiece(projPiece, false);
   }

    class KeyboardControls implements KeyListener {

        public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent event) {
            //Double check if this is needed
			if (event.getKeyCode() == KeyEvent.VK_UP) {
                moveDir = 4;
			}
			
			if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                moveDir = 1;
			}
			
			if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                moveDir = 2;
			}
			
			if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                moveDir = 3;
			}

            if (event.getKeyCode() == 32) {
                moveDir = 5;
			}

            if (event.getKeyCode() == 67) {
                moveDir = 6;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
    }

    
}