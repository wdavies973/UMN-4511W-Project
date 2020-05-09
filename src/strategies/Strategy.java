package strategies;

import blokus.Action;
import blokus.Grid;
import engine.ViewWatcher;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

// a player will request the move they should play from the strategy object
public interface Strategy extends ViewWatcher {

    void turnStarted(BlockingQueue<Action> submit, Grid grid, ArrayList<Action> moves);

}
