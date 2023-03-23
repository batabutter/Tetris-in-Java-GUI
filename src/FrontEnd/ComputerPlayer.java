package FrontEnd;
import javax.swing.*;
import BackEnd.PuzzlePiece;

public class ComputerPlayer extends Player{
    private double startTime;
    
    public ComputerPlayer(int xStart, int yStart, JFrame frame) {
        super(xStart, yStart, frame);
        startTime = System.currentTimeMillis() / 1000.0;
    }

    public void movePiece(PuzzlePiece piece) {
        double endTime = System.currentTimeMillis() / 1000.0;

        //System.out.println("Time elasped > "+(endTime-startTime));

        if ((endTime - startTime) % 2 == 0) {
            double rand = Math.random();
            if (rand < .49)
                getBoard().movePieceRight();
            else
                getBoard().movePieceLeft();
        }
    }

}