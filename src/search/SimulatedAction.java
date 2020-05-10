package search;

import blokus.Action;
import blokus.Grid;
import blokus.Player;

import java.awt.*;
import java.util.*;

// represents a complete copy of the game that can be mutated without affecting the actual game
public class SimulatedAction {

    private final Color[][] grid;
    private final Player[] players;
    private final int player;

    private final HashMap<Integer, HashSet<Integer>> playedPieces;

    private static final Random random = new Random();

    Action action;

    public ArrayList<SimulatedAction> children;

    public SimulatedAction(Color[][] grid, Player[] players, int player) {
        this.players = players;
        this.player = player;
        this.action = null;

        this.grid = new Color[Grid.HEIGHT_CELLS][Grid.WIDTH_CELLS];

        for(int row = 0; row < Grid.HEIGHT_CELLS; row++) {
            System.arraycopy(grid[row], 0, this.grid[row], 0, Grid.WIDTH_CELLS);
        }

        this.playedPieces = new HashMap<>();
        this.playedPieces.put(0, new HashSet<>());
        this.playedPieces.put(1, new HashSet<>());
        this.playedPieces.put(2, new HashSet<>());
        this.playedPieces.put(3, new HashSet<>());

        PRINT_GRID(this.grid);
    }

    private SimulatedAction(Color[][] grid, Player[] players, int player, HashMap<Integer, HashSet<Integer>> playedPieces, Action action) {
        this.players = players;
        this.player = player;
        this.action = action;

        this.grid = new Color[Grid.HEIGHT_CELLS][Grid.WIDTH_CELLS];

        for(int row = 0; row < Grid.HEIGHT_CELLS; row++) {
            System.arraycopy(grid[row], 0, this.grid[row], 0, Grid.WIDTH_CELLS);
        }

        this.playedPieces = new HashMap<>();
        this.playedPieces.put(0, new HashSet<>(playedPieces.get(0)));
        this.playedPieces.put(1, new HashSet<>(playedPieces.get(1)));
        this.playedPieces.put(2, new HashSet<>(playedPieces.get(2)));
        this.playedPieces.put(3, new HashSet<>(playedPieces.get(3)));
    }

    private void apply(Color[][] grid) {
        if(action != null) {
            action.piece.apply(false, action.flip, action.rotation);

            if(!action.piece.place(true, grid, action.cellX, action.cellY)) {
                System.out.println(action);
                System.out.println("ACTION REJECTED");
                PRINT_GRID(grid);
                System.exit(0);
            }

            HashSet<Integer> set = playedPieces.get(player);
            set.add(action.piece.getKind());
        }
    }

    public void expand() {
        expand(this.grid);
    }

    private void expand(Color[][] grid) {
        if(children != null) {
            return;
        }

        int index = (player + 1) % players.length;

        ArrayList<Action> possibleActions = players[index].getAllActionsExcluding(grid, playedPieces.get(index));

        ArrayList<SimulatedAction> simulatedActions = new ArrayList<>();

        for(Action action : possibleActions) {
            simulatedActions.add(new SimulatedAction(grid, players, index, playedPieces, action));
        }

        children = simulatedActions;
    }

    public Color[][] playedOut;

    // pre-condition: children array already populated
    public double[] playout() {
        if(playedOut != null) {
            throw new RuntimeException("Cannot play out simulated action twice");
        }

        Color[][] copy = new Color[Grid.HEIGHT_CELLS][Grid.WIDTH_CELLS];

        for(int row = 0; row < Grid.HEIGHT_CELLS; row++) {
            System.arraycopy(grid[row], 0, copy[row], 0, Grid.WIDTH_CELLS);
        }

        SimulatedAction action = this;

        while(true) {
            boolean anyPlayed = false;

            for(int i = 0; i < players.length; i++) {
                ArrayList<SimulatedAction> possible = action.children;

                if(possible.size() > 0) {
                    // Play a random child
                    action = possible.get(random.nextInt(possible.size()));

                    action.apply(copy);
                    action.expand(copy);

                    anyPlayed = true;
                    break;
                } else {
                    // skip to the next player
                    action = new SimulatedAction(copy, players, (action.player + 1) % players.length, action.playedPieces, null);
                    action.expand(copy);
                }
            }

            // Game over!!
            if(!anyPlayed) {
                // determine scores (higher must be better, so take max number of cells and subtract remaining pieces)
                double[] array = new double[]{countCells(copy, players[0]), countCells(copy, players[1]), countCells(copy, players[2]), countCells(copy, players[3])};
                this.playedOut = copy;
                return array;
            }
        }
    }

    private double countCells(Color[][] grid, Player player) {
        double count = 0;

        for(int row = 0; row < Grid.HEIGHT_CELLS; row++) {
            for(int col = 0; col < Grid.WIDTH_CELLS; col++) {
                if(player.getColor().equals(grid[row][col])) {
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

    public static void PRINT_LIST(ArrayList<SimulatedAction> actions) {
        System.out.println("-------------------- Actions ("+actions.size()+")--------------------");
        for(SimulatedAction action : actions) {
            System.out.println(action.getAction());
        }
        System.out.println("-------------------------------------------------");
    }

    public static void PRINT_GRID(Color[][] grid) {
        for(int row = 0; row < Grid.HEIGHT_CELLS; row++) {
            for(int col = 0; col < Grid.WIDTH_CELLS; col++) {
                if(grid[row][col] != null) {
                    if(grid[row][col].getRGB() == Color.blue.getRGB()) {
                        System.out.print("B ");
                    } else if(grid[row][col].getRGB() == Color.yellow.getRGB()) {
                        System.out.print("Y ");
                    } else if(grid[row][col].getRGB() == Color.red.getRGB()) {
                        System.out.print("R ");
                    } else {
                        System.out.print("G ");
                    }
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }
}
