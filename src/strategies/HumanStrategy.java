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

        for(Node move : moves) {
            System.out.println("Move: piece " + move.piece.getKind() + " at (" + move.cellX + "," + move.cellY + ") with rotation " + move.rotation + " and flip " + (move.flip));
        }
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

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_R) {
            if(grid.getInHand() != null) {
                grid.getInHand().reset();
            }
        }
    }
}
