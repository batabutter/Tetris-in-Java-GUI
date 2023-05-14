package BackEnd;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
public class PuzzlePiece {
    
    private ImageIcon[] blocks = {new ImageIcon("images/yellow.png"), new ImageIcon("images/teal.png"), new ImageIcon("images/red.png"),  
    new ImageIcon("images/purple.png"), new ImageIcon("images/orange.png"), new ImageIcon("images/blue.png"), new ImageIcon("images/green.png"), 
    new ImageIcon("images/yellowTrans.png"), new ImageIcon("images/tealTrans.png"), new ImageIcon("images/redTrans.png"),  
    new ImageIcon("images/purpleTrans.png"), new ImageIcon("images/orangeTrans.png"), new ImageIcon("images/blueTrans.png"), new ImageIcon("images/greenTrans.png")};

    private ImageIcon shape;
    private Dimension size;
    private JLabel pieceLabel;
    private int pieceType;
    private int timesRotated;
    //Temp
    private int xCell;
    private int yCell;
    private Hitbox hitbox;
    private int xStart;
    private int yStart;
    private int farthestX;
    private int color;
    private ArrayList<JLabel> currentPieceLabels;


    public PuzzlePiece(int xStart, int yStart, int boardXStart, int boardYStart, int pieceType, boolean hollow) {
        setPieceType(pieceType, hollow);

        this.xStart = boardXStart;
        this.yStart = boardYStart;
        xCell = xStart;
        yCell = yStart;
        pieceLabel = new JLabel(shape);
        size = pieceLabel.getPreferredSize();
        hitbox = new Hitbox(this.pieceType, this, this.xStart, this.yStart);
        farthestX = -1;
        currentPieceLabels = new ArrayList<JLabel>();
    }

    private void setPieceType(int pieceType, boolean hollow) {
        int index = pieceType;
        if (pieceType < 0) {
            index = (int)(Math.random()*7);
            this.pieceType = index;
            shape = blocks[this.pieceType];
            this.color = this.pieceType + 2;
        } else if (hollow) {
            index = pieceType + 7;
            this.pieceType = pieceType;
            shape = blocks[index];
            this.color = index + 2;
        } else {
            this.pieceType = index;
            shape = blocks[this.pieceType];
            color = this.pieceType + 2;
        }
        
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
        //pieceLabel.setBounds(xCell, yCell, size.width, size.height);
        hitbox.updateGridLoc();
    }

    public void setXLabel(int cell){
        xCell = cell;
        pieceLabel.setBounds(xCell, yCell, size.width, size.height);
    }

    public int getX(){
        return xCell;
    }
    public void setY(int cell){
        yCell = cell;
        //pieceLabel.setBounds(xCell, yCell, size.width, size.height);
        hitbox.updateGridLoc();
    }

    public void setYLabel(int cell){
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

    public void setShape(ImageIcon icon) {
        shape = icon;
        pieceLabel.setIcon(shape);

        size = pieceLabel.getPreferredSize();
        //pieceLabel.setBounds(xCell, yCell, size.width, size.height);

    }

    public void setShape(ImageIcon icon, int size, int width) {
        shape = icon;
        pieceLabel.setIcon(shape);
        pieceLabel.setBounds(xCell, yCell, size, width);

    }

    public void updateSize() {
        size = pieceLabel.getPreferredSize();
    }

    public int getFarthestX() {
        return farthestX;
    }

    public void setFarthestX(int n) {
        farthestX = n;
    }

    //Returns a boolean value: if the piece was successfully rotated or not
    public void changeToPiece(PuzzlePiece piece) {
        hitbox.setGridLoc(piece.getHitbox().getGridLocations());
        //piece = null;
    }

    public void setHitbox(Hitbox h) {
        hitbox = h;
    }

    public int getColor() {
        return color;
    }

    public String toString() {
        return "Piece type > "+this.pieceType +" at > "+"("+getX()+", "+getY()+")";
    }

    public void setTimesRotated(int timesRotated) {
        this.timesRotated = timesRotated;
    }

    public int getTimesRotated(){
        return this.timesRotated;
    }

    public ArrayList<JLabel> getPieceImages (){
        return currentPieceLabels;
    }

    public void addLabel(JLabel label) {
        currentPieceLabels.add(label);
    }
}
