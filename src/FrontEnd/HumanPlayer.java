package FrontEnd;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Point;
import BackEnd.PuzzlePiece;

import javax.swing.*;

public class HumanPlayer extends Player{
    //Each player will be given a frame, and they will send that to the board.
    //Inner calculations are done here
    private JFrame frame;
    private int x;
    private int y;
    public HumanPlayer(JFrame frame) {
        super(new Board(100,100, frame));
        this.frame = frame;
    }

    public Point movePiece(PuzzlePiece piece) {
        frame.addKeyListener(new KeyboardControls());
        getBoard().remove();

        Point temp = new Point(x, y);
        piece.setX(x);
        piece.setY(y);

        getBoard().add(piece);
        

        frame.removeKeyListener(new KeyboardControls());

        return temp;
    }
    class KeyboardControls implements KeyListener {
        
        public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.VK_UP) {
                y = y - 20;
			}
			
			if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                y = y + 20;
                System.out.println("bruh");
			}
			
			if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                x = x + 20;
                System.out.println("bruh");
			}
			
			if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                x = x - 20;
                System.out.println("bruh");
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
    }
}
