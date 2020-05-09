package strategies;

import blokus.Action;
import blokus.Grid;
import search.SimulatedAction;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class RandomStrategy implements Strategy {

    private final Random rnd = new Random();

    @Override
    public void turnStarted(BlockingQueue<Action> submit, Grid grid, SimulatedAction root) {
        ArrayList<SimulatedAction> actions = root.expand();

        SimulatedAction n = actions.get(rnd.nextInt(actions.size()));

        System.out.println("playing node"+n.getAction());

        submit.add(n.getAction());
    }

}
