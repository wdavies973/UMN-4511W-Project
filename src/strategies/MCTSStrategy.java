package strategies;

import blokus.Action;
import search.SimulatedNode;

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

    long id = System.nanoTime();

    // the number of seconds the strategy is allowed to work for
    private static final int COMPUTE_TIME_MS = 6000;

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

        int inded =  0;

        for(SimulatedNode child : root.getChildren()) {
            if(child.getVisits() == 0) {
                System.out.println(inded+" / "+root.getChildren().size()+" were expanded");
                break;
            }

            if(child.getAverageScore() > maxScore) {
                maxScore = child.getAverageScore();
                bestChild = child;
            }

            inded++;
        }

        if(bestChild == null) {
            throw new IllegalStateException("An error occurred"+root.getChildren().size());
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
                double ucb1 = child.getUCB1();

                if(ucb1 > maxUCB1) {
                    maxUCB1 = ucb1;
                    bestChild = child;
                }
            }
        }

        double[] result;

        if(bestChild.getVisits() == 0) {
            bestChild.expand();
            result = bestChild.playout();
        } else {
            result = MCTS(bestChild);
        }
        bestChild.update(result);
        return result;
    }

    @Override
    public String getName() {
        return "Vanilla MCTS"+id;
    }
}
