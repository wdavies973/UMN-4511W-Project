package blokus;

import engine.View;

import java.awt.*;
import java.util.ArrayList;

// controls the overall game and state
public class Blokus extends View {

    // draw 4 PieceBanks with grid in the center
    // piecebank will be roughly 20%? of width/height

    private Grid grid = new Grid();

    private PieceBank top = new PieceBank(Color.blue, PieceBank.Style.Horizontal, true);
    private PieceBank bottom = new PieceBank(Color.red, PieceBank.Style.Horizontal, true);
    private PieceBank left = new PieceBank(new Color(0, 100, 0), PieceBank.Style.Vertical, true);
    private PieceBank right = new PieceBank(Color.yellow, PieceBank.Style.Vertical, true);

    public Blokus() {
        addChildren(grid, top, bottom, left, right);

        grid.setDrawDots(Color.blue, Color.yellow, Color.green, Color.red);
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
}
