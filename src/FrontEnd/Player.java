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
    
    public Player(JFrame frame) {
        this.board = new Board(100,100, frame);
        this.frame = frame;
   }

   public Board getBoard(){
    return board;
   }

   public Point movePiece(JLabel piece) {
    return null;
   }

   //Creates new piece at given location
   //Most likely are just going to specify how this method works ofr both computer and human
   public void createNewPiece() {
        PuzzlePiece piece = new PuzzlePiece();
        System.out.println("Stuck");
        board.add(piece);
        KeyboardControls k = new KeyboardControls(piece);
        frame.addKeyListener(k);
        board.showBoard();

        while (!Game.pieceSettled(board)) {

        }
    }

    class KeyboardControls implements KeyListener {
        private int width;
        private int height;
        private int x;
        private int y;
        private PuzzlePiece piece;

        KeyboardControls(PuzzlePiece piece) {
            this.piece = piece;
            x = piece.getX();
            y = piece.getY();
            Dimension size = piece.getLabel().getPreferredSize();
            
            width = size.width;
            height = size.height;
        }
        
        public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent event) {
            //Double check if this is needed
			if (event.getKeyCode() == KeyEvent.VK_UP) {
                y = piece.getY() - 20;
                piece.setX(x);
                piece.setY(y);
                SwingUtilities.updateComponentTreeUI(frame);
                System.out.println("up");
                System.out.println("Width > "+width);
			}
			
			if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                y = piece.getY() + 20;
                piece.setX(x);
                piece.setY(y);
                SwingUtilities.updateComponentTreeUI(frame);
                System.out.println("down");
			}
			
			if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                x = piece.getX() + 20;
                piece.setX(x);
                piece.setY(y);
                SwingUtilities.updateComponentTreeUI(frame);
                System.out.println("right");
			}
			
			if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                x = piece.getX() - 20;
                piece.setX(x);
                piece.setY(y);
                SwingUtilities.updateComponentTreeUI(frame);
                System.out.println("left");
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
    }
}
