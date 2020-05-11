package strategies;

import blokus.Action;
import search.SimulatedNode;

import java.util.concurrent.BlockingQueue;

public class PB_MCTSStrategy implements Strategy {

    // the number of seconds the strategy is allowed to work for
    private static final int COMPUTE_TIME_MS = 500;

    long id = System.nanoTime();

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
                double ucb1 = child.getProgressiveBias();

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
        } else {
            result = MCTS(bestChild);
        }
        bestChild.update(result);
        return result;
    }

    @Override
    public String getName() {
        return "Progress bias"+id;
    }
}
