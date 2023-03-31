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

    private ImageIcon[] blocks = {new ImageIcon("images/yellow.png"), new ImageIcon("images/teal.png"), new ImageIcon("images/red.png"),  
    new ImageIcon("images/purple.png"), new ImageIcon("images/orange.png"), new ImageIcon("images/blue.png"), new ImageIcon("images/green.png")};

    private ImageIcon shape;
    private Dimension size;
    private JLabel pieceLabel;
    private int pieceType;
    //Temp
    private int xCell;
    private int yCell;
    private Hitbox hitbox;
    private int xStart;
    private int yStart;

    public PuzzlePiece(int xStart, int yStart, int boardXStart, int boardYStart, int pieceType){
        int index = pieceType;
        if (pieceType < 0 || pieceType >= blocks.length)
            index = (int)(Math.random()*blocks.length);
        this.pieceType = index;

        //System.out.println("piece type here > " + index);
        shape = blocks[index];
        this.xStart = boardXStart;
        this.yStart = boardYStart;
        xCell = xStart;
        yCell = yStart;
        pieceLabel = new JLabel(shape);
        size = pieceLabel.getPreferredSize();
        hitbox = new Hitbox(index, this, xStart, yStart);
    }

    public int getPieceType() {
        return pieceType;
    }

    public int xStart() {
        return xStart;
    }

    public int yStart() {
        return yStart;
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
        pieceLabel.setBounds(xCell, yCell, size.width, size.height);
        hitbox.updateGridLoc();
    }

    public int getX(){
        return xCell;
    }
    public void setY(int cell){
        yCell = cell;
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
        //Rotate the spots on the grid as well
        //Once it's rotated and the coordinates are set, check to see if the piece is valid or not



        shape = icon;
        pieceLabel.setIcon(shape);

        size = pieceLabel.getPreferredSize();
        pieceLabel.setBounds(xCell, yCell, size.width, size.height);

    }

    public void updateSize() {
        size = pieceLabel.getPreferredSize();
    }

}
