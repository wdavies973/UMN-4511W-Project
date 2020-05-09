package strategies;

import blokus.Grid;
import blokus.Action;

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

public class MCTSStrategy implements Strategy {

    private static class MNode {
        private MNode parent;
        private ArrayList<MNode> children;

        // the action that is associated with it
        private Action node;

        private double t = 0, n = 0;

        public MNode() {

        }

        public MNode(ArrayList<Action> children) {
            this.children = new ArrayList<>();
            for(Action action : children) {
                this.children.add(new MNode());
            }
        }

        public boolean isLeaf() {
            return children == null;
        }
    }

    @Override
    public void turnStarted(BlockingQueue<Action> submit, Grid grid, ArrayList<Action> moves) {
        // Setup the root node
        MNode root = new MNode();


    }

}
