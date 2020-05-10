package strategies;

import blokus.Action;
import blokus.Grid;
import search.SimulatedNode;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.BlockingQueue;

public class HumanStrategy implements Strategy {

    private final Grid grid;
    private BlockingQueue<Action> submit;

    public HumanStrategy(Grid grid) {
        this.grid = grid;
    }

    @Override
    public void turnStarted(BlockingQueue<Action> submit, SimulatedNode root) {
        this.submit = submit;
    }

    @Override
    public void mouseClicked(int x, int y) {
        Point cell = grid.mouseToCell(x, y);

        if(submit != null && grid.getInHand() != null) {
            Action action = new Action(grid.getInHand(), cell.x, cell.y);

            if(grid.getInHand().isValid(grid.cells, cell.x, cell.y)) {
                grid.setInHand(null);
                submit.add(action);
            }
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
