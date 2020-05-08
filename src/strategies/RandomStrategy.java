package strategies;

import blokus.Node;
import blokus.Grid;

import java.util.ArrayList;
import java.util.Random;

public class RandomStrategy implements Strategy {

    private final Random rnd = new Random();

    @Override
    public void turnStarted(Grid grid, ArrayList<Node> moves) {
        Node n = moves.get(rnd.nextInt(moves.size()));

        System.out.println("playing node"+n);

        grid.move(n);
    }

}
