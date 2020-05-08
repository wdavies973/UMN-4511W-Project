package blokus;

import engine.Component;

import java.awt.*;

public class Board implements Component {

    // draw 4 PieceBanks with grid in the center
    // piecebank will be roughly 20%? of width/height

    private Grid grid = new Grid();

    @Override
    public void draw(Graphics2D g, int x, int y, int width, int height) {
        grid.draw(g, x, y, width, height);
    }

    @Override
    public void mouseMoved(int x, int y) {
        grid.mouseMoved(x, y);
    }
}
