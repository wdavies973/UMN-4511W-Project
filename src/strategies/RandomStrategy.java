package strategies;

import blokus.Action;
import blokus.Grid;
import search.SimulatedNode;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class RandomStrategy implements Strategy {

    private final Random rnd = new Random();

    @Override
    public void turnStarted(BlockingQueue<Action> submit, SimulatedNode root) {
        ArrayList<SimulatedNode> actions = root.expand();

        SimulatedNode n = actions.get(rnd.nextInt(actions.size()));

        //System.out.println("playing node"+n.getAction()+" for player "+n.getPlayer()+" actions "+actions.size());
        submit.add(n.getAction());
    }

}
