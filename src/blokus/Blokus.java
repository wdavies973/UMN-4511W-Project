package blokus;

import engine.View;

import java.awt.*;
import java.util.ArrayList;

// controls the overall game and state
public class Blokus extends View implements PieceBank.Listener {

    // draw 4 PieceBanks with grid in the center
    // piecebank will be roughly 20%? of width/height

    private Grid grid = new Grid();

    private Color betterGreen = new Color(0, 100, 0);

    private PieceBank top = new PieceBank(this, Color.blue, PieceBank.Style.Horizontal, true);
    private PieceBank bottom = new PieceBank(this, Color.red, PieceBank.Style.Horizontal, true);
    private PieceBank left = new PieceBank(this, betterGreen, PieceBank.Style.Vertical, true);
    private PieceBank right = new PieceBank(this, Color.yellow, PieceBank.Style.Vertical, true);

    public Blokus() {
        addChildren(grid, top, bottom, left, right);

        grid.setDrawDots(Color.blue, Color.yellow, betterGreen, Color.red);
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
}
