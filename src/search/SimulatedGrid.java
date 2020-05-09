package search;

import blokus.Action;
import blokus.Grid;
import blokus.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class SimulatedGrid {

    private final Player[] players;

    private final Grid referenceGrid; // don't mutate this!
    private final Color[][] grid;
    private final Color[][] gridCopy;

    private int playerTurn; // 0-3 of which player's turn it currently is

    public SimulatedGrid(Grid grid, Player[] players, int playerTurn) {
        this.referenceGrid = grid;
        this.players = players;
        this.playerTurn = playerTurn;

        this.grid = new Color[Grid.HEIGHT_CELLS][Grid.WIDTH_CELLS];
        this.gridCopy = new Color[Grid.HEIGHT_CELLS][Grid.WIDTH_CELLS];

        for(int row = 0; row < Grid.HEIGHT_CELLS; row++) {
            System.arraycopy(grid.cells[row], 0, this.gridCopy[row], 0, Grid.WIDTH_CELLS);
        }

        rollback();
    }

    // resets grid with gridCopy
    public void rollback() {
        for(int row = 0; row < Grid.HEIGHT_CELLS; row++) {
            System.arraycopy(gridCopy[row], 0, grid[row], 0, Grid.WIDTH_CELLS);
        }

        for(Player p : players) {
            p.resetSimulated();
        }
    }

    /*
     * These functions should only be called by simulated action
     */

    private final Random random = new Random();

    double[] playout() {
        while(true) {
            boolean anyPlayed = false;

            for(int i = 0; i < players.length; i++) {
                ArrayList<Action> possibleActions = players[playerTurn].getAllPossibleMoves(grid);

                if(possibleActions.size() > 0) {
                    Action chosen = possibleActions.get(random.nextInt(possibleActions.size()));
                    new SimulatedAction(this, playerTurn, chosen).apply();

                    anyPlayed = true;

                    break;
                }
            }

            // Game over!!
            if(!anyPlayed) {
                // determine scores (higher must be better, so take max number of cells and subtract remaining pieces)
                double[] scores = new double[players.length];
                for(int i = 0; i < players.length; i++) {
                    scores[i] = players[i].getRemainingArea(true);
                }

                return scores;
            }
        }
    }

    void applyAction(SimulatedAction sim) {
        sim.action.piece.place(true, grid, sim.action.cellX, sim.action.cellY);
        playerTurn = (playerTurn + 1) % 4;
    }

    ArrayList<SimulatedAction> getPossibleMoves() {
        ArrayList<Action> possibleActions = players[playerTurn].getAllPossibleMoves(grid);

        ArrayList<SimulatedAction> simulatedActions = new ArrayList<>();

        for(Action action : possibleActions) {
            simulatedActions.add(new SimulatedAction(this, playerTurn, action));
        }

        return simulatedActions;
    }

    public Grid getReferenceGrid() {
        return referenceGrid;
    }
}
