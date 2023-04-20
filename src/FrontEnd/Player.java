package FrontEnd;
import BackEnd.Game;
import BackEnd.PuzzlePiece;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Player {
    private Board board;
    //private JFrame frame;
    private JFrame frame;
    private boolean keyAdded;
    private int xStart;
    private int yStart;
    private int moveDir;
    private boolean gameOver;

    public Player(int xStart, int yStart, JFrame frame) {
        this.board = new Board(xStart,yStart, frame);
        this.xStart = xStart;
        this.yStart = yStart;
        this.frame = frame;
        keyAdded = false;
        moveDir = 0;
        gameOver = false;
   }

   public Board getBoard(){
    return board;
   }

   public void movePiece(PuzzlePiece piece) {
    
    KeyboardControls k = new KeyboardControls(piece);
    if (!keyAdded) {
        frame.addKeyListener(k);
        keyAdded = true;
    }
    if (moveDir == 1) {
        moveDir = 0;
        board.movePieceDown(board.getCurrentPiece());
    } else if (moveDir == 2) {
        moveDir = 0;
        board.movePieceRight();
    } else if (moveDir == 3) {
        moveDir = 0;
        board.movePieceLeft();
    } else if (moveDir ==4) {
        moveDir = 0;
        board.rotatePiece();
    } else if (moveDir == 5) {
        moveDir = 0;
        board.settlePiece(board.getCurrentPiece());
    } else if (moveDir == 6) {
        moveDir = 0;
        board.holdPiece();
    }
    if (board.getCurrentPiece() != null) {
        projPiece(board.getCurrentPiece());
    }
   }

   public void setGameOver(boolean gameOver) {
    this.gameOver = gameOver;
   }

   public boolean gameOver() {
    return gameOver;
   }

   //Creates new piece at given location
   //Most likely are just going to specify how this method works ofr both computer and human
   public void createNewPiece(PuzzlePiece piece, PuzzlePiece nextPiece) {
        board.add(piece);
        board.addNextPiece(nextPiece);
        SwingUtilities.updateComponentTreeUI(frame);
   }

   public void projPiece(PuzzlePiece piece) {
    PuzzlePiece projPiece = new PuzzlePiece(piece.getX(), piece.getY(), board.getXStart(), board.getYStart(), piece.getPieceType() + 7);
    board.addProjPiece(projPiece);
    while (!board.pieceSettled(projPiece)) {
        board.movePieceDown(projPiece);
    }
    SwingUtilities.updateComponentTreeUI(frame);
   }

    class KeyboardControls implements KeyListener {
        private int x;
        private int y;
        private PuzzlePiece piece;

        KeyboardControls(PuzzlePiece piece) {
            this.piece = piece;
            x = piece.getX();
            y = piece.getY();
            Dimension size = piece.getLabel().getPreferredSize();
        }
        
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