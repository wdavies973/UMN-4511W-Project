package strategies;

import blokus.Action;
import engine.ViewWatcher;
import search.SimulatedNode;

import java.util.concurrent.BlockingQueue;

// a player will request the move they should play from the strategy object
public interface Strategy extends ViewWatcher {

    // simulated action root does not actually have its own "action", it just has children
    void turnStarted(BlockingQueue<Action> submit, SimulatedNode root);

}
