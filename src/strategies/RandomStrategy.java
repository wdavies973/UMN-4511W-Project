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
        root.expand();
        ArrayList<SimulatedAction> actions = root.children;

        SimulatedAction n = actions.get(0);

        System.out.println("playing node"+n.getAction()+" for player "+n.getPlayer()+" actions "+actions.size());

        submit.add(n.getAction());
    }

}
