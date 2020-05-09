package search;

import blokus.Action;
import blokus.Grid;
import blokus.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class SimulatedAction {

    private static final Random random = new Random();

    private final Color[][] grid;
    private final Color[][] beforeApply;

    private final Player[] players;

    private final HashSet<Integer> playedPieces;

    private final int player;
    Action action;

    public SimulatedAction(Color[][] grid, Player[] players, int player, HashSet<Integer> playedPieces, Action action) {
        this.players = players;
        this.player = player;
        this.action = action;
        this.playedPieces = new HashSet<>(playedPieces);

        this.grid = new Color[Grid.HEIGHT_CELLS][Grid.WIDTH_CELLS];
        this.beforeApply = new Color[Grid.HEIGHT_CELLS][Grid.WIDTH_CELLS];

        for(int row = 0; row < Grid.HEIGHT_CELLS; row++) {
            System.arraycopy(grid[row], 0, this.grid[row], 0, Grid.WIDTH_CELLS);
        }
    }


    private void apply(Color[][] grid) {
        if(action != null) {
            for(int row = 0; row < Grid.HEIGHT_CELLS; row++) {
                System.arraycopy(grid[row], 0, this.beforeApply[row], 0, Grid.WIDTH_CELLS);
            }

            action.piece.place(true, grid, action.cellX, action.cellY);
            playedPieces.add(action.piece.getKind());
        }
    }

    private void rollbackApply(Color[][] grid) {
        if(action != null) {
            for(int row = 0; row < Grid.HEIGHT_CELLS; row++) {
                System.arraycopy(beforeApply[row], 0, grid[row], 0, Grid.WIDTH_CELLS);
            }

            playedPieces.remove(action.piece.getKind());
        }
    }

    public ArrayList<SimulatedAction> expand() {
        return expand(this.grid);
    }

    private ArrayList<SimulatedAction> expand(Color[][] grid) {
        apply(grid);

        ArrayList<Action> possibleActions = players[player].getAllActionsExcluding(grid, playedPieces);

        ArrayList<SimulatedAction> simulatedActions = new ArrayList<>();

        for(Action action : possibleActions) {
            simulatedActions.add(new SimulatedAction(grid, players, this.action == null ? player : (player + 1) % players.length, playedPieces, action));
        }

        return simulatedActions;
    }

    public double[] playout() {
        Color[][] copy = new Color[Grid.HEIGHT_CELLS][Grid.WIDTH_CELLS];

        for(int row = 0; row < Grid.HEIGHT_CELLS; row++) {
            System.arraycopy(grid[row], 0, copy[row], 0, Grid.WIDTH_CELLS);
        }

        while(true) {
            boolean anyPlayed = false;

            for(int i = 0; i < players.length; i++) {
                ArrayList<SimulatedAction> possibleActions = expand(copy);

                // TODO rollback apply if no possible actions

                if(possibleActions.size() > 0) {
                    SimulatedAction chosen = possibleActions.get(random.nextInt(possibleActions.size()));
                    chosen.apply(copy);

                    anyPlayed = true;

                    break;
                } else {
                    rollbackApply(copy);
                }
            }

            // Game over!!
            if(!anyPlayed) {
                // determine scores (higher must be better, so take max number of cells and subtract remaining pieces)
                double[] array = new double[]{countCells(copy, players[0]), countCells(copy, players[1]), countCells(copy, players[2]), countCells(copy, players[3])};
                System.out.println(Arrays.toString(array));
                return array;
            }
        }
    }

    private double countCells(Color[][] grid, Player player) {
        double count = 0;

        for(int row = 0; row < Grid.HEIGHT_CELLS; row++) {
            for(int col = 0; col < Grid.WIDTH_CELLS; col++) {
                if(grid[row][col] != null && grid[row][col].getRGB() == player.getColor().getRGB()) {
                    count++;
                }
            }
        }

        return count;
    }

    public Action getAction() {
        return action;
    }

    public int getPlayer() {
        return player;
    }
}
