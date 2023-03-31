package BackEnd;
import java.awt.*;
import javax.swing.*;
public class PuzzlePiece {
    //Orientation of these elements are dependent on the program working

    /* 
    private ImageIcon[] blocks = {new ImageIcon("images/yellow.png"), new ImageIcon("images/teal.png"), 
    new ImageIcon("images/red.png"), new ImageIcon("images/purple.png"), new ImageIcon("images/orange.png"),
    new ImageIcon("images/green.png"), new ImageIcon("images/blue.png")};
    */

    private ImageIcon[] blocks = {new ImageIcon("images/yellow.png")};

    private ImageIcon shape;
    private Dimension size;
    private JLabel pieceLabel;
    //Temp
    private int xCell;
    private int yCell;
    private Hitbox hitbox;
    private int xStart;
    private int yStart;

    public PuzzlePiece(int xStart, int yStart){
        int index = (int)(Math.random()*blocks.length);
        shape = blocks[index];
        this.xStart = xStart;
        this.yStart = yStart;
        xCell = xStart;
        yCell = yStart;
        pieceLabel = new JLabel(shape);
        size = pieceLabel.getPreferredSize();
        hitbox = new Hitbox(index, this, xStart, yStart);
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public ImageIcon getShape(){
        return shape;
    }

    //Change Icon here too
    public void setX(int cell){
        xCell = cell;
        hitbox.updateLocation(xCell, yCell);
        pieceLabel.setBounds(xCell, yCell, size.width, size.height);
        hitbox.updateGridLoc();
    }

    public int getX(){
        return xCell;
    }
    public void setY(int cell){
        yCell = cell;
        hitbox.updateLocation(xCell, yCell);
        pieceLabel.setBounds(xCell, yCell, size.width, size.height);
        hitbox.updateGridLoc();

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

    public void setShape(ImageIcon icon) {
        shape = icon;
        pieceLabel.setIcon(shape);

        size = pieceLabel.getPreferredSize();
        pieceLabel.setBounds(xCell, yCell, size.width, size.height);

    }

    public void updateSize() {
        size = pieceLabel.getPreferredSize();
    }

}
