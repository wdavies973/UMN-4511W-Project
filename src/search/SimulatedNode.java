package search;

// version 2 of simulated action

import blokus.Action;
import blokus.Grid;
import blokus.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

// represents a state as the result of an action being performed
public class SimulatedNode {

    private static final Random random = new Random();

    private SimulatedNode parent;
    private ArrayList<SimulatedNode> children;

    // A map from player id to pieces that the player may no longer
    // use throughout the course of this simulation
    private HashMap<Integer, HashSet<Integer>> excludedPieces;

    // state of the game at this node, which will be in turn
    // altered by any of the nodes in "children"
    private final Color[][] grid;

    // All players in the game
    private final Player[] players;

    // The action that was applied to this game state
    private Action action;

    // Helper variables that other algorithms may make use of
    private double score, visits;

    // Which player made the action leading to this state?
    private final int player;

    public static SimulatedNode CREATE_ROOT(Color[][] grid, Player[] players, int player) {
        // NORMALIZE THE PLAYER ID, this is a special case for the root, because the root
        // has no direct ancestor, so it will look like whatever player is before the current,
        // but not action applies and the previous move doesn't technically exist
        return new SimulatedNode(grid, players, Math.floorMod(player - 1, players.length));
    }

    // This is only for the special case node at the root, use CREATE_ROOT to make it
    private SimulatedNode(Color[][] grid, Player[] players, int player) {
        this.players = players;

        // CREATE EXCLUDED PIECES
        this.excludedPieces = new HashMap<>();
        for(int i = 0; i < players.length; i++) {
            this.excludedPieces.put(i, new HashSet<>());
        }

        // COPY THE BOARD STATE
        this.grid = new Color[Grid.HEIGHT_CELLS][Grid.WIDTH_CELLS];

        for(int row = 0; row < Grid.HEIGHT_CELLS; row++) {
            for(int col = 0; col < Grid.WIDTH_CELLS; col++) {
                this.grid[row][col] = grid[row][col];
            }
        }

        // SET THE PLAYER THAT MADE THE MOVE
        this.player = player;
    }

    /*
     * Initializes the simulated node, this will copy the game position
     * and apply the action immediately on the grid
     *
     * @param player = the player who is performing Action
     * @param action = the action to apply to the game state
     * @precondition - action is a valid move that can be made on the grid
     */
    public SimulatedNode(Color[][] grid, Player[] players, int player, HashMap<Integer, HashSet<Integer>> excludedPieces, SimulatedNode parent, Action action) {
        this(grid, players, player);

        this.parent = parent;

        if(action == null) {
            throw new IllegalArgumentException("Action may not be null");
        }

        // COPY THE HASHSETS
        this.excludedPieces = new HashMap<>();
        for(int playerId : excludedPieces.keySet()) {
            this.excludedPieces.put(playerId, (HashSet<Integer>)(excludedPieces.get(playerId).clone()));
        }

        // APPLY THE ACTION
        action.piece.apply(false, action.flip, action.rotation);
        action.piece.place(true, this.grid, action.cellX, action.cellY);

        this.action = action;

        // BAN THE PIECE FOR FUTURE USE FROM THIS PLAYER
        HashSet<Integer> excluded = this.excludedPieces.get(player);
        excluded.add(action.piece.getKind());
    }

    // SimulatedNodes are lazily expanded - they are only expanded on demand,
    // this will populate the children with available moves
    public ArrayList<SimulatedNode> expand() {
        if(children != null) {
            return children;
        }

        /*
         * Note: if there are no nodes that can be expanded, try expanded three more times
         * for the other players, otherwise, set the children to an empty array
         */

        int nextPlayer = (player + 1) % players.length;

        ArrayList<Action> childActions = new ArrayList<>();

        for(int i = 0; i < players.length; i++) {
            nextPlayer = (nextPlayer + i) % players.length;

            childActions = players[nextPlayer].getAllActionsExcluding(grid, excludedPieces.get(nextPlayer));

            if(childActions.size() > 0) {
                break;
            }
        }

        children = new ArrayList<>();

        if(childActions.size() > 0) {
            // nextPlayer was the player that succeeded in finding at least 1 move to play
            for(Action action : childActions) {
                children.add(new SimulatedNode(grid, players, nextPlayer, excludedPieces, this, action));
            }

        }

        return children;
    }

    public Color[][] playout;

    /*
     * Randomly play out this game state by successively applying
     * actions from "children", make sure grid is reset afterwords though.
     *
     * @precondition = this node itself has already been expanded
     * @constraint = cannot affect the state of the SimulatedNode, should be callable multiple times (but not in practice)
     */
    public double[] playout() {
        if(children == null) {
            throw new IllegalCallerException("Please call expand() before calling playout()");
        }

        SimulatedNode currentNode = this;

        while(true) {
            ArrayList<SimulatedNode> childNodes = currentNode.children;

            if(childNodes.size() == 0) {
                //SimulatedAction.PRINT_GRID(currentNode.grid);

                this.playout = currentNode.grid;

                // PLAYOUT GAME OVER
                return countScores(currentNode.grid);
            } else {
                currentNode = childNodes.get(random.nextInt(childNodes.size()));
                currentNode.expand();
            }
        }
    }

    private double[] countScores(Color[][] grid) {
        double[] scores = new double[players.length];

        for(int row = 0; row < Grid.HEIGHT_CELLS; row++) {
            for(int col = 0; col < Grid.HEIGHT_CELLS; col++) {
                if(players[0].getColor().equals(grid[row][col])) {
                    scores[0]++;
                } else if(players[1].getColor().equals(grid[row][col])) {
                    scores[1]++;
                } else if(players[2].getColor().equals(grid[row][col])) {
                    scores[2]++;
                } else if(players[3].getColor().equals(grid[row][col])) {
                    scores[3]++;
                }
            }
        }

        return scores;
    }

    public void update(double[] scores) {
        this.score += scores[this.player];
        this.visits++;
    }

    public double getVisits() {
        return visits;
    }

    public double getAverageScore() {
        return this.score / this.visits;
    }

    public double[] getScore() {
        return countScores(this.grid);
    }

    public ArrayList<SimulatedNode> getChildren() {
        return children;
    }

    public double getUCB1() {
        if(visits == 0) {
            return Double.MAX_VALUE;
        }

        return (score / visits) + 2 * Math.sqrt(Math.log(parent.visits) / visits);
    }

    public Action getAction() {
        return action;
    }
}
