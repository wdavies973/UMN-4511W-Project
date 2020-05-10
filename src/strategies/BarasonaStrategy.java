package strategies;

import blokus.Action;
import blokus.Grid;
import search.SimulatedNode;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class BarasonaStrategy implements Strategy {

    private Strategy strategy;

    private int turn = 0;
    private final Random rnd = new Random();

    public BarasonaStrategy(Strategy strategy) {

        this.strategy = strategy;
    }

    @Override
    public void turnStarted(BlockingQueue<Action> submit, SimulatedNode root) {
        turn++;

        ArrayList<SimulatedNode> actions = root.expand();

        /*
        if (turn == 1) {
            submit.add(Action())
        }
        */

        SimulatedNode best_child = actions.get(rnd.nextInt(actions.size()));
        double best = 5000;

        for (int i = 0; i < actions.size(); i++) {
            double players[] = actions.get(i).getScore();
            if (players[actions.get(i).getPlayer()] < best) {
                best_child = actions.get(i);
                best = players[actions.get(i).getPlayer()];
            }
        }


        submit.add(best_child.getAction());

        // play the first 6 moves (6 calls of this method) as barasona

        if(turn > 8) {

            // after
            //strategy.turnStarted(submit, grid, root);
        } else {
            // barsona
        }



    }


}
