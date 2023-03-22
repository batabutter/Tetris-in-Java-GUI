package BackEnd;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
public class PuzzlePiece {
    private ImageIcon[] blocks = {new ImageIcon("images/blue.png")};
    private ImageIcon shape;
    private Dimension size;
    private JLabel pieceLabel;
    //Temp
    private int xCell = 0;
    private int yCell = 0;
    public PuzzlePiece(){
        shape = blocks[(int)(Math.random())*blocks.length];
        pieceLabel = new JLabel(shape);
        size = pieceLabel.getPreferredSize();
    }
    public ImageIcon getShape(){
        return shape;
    }

    //Change Icon here too
    public void setX(int cell){
        xCell = cell;
        pieceLabel.setBounds(xCell, yCell, size.width, size.height);

    }

    public int getX(){
        return xCell;
    }
    public void setY(int cell){
        yCell = cell;
        pieceLabel.setBounds(xCell, yCell, size.width, size.height);

    }
    public int getY(){
        return yCell;
    }

    public JLabel getLabel() {
        return pieceLabel;
    }

    public void moveRight(JFrame frame){
        setX(xCell+20);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void moveLeft(JFrame frame) {
        setX(xCell-20);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void moveDown(JFrame frame) {
        setY(yCell+20);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void rotate(JFrame frame) {
        SwingUtilities.updateComponentTreeUI(frame);
    }
}
