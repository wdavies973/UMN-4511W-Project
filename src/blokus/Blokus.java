package blokus;

import engine.View;
import strategies.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

// controls the overall game and state
public class Blokus extends View implements Player.Listener {

    // draw 4 PieceBanks with grid in the center
    // piecebank will be roughly 20%? of width/height

    private final Grid grid = new Grid();

    private static final Color BETTER_GREEN = new Color(0, 100, 0);

    public final Strategy[] strategies = {
            new HumanStrategy(grid),
            new PH_MCTSStrategy(),
            new BarasonaStrategy(2, new PH_MCTSStrategy()),
            new BarasonaStrategy(3, new MCTSStrategy())
    };

    private final Player bottom = new Player(0, "Red", Color.red, strategies[0], this, Player.Style.Bottom);
    private final Player right = new Player(1,"Yellow", Color.yellow, strategies[1],this, Player.Style.Right);
    private final Player top = new Player(2,"Blue", Color.blue, strategies[2], this, Player.Style.Top);
    private final Player left = new Player(3,"Green", BETTER_GREEN, strategies[3],this,  Player.Style.Left);

    private final Player[] players = new Player[]{bottom, right, top, left};

    private int turn = 0;

    private String winner = "";
    private boolean gameOver;

    private final HashMap<String, Integer> wins = new HashMap<>();

    private static final boolean DISTRIBUTE_STRATEGIES = false;
    private static final int NUM_GAMES = 1;
    private int gameNum = 0;

    private final ArrayBlockingQueue<Action> queue = new ArrayBlockingQueue<>(1);

    public Blokus() {
        addChildren(grid, top, bottom, left, right);

        grid.addWatchers(players[0].getStrategy(), players[1].getStrategy(), players[2].getStrategy(), players[3].getStrategy());
        grid.setDrawDots(Color.blue, Color.yellow, BETTER_GREEN, Color.red);
    }

    public void beginGame() {
        bottom.setTurn(true);
        grid.setActiveWatcher(0);
        bottom.startTurn(queue, players, grid);
    }

    public void update() {
        if(gameOver) return;

        Action action = null;

        try {
            action = queue.take();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        if(action != null) {
            // applies the action
            grid.move(action);

            /*
             * Progress game state
             */
            nextTurn();

            for(int i = 0; i < players.length; i++) {
                Player currentTurn = players[(turn + i) % players.length];

                grid.setActiveWatcher((turn + i) % players.length);

                if(currentTurn.startTurn(queue, players, grid)) {
                    turn = (turn + i) % players.length;
                    return;
                } else {
                    currentTurn.setOutOfMoves(true);
                }
            }

            // We haven't returned, that means the game is over,
            // figure out the winner

            // step 1, is the game over?
            Player winner = null;
            int scoreMin = Integer.MAX_VALUE;

            for(Player p : players) {
                if(p.getRemainingArea() < scoreMin) {
                    scoreMin = p.getRemainingArea();
                    winner = p;
                }
            }

            this.gameOver = true;
            if(winner != null) {
                this.winner = winner.getName();
                int amount = wins.getOrDefault(winner.getStrategy().getName(), 0);
                wins.put(winner.getStrategy().getName(), amount + 1);

            }

            gameNum++;

            System.out.println("Game "+gameNum+" / "+NUM_GAMES+" finished");

            System.out.println("---------------- RESULTS ----------------");
            // Print results
            for(String name : wins.keySet()) {
                System.out.println(name+" wins = "+wins.get(name));
            }
            System.out.println("-----------------------------------------");

            if(gameNum < NUM_GAMES) {
                if(DISTRIBUTE_STRATEGIES) {
                    // Assign strategies
                    int offset = gameNum % (NUM_GAMES / 4); //

                    for(int i = 0; i < players.length; i++) {
                        players[(offset + i ) % players.length].setStrategy(strategies[i]);

                        System.out.println("Player "+((offset + i) % players.length)+" assigned strategy "+i);
                    }
                }

                reset();
            }
        }
    }

    private void nextTurn() {
        turn++;
        if(turn >= 4) {
            turn = 0;
        }

        for(Player p : players) {
            p.setTurn(false);
        }
    }

    @Override
    public void draw(Graphics2D g, int x, int y, int width, int height) {
        g.setColor(Color.lightGray);
        g.fillRect(x, y, width, height);

        g.setColor(Color.black);
        if(gameOver) {
            g.drawString("GAME OVER - "+winner+" wins!", x + 15, y + height - 15);
        }
    }

    @Override
    protected void layoutChildren(ArrayList<View> children, int x, int y, int width, int height) {
        /*
         * Layout grid
         */
        int gridSize = (int)(height * 0.58);

        int gridX = x + width / 2 - gridSize / 2;
        int gridY = y + height / 2 - gridSize / 2;

        grid.layout(gridX, gridY, gridSize, gridSize);

        /*
         * Layout piece banks
         */
        int bankLength = (int)(gridSize * 0.8);
        int bankThickness = (int)(bankLength / 7.0 * 3.0);
        
        top.layout(gridX + gridSize / 2 - bankLength / 2, gridY / 2 - bankThickness / 2, bankLength, bankThickness);
        bottom.layout(gridX + gridSize / 2 - bankLength / 2, gridY + gridSize + gridY / 2 - bankThickness / 2, bankLength, bankThickness);

        left.layout(gridX / 2 - bankThickness / 2, gridY + gridSize / 2 - bankLength / 2, bankThickness, bankLength); // left
        right.layout(gridX + gridSize + gridX / 2 - bankThickness / 2, gridY + gridSize / 2 - bankLength / 2, bankThickness, bankLength); // right
    }

    @Override
    public void pieceSelected(Piece piece) {
        grid.setInHand(piece);
    }

    public void reset() {
        queue.clear();

        grid.reset();

        for(Player p : players) {
            p.reset();
        }

        turn = 0;
        gameOver = false;
        winner = "";
        beginGame();
    }

    @Override
    public void keyPressed(KeyEvent key) {
        super.keyPressed(key);

        if(key.getKeyCode() == KeyEvent.VK_F12) {
            gameNum = 0;
            wins.clear();
        }
    }
}
