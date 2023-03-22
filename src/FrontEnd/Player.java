package FrontEnd;
import BackEnd.Game;
import BackEnd.PuzzlePiece;
import javax.swing.*;
import java.awt.Point;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Player {
    private boolean automated;
    private Board board;
    private JFrame frame;
    private boolean keyAdded;
    
    public Player(int xStart, int yStart, JFrame frame) {
        this.board = new Board(xStart,xStart, frame);
        this.frame = frame;
        keyAdded = false;
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

   }

   //Creates new piece at given location
   //Most likely are just going to specify how this method works ofr both computer and human
   public void createNewPiece() {
        PuzzlePiece piece = new PuzzlePiece();
        System.out.println("Stuck");
        board.add(piece);
        board.showBoard();
        double start = System.currentTimeMillis() / 1000.00;
        double end = 0;

        while (!Game.pieceSettled(board)) {
            movePiece(piece);
            end = System.currentTimeMillis()/ 1000.00;

            //We would have to change the speed as we go on, but this is a good demo
            //For now, it moves the piece down every two seconds
            if ((end-start) % 1.5 == 0) {
                piece.moveDown(frame);
            }
        }
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
                piece.rotate(frame);
                System.out.println("Roatated the piece");
			}
			
			if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                piece.moveDown(frame);
                System.out.println("down");
			}
			
			if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                piece.moveRight(frame);
                System.out.println("right");
			}
			
			if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                piece.moveLeft(frame);
                System.out.println("left");
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
    }
}
