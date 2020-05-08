package blokus;

import engine.View;
import strategies.HumanStrategy;
import strategies.RandomStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

// controls the overall game and state
public class Blokus extends View implements Player.Listener, Grid.Listener {

    // draw 4 PieceBanks with grid in the center
    // piecebank will be roughly 20%? of width/height

    private final Grid grid = new Grid(this);

    private static final Color BETTER_GREEN = new Color(0, 100, 0);

    private final Player top = new Player("John", Color.blue, new RandomStrategy(), this, Player.Style.Top);
    private final Player bottom = new Player("AI", Color.red, new HumanStrategy(), this, Player.Style.Bottom);
    private final Player left = new Player("AI", BETTER_GREEN, new HumanStrategy(),this,  Player.Style.Left);
    private final Player right = new Player("AI", Color.yellow, new HumanStrategy(),this, Player.Style.Right);

    private final Player[] players = new Player[]{bottom, right, top, left};

    private int turn = 0;

    public Blokus() {
        addChildren(grid, top, bottom, left, right);

        grid.addWatchers(players[0].getStrategy(), players[1].getStrategy(), players[2].getStrategy(), players[3].getStrategy());
        grid.setDrawDots(Color.blue, Color.yellow, BETTER_GREEN, Color.red);

        // bottom player starts
        bottom.setTurn(true);
        bottom.startTurn(grid);
        grid.setActiveWatcher(0);
    }

    @Override
    public void turnFinished() {
        boolean any = false;

        for(Player p : players) {
            if(p.canPlay(grid.cells)) {
                any = true;
                break;
            } else {
                p.setOutOfMoves(true);
            }
        }

        if(!any) {
            // game over!
            // TODO could stand some improvement
            JOptionPane.showMessageDialog(null, "Game over!");
            return;
        }

        turn++;
        if(turn >= 4) {
            turn = 0;
        }

        for(Player p : players) {
            p.setTurn(false);
        }

        grid.setInHand(null);

        players[turn].setTurn(true);

        // skip a player if they can't make any moves
        if(!players[turn].startTurn(grid)) {
            turnFinished();
            return;
        }

        grid.setActiveWatcher(turn);
    }

    @Override
    public void draw(Graphics2D g, int x, int y, int width, int height) {
        g.setColor(Color.lightGray);
        g.fillRect(x, y, width, height);
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

    @Override
    public void keyPressed(KeyEvent key) {
        super.keyPressed(key);

        if(key.getKeyCode() == KeyEvent.VK_F12) {
            turn = -1;
            turnFinished();
        }
    }
}
