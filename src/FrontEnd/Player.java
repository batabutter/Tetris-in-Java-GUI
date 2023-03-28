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

    public Player(int xStart, int yStart, JFrame frame) {
        this.board = new Board(xStart,yStart, frame);
        this.xStart = xStart;
        this.yStart = yStart;
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
   public void createNewPiece(PuzzlePiece piece, PuzzlePiece nextPiece) {
        board.add(piece);
        //board.showBoard();
        //SwingUtilities.updateComponentTreeUI(frame);

        //SwingUtilities.updateComponentTreeUI(frame);
        double start = System.currentTimeMillis() / 1000.00;
        double end = 0;

        while (!board.pieceSettled()) {
            movePiece(board.getCurrentPiece());
            end = System.currentTimeMillis()/ 1000.00;
            //We would have to change the speed as we go on, but this is a good demo
            //For now, it moves the piece down every second
            if (((end-start) % board.getDropSpeed() == 0)) {
                try {
                Thread.sleep(200);
                } catch (Exception e) {
                    System.out.println(e);
                }
                board.movePieceDown();
            }
        }
        board.update();


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
                board.rotatePiece();
			}
			
			if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                board.movePieceDown();
			}
			
			if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                board.movePieceRight();
			}
			
			if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                board.movePieceLeft();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
    }

    
}