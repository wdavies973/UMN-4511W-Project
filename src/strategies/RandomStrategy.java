package strategies;

import blokus.Action;
import blokus.Grid;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class RandomStrategy implements Strategy {

    private final Random rnd = new Random();

    @Override
    public void turnStarted(BlockingQueue<Action> submit, Grid grid, ArrayList<Action> moves) {
        Action n = moves.get(rnd.nextInt(moves.size()));

        //System.out.println("playing node"+n);

        submit.add(n);
    }

}
