package FrontEnd;
import BackEnd.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Image;

public class Board {
    PuzzlePiece currentPiece;
    //How much the piece will move down
    final int verticalMovement = 0;
    final static public int boardHeight = 600;
    final static public int boardWidth = 300;
    final static public int squareDim = boardWidth / 10;
    private double dropSpeed;
    private int startX;
    private int startY;
    private JFrame frame;

    //It will tell the board where it should create the piece
    //Paintcomponenet here
    public Board(int x, int y, JFrame frame) {
        startX = x;
        startY = y;
        dropSpeed = 1.5;
        this.frame = frame;
    }

    public JFrame getFrame() {
        return frame;
    }

    public double getDropSpeed() {
        return dropSpeed;
    }

    //This might also need to be changed depending on how Tetris changes the speed depending on the level
    public void setDropSpeed(double speed) {
        dropSpeed = speed;
    }

    public void showBoard() {
        DrawBackGround temp = new DrawBackGround();
        frame.add(temp);
        frame.setVisible(true);
    }

    public void setCurrentPiece(PuzzlePiece temp) {
        currentPiece = temp;
    }

    public PuzzlePiece getCurrentPiece() {
        return currentPiece;
    }

    //Change
    public void add(PuzzlePiece piece) {
        currentPiece = piece;
        JLabel temp = piece.getLabel();
        piece.setX(startX);
        piece.setY(startY);
        frame.add(temp);
        System.out.println(temp.getX());
    }

    public PuzzlePiece remove() {

        frame.remove(currentPiece.getLabel());
        PuzzlePiece temp = currentPiece;
        currentPiece = null;
        return temp;
    }

    class DrawBackGround extends JPanel {

        
        public void paintComponent(Graphics g) {
            
            g.fillRect(startX, startY, boardWidth, boardHeight);
            g.drawRect(startX-20, startY-20, (int) boardWidth + 2*(20), boardHeight + 2*(20));

            g.setColor(new Color(112,112,112));
            
            for (int i = 0; i <= boardWidth; i= i+squareDim) {
                g.drawLine(startX+i, startY, startX+i, startY + boardHeight);
            }

            for (int i = 0; i <= boardHeight; i+= squareDim) {
                g.drawLine(startX, startY+i, startX+boardWidth, startY + i);
            }
        }
    }

    //We most likely are going to have to rework some of these methods in order to account for different types of collisions

    public void movePieceRight(){
        PuzzlePiece piece = currentPiece;

        if ((piece.getX()+20) + piece.getWidth() <= startX+Board.boardWidth)
            piece.setX(piece.getX()+squareDim);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void movePieceLeft() {
        PuzzlePiece piece = currentPiece;

        if (piece.getX()-squareDim >= startX)
            piece.setX(piece.getX()-squareDim);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    //Maybe change where this calculation is done
    public void movePieceDown() {
        PuzzlePiece piece = currentPiece;

        if ((piece.getY()+20)+piece.getHeight() <= startY+Board.boardHeight)
            piece.setY(piece.getY()+squareDim);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void rotatePiece() {
        PuzzlePiece piece = currentPiece;
        ImageIcon shape = piece.getShape();
        Image image = shape.getImage();
        BufferedImage rotated = (BufferedImage) image;

        rotated = rotate(rotated, 90.0);
        ImageIcon temp = new ImageIcon(rotated.getScaledInstance(boardWidth, boardHeight, boardHeight));

        piece.setShape(temp);

        SwingUtilities.updateComponentTreeUI(frame);
    }

    public BufferedImage rotate(BufferedImage image, Double degrees) {
        // Calculate the new size of the image based on the angle of rotaion
        double radians = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int newWidth = (int) Math.round(image.getWidth() * cos + image.getHeight() * sin);
        int newHeight = (int) Math.round(image.getWidth() * sin + image.getHeight() * cos);
    
        // Create a new image
        BufferedImage rotate = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotate.createGraphics();
        // Calculate the "anchor" point around which the image will be rotated
        int x = (newWidth - image.getWidth()) / 2;
        int y = (newHeight - image.getHeight()) / 2;
        // Transform the origin point around the anchor point
        AffineTransform at = new AffineTransform();
        at.setToRotation(radians, x + (image.getWidth() / 2), y + (image.getHeight() / 2));
        at.translate(x, y);
        g2d.setTransform(at);
        // Paint the originl image
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return rotate;
    }
    
}