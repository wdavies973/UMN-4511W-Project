package strategies;

import blokus.Action;
import search.SimulatedNode;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class GreedyFirstStrategy implements Strategy {

    //final private Random rnd = new Random();
    public void turnStarted(BlockingQueue<Action> submit, SimulatedNode root) {

        Random rnd = new Random();
        ArrayList<SimulatedNode> actions = root.expand();

        SimulatedNode best_child = actions.get(rnd.nextInt(actions.size()));
        double best = 0;

        for (int i = 0; i < actions.size(); i++) {

            int move = rnd.nextInt(actions.size());
            double players[] = actions.get(move).getScore();

            if (players[actions.get(move).getPlayer()] > best) {
                best_child = actions.get(move);
                best = players[actions.get(move).getPlayer()];
            }
        }


        submit.add(best_child.getAction());
    }
}

