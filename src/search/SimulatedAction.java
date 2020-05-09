package search;

import blokus.Action;

import java.util.ArrayList;

public class SimulatedAction {

    private final int player;
    private final SimulatedGrid grid;
    Action action;

    public SimulatedAction(SimulatedGrid grid, int player, Action action) {
        this.player = player;
        this.grid = grid;
        this.action = action;
    }

    public void apply() {
        grid.applyAction(this);
    }

    public ArrayList<SimulatedAction> expand() {
        return grid.getPossibleMoves();
    }

    public double[] playout() {
        return grid.playout();
    }

    public Action getAction() {
        return action;
    }

    public int getPlayer() {
        return player;
    }
}
