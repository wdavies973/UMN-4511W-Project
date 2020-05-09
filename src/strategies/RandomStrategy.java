package strategies;

import blokus.Action;
import search.SimulatedAction;
import search.SimulatedGrid;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class RandomStrategy implements Strategy {

    private final Random rnd = new Random();

    @Override
    public void turnStarted(BlockingQueue<Action> submit, SimulatedGrid grid, SimulatedAction root) {
        ArrayList<SimulatedAction> actions = root.expand();

        SimulatedAction n = actions.get(rnd.nextInt(actions.size()));

        //System.out.println("playing node"+n);

        submit.add(n.getAction());
    }

}
