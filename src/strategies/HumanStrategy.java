package strategies;

import blokus.Grid;
import blokus.Piece;

import java.awt.*;
import java.util.ArrayList;

public class HumanStrategy implements Strategy {

    private Grid grid;

    @Override
    public void turnStarted(Grid grid, ArrayList<Piece> pieces) {
        this.grid = grid;
    }

    @Override
    public void mouseClicked(int x, int y) {
        Point cell = grid.mouseToCell(x, y);

        if(grid.getInHand() != null) {
            grid.move(grid.getInHand(), cell.x, cell.y);
        }
    }

    @Override
    public void mouseRightClicked(int x, int y) {
        if(grid.getInHand() != null) {
            grid.getInHand().rotateClockwise();
        }
    }

    @Override
    public void mouseMiddleClicked(int x, int y) {
        if(grid.getInHand() != null) {
            grid.getInHand().flip();
        }
    }
}
