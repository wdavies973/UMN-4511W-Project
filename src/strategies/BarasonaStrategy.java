package strategies;

import blokus.Action;
import blokus.Grid;
import search.SimulatedNode;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class BarasonaStrategy implements Strategy {

    private Strategy strategy;

    private int turn = 0;

    public BarasonaStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void turnStarted(BlockingQueue<Action> submit, Grid grid, SimulatedNode root) {
        turn++;

        ArrayList<SimulatedNode> actions = root.expand();
        SimulatedNode node = root.expand().get(50);

        submit.add(node.getAction());

        // play the first 6 moves (6 calls of this method) as barasona

        if(turn > 8) {

            // after
            strategy.turnStarted(submit, grid, root);
        } else {
            // barsona
        }



    }


}
