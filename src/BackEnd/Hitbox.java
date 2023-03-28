package BackEnd;
import java.util.ArrayList;
import java.awt.geom.Line2D;

public class Hitbox {
    private ArrayList<Line2D> points;
    private int xCell;
    private int yCell;
    private int pieceType;
    private PuzzlePiece piece;

    public Hitbox(int pieceType, PuzzlePiece piece) {
        //Always creates a hitbox starting at 0,0
        points = new ArrayList<Line2D>();
        this.pieceType = pieceType;
        this.piece= piece;
        switch (pieceType){
            case 0: 
                points.add(new Line2D.Float(0, 0, 60, 0));
                points.add(new Line2D.Float(0, 0, 0, 60));
                points.add(new Line2D.Float(0, 60, 60, 60));
                points.add(new Line2D.Float(60,60,60,0));

                // ----------
                // |        |
                // |        |
                // |        |
                // |        |
                // ----------

                break;
        }
    }

    public Hitbox(int xStart, int yStart, int pieceType) {
        xCell = xStart;
        yCell = yStart;
        this.pieceType = pieceType;
        points = new ArrayList<Line2D>();

        switch (pieceType){
            case 0: 
            points.add(new Line2D.Float(xCell, yCell, xCell+60, yCell));
            points.add(new Line2D.Float(xCell, yCell, xCell, yCell+60));
            points.add(new Line2D.Float(xCell, yCell+60, xCell+60, yCell+60));
            points.add(new Line2D.Float(xCell+60, yCell+60, xCell+60, yCell));

                // ----------
                // |        |
                // |        |
                // |        |
                // |        |
                // ----------

                break;
        }
    }

    public ArrayList<Line2D> getPoints() {
        return points;
    }

    public void updateLocation(int xCell, int yCell) {
        //xCell = piece.getX();
        //yCell = piece.getY();

        switch (pieceType) {
            case 0:
                points.get(0).setLine(xCell, yCell, xCell+60, yCell);
                points.get(1).setLine(xCell, yCell, xCell, yCell+60);
                points.get(2).setLine(xCell, yCell+60, xCell+60, yCell+60);
                points.get(3).setLine(xCell+60, yCell+60, xCell+60, yCell);
            break;
        }


    }

    public void clearLines() {
        points.clear();
    }

    public int getPieceType() {
        return pieceType;
    }


}
