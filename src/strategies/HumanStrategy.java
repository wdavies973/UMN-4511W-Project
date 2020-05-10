package strategies;

import blokus.Action;
import blokus.Grid;
import search.SimulatedNode;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.BlockingQueue;

public class HumanStrategy implements Strategy {

    private Grid grid;
    private BlockingQueue<Action> submit;

    @Override
    public void turnStarted(BlockingQueue<Action> submit, Grid grid, SimulatedNode root) {
        this.grid = grid;
        this.submit = submit;
    }

    @Override
    public void mouseClicked(int x, int y) {
        Point cell = grid.mouseToCell(x, y);

        if(submit != null && grid.getInHand() != null) {
            Action action = new Action(grid.getInHand(), cell.x, cell.y);

            if(grid.getInHand().isValid(grid.cells, cell.x, cell.y)) {
                submit.add(action);
            }
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
