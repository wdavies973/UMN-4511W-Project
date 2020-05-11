package strategies;

import blokus.Action;
import search.SimulatedNode;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class DepthLimitedStrategy implements Strategy{

    private static final int COMPUTE_TIME_MS = 1000;

    public void turnStarted(BlockingQueue<Action> submit, SimulatedNode root) {
        long start = System.nanoTime();

        ArrayList<SimulatedNode> actions = root.expand();

        Random rnd = new Random();
        SimulatedNode best_child = actions.get(rnd.nextInt(actions.size()));
        ;
        double best_num = 0;

        for (int i = 0; i < actions.size(); i++) {

            if ((System.nanoTime() - start) / 1_000_000 > COMPUTE_TIME_MS) {
                break;
            }

            double possible = depthFirst(actions.get(i), 0, 0.0);
            if (possible > best_num) {
                best_child = actions.get(i);
                best_num = possible;
            }
        }
        submit.add(best_child.getAction());
    }

    private double depthFirst(SimulatedNode actions, int depth, double max) {
        final int COMPUTE_TIME_MS = 1000;
        if (depth == 2) {
            return max;
        }
        else {
            long start = System.nanoTime();
            double player_scores[] = actions.getScore();
            if (player_scores[actions.getPlayer()] > max) {
                max = player_scores[actions.getPlayer()];
            }

            ArrayList<SimulatedNode> children = actions.expand();

            for (int i = 0; i < children.size(); i++) {

                if ((System.nanoTime() - start) / 1_000_000 > COMPUTE_TIME_MS) {
                    break;
                }

                double result = depthFirst(children.get(i), depth++, max);
                if (result > max) {
                    max = result;
                }
            }
        }
        return max;
    }
}
