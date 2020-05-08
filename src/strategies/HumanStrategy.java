package strategies;

import blokus.Node;
import blokus.Grid;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class HumanStrategy implements Strategy {

    private Grid grid;

    @Override
    public void turnStarted(Grid grid, ArrayList<Node> moves) {
        this.grid = grid;
    }

    @Override
    public void mouseClicked(int x, int y) {
        Point cell = grid.mouseToCell(x, y);

        if(grid != null && grid.getInHand() != null) {
            grid.move(grid.getInHand(), cell.x, cell.y);
        }
    }

    @Override
    public void mouseRightClicked(int x, int y) {
        if(grid != null && grid.getInHand() != null) {
            grid.getInHand().rotateClockwise();
        }
    }

    @Override
    public void mouseMiddleClicked(int x, int y) {
        if(grid != null && grid.getInHand() != null) {
            grid.getInHand().flip();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_R) {
            if(grid != null && grid.getInHand() != null) {
                grid.getInHand().reset();
            }
        }
    }
}
