package strategies;

import blokus.Action;
import search.SimulatedNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

// Applies several improvements to the MCTSStrategy, namely:
    // Probably not needed - Question 1 - embedding max^n (variant of minimax), paranoid, and BRS, these determine how nodes are selected during MCTS ("search policies")
    // Question 2 - traversal strategy, RAVE? Progressive History (relative history heuristic & progressive bias)
    // Question 3 - Enhancing playouts
        // alpha beta two-ply
        //

    // chapter 4, which overall search strategy to use
    // chapter 5, improve selection strategy of MCTS using Progressive History (against UCT)
    // chapter 6, playout strategies
    // chapter 8, overview
    // take aways
        // chapter 4 - make sure we're using MCTS-maxn (MAYBE paranoid)
        // chapter 5 - use Progressively history
        // chapter 6 - maybe use BRS for playouts

    // vanilla is when e-greedy and ph are disabled

public class PH_MCTSStrategy implements Strategy {

    long id = System.nanoTime();

    private static class ActionInfo {
        private int player;
        private final Action action;
        private double simulations; // the number of simulations in which this move was played
        private double score; // the total score the action achieved across all of these simulations

        public ActionInfo(int player, Action action) {
            this.action = action;
            this.simulations = 1;
            this.player = player;
        }

        // call after one simulation has been played
        public void update(double[] score) {
            double max = Double.MIN_VALUE;

            for(double d : score) {
                if(d > max) {
                    max = d;
                }
            }

            if(score[player] == max) {
                this.score++;
            }

            this.simulations++;
        }

        public double getAverageScore() {
            return score / simulations;
        }

        public boolean equals(Action action) {
            // && action.rotation == this.action.rotation && action.flip == this.action.flip && action.cellX == this.action.cellX && this.action.cellY == action.cellY
            return action.piece.getKind() == this.action.piece.getKind() && action.flip == this.action.flip && action.rotation == this.action.rotation;
        }
    }

    private static final HashMap<Integer, ArrayList<ActionInfo>> HISTORY = new HashMap<>();

    static {
        HISTORY.put(0, new ArrayList<>());
        HISTORY.put(1, new ArrayList<>());
        HISTORY.put(2, new ArrayList<>());
        HISTORY.put(3, new ArrayList<>());
    }

    // the number of seconds the strategy is allowed to work for
    private static final int COMPUTE_TIME_MS = 500;

    private double getProgressiveHistoryForPlayer(int player, Action action) {
        ArrayList<ActionInfo> playerHistory = HISTORY.get(player);
        for(ActionInfo info : playerHistory) {
            if(info.equals(action)) {
                return info.getAverageScore();
            }
        }

        return 0;
    }

    @Override
    public void turnStarted(BlockingQueue<Action> submit, SimulatedNode root) {
        long start = System.nanoTime();

        root.expand();

        while((System.nanoTime() - start) / 1_000_000 < COMPUTE_TIME_MS) {
            double[] result = MCTS(root);
            root.update(result);
        }

        double maxScore = Double.MIN_VALUE;
        SimulatedNode bestChild = null;

        for(SimulatedNode child : root.getChildren()) {
            if(child.getAverageScore() > maxScore) {
                maxScore = child.getAverageScore();
                bestChild = child;
            }
        }

        if(bestChild  == null) {
            throw new IllegalStateException("An error occurred");
        }

        submit.add(bestChild.getAction());
    }

    private double[] MCTS(SimulatedNode node) {
        SimulatedNode bestChild = null;
        double maxUCB1 = Double.MIN_VALUE;

        if(node.getChildren().size() == 0) {
            return node.getScore();
        } else {
            for(SimulatedNode child : node.getChildren()) {
                double ucb1 = child.getProgressiveHistory(getProgressiveHistoryForPlayer(child.getPlayer(), child.getAction()));

                if(ucb1 > maxUCB1) {
                    maxUCB1 = ucb1;
                    bestChild = child;
                }
            }
        }

        double[] result;

        //noinspection ConstantConditions
        if(bestChild.getVisits() == 0) {
            bestChild.expand();
            result = bestChild.playout();

            // Update the table
            ArrayList<ActionInfo> playerHistory = HISTORY.get(bestChild.getPlayer());
            boolean updated = false;
            for(ActionInfo info : playerHistory) {
                if(info.equals(bestChild.getAction())) {
                    info.update(result);
                    updated = true;
                    break;
                }
            }

            if(!updated) {
                playerHistory.add(new ActionInfo(bestChild.getPlayer(), bestChild.getAction()));
            }
        } else {
            result = MCTS(bestChild);
        }
        bestChild.update(result);
        return result;
    }

    @Override
    public String getName() {
        return "Progressive History"+id;
    }
}
