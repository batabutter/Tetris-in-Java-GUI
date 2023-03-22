package BackEnd;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import FrontEnd.Board;
public class PuzzlePiece {
    private ImageIcon[] blocks = {new ImageIcon("images/yellow.png")};
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

    public int getWidth() {
        return size.width;
    }

    public int getHeight() {
        return size.height;
    }

}
