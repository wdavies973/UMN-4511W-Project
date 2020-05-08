package strategies;

import blokus.Node;
import blokus.Grid;
import engine.ViewWatcher;

import java.util.ArrayList;

// a player will request the move they should play from the strategy object
public interface Strategy extends ViewWatcher {

    void turnStarted(Grid grid, ArrayList<Node> moves);


}
