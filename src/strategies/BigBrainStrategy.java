package strategies;

import blokus.Action;
import search.SimulatedNode;

import java.util.concurrent.BlockingQueue;

// the best we've got, it's not much, but its honest work
public class BigBrainStrategy implements Strategy {
    @Override
    public void turnStarted(BlockingQueue<Action> submit, SimulatedNode root) {

    }

    @Override
    public String getName() {
        return "MCTS FULL";
    }
}
