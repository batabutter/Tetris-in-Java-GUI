package BackEnd;
import java.awt.*;
import javax.swing.*;
import java.util.*;
public class PuzzlePiece {
    private ImageIcon[] blocks = {new ImageIcon("images/blue.png")};
    private ImageIcon shape;
    private int xCell;
    private int yCell;
    public PuzzlePiece(){
        shape = blocks[(int)(Math.random())*blocks.length];
    }
    public ImageIcon getShape(){
        return shape;
    }
    public void setX(int cell){
        xCell = cell;
    }
    public int getX(){
        return xCell;
    }
    public void setY(int cell){
        yCell = cell;
    }
    public int getY(){
        return yCell;
    }

}
