package strategies;

import blokus.Action;
import blokus.Grid;
import search.SimulatedAction;
import search.SimulatedNode;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

// implements MCTS-max^n

// four phases
    // traverse the tree and compute UCB, select the highest value node
    // expand the nodes ?
    // random playout (play through the rest of the game and figure out who won)
    // backpropagate that value

// essentially monte-carlo is building up its own tree
// start off as a root node

// https://www.youtube.com/watch?v=UXW2yZndl7U

// a number of strategies that can be applied
    // max^n (minimax for multiplayer, each player only tries to increase his own position)
    // BRS (only 1 opponents plays a counter move)
    // Paranoid (assumption: all players have formed a coalition)
    // all above require a heuristic evaluation function
    // And of course, MCTS
        // improvements: different search policies, improving selection phase, improving playout phase

public class MCTSStrategy implements Strategy {

    // the number of seconds the strategy is allowed to work for
    private static final int COMPUTE_TIME_SECS = 10;

    private static class MNode {
        private final MNode parent;
        private ArrayList<MNode> children = new ArrayList<>();

        // the action that is associated with it
        private final SimulatedAction action;

        private double s = 0; // total score
        private double n = 0; // visits

        public MNode(MNode parent, SimulatedAction action) {
            this.parent = parent;
            this.action = action;
        }

        public void update(double[] scores) {
            s += scores[action.getPlayer()];
            n++;
        }
    }

    @Override
    public void turnStarted(BlockingQueue<Action> submit, Grid grid, SimulatedNode root) {


//        long start = System.nanoTime();
//
//        MNode root = new MNode(null, rootAction);
//        expand(root);
//
//        while(true) {
//            long elapsed = System.nanoTime() - start;
//
//            if(elapsed / 1_000_000_000 >= COMPUTE_TIME_SECS) {
//                break;
//            }
//
//            double[] result = MCTS(root);
//            root.update(result);
//        }
//
//        /*
//         * Choose final move, whichever child has the highest score
//         */
//        MNode bestChild = null;
//        double max = Double.MIN_VALUE;
//
//        for(MNode child : root.children) {
//            double score = child.s / child.n;
//
//            if(score > max) {
//                max = score;
//                bestChild = child;
//            }
//        }
//
//        System.out.println("Score: "+max);
//
//        if(bestChild == null) {
//            throw new RuntimeException("An error occurred");
//        }
//
//        submit.add(bestChild.action.getAction());
    }

    private double[] MCTS(MNode node) {
        MNode bestChild = null;
        double maxUct = Double.MIN_VALUE;

        for(MNode child : node.children) {
            double uct = uct(child);

            if(uct > maxUct) {
                maxUct = uct;
                bestChild = child;
            }
        }

        if(bestChild == null) {
            throw new RuntimeException("Best child cannot be null");
        }

        double[] result;

        if(bestChild.n == 0) {
            result = playout(bestChild);
            expand(bestChild);
        } else {
            result = MCTS(bestChild);
        }
        bestChild.update(result);
        return result;
    }

    private void expand(MNode node) {
        node.action.expand();
        ArrayList<SimulatedAction> expanded = node.action.children;

        ArrayList<MNode> nodes = new ArrayList<>();

        for(SimulatedAction simAction : expanded) {
            nodes.add(new MNode(node, simAction));
        }

        node.children = nodes;
    }

    // should take the current position
    private double[] playout(MNode current) {
        return current.action.playout();
    }

    private double uct(MNode node) {
        if(node.n == 0) {
            return Double.MAX_VALUE;
        }

        return (node.s / node.n) + 2 * Math.sqrt(Math.log(node.parent.n) / node.n);
    }

}
