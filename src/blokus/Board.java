package blokus;

import engine.Component;

import java.awt.*;

public class Board implements Component {

    // draw 4 PieceBanks with grid in the center
    // piecebank will be roughly 20%? of width/height

    private Grid grid = new Grid();

    private PieceBank top = new PieceBank(Color.blue, PieceBank.Style.Horizontal, true);
    private PieceBank bottom = new PieceBank(Color.red, PieceBank.Style.Horizontal, true);
    private PieceBank left = new PieceBank(new Color(0, 100, 0), PieceBank.Style.Vertical, true);
    private PieceBank right = new PieceBank(Color.yellow, PieceBank.Style.Vertical, true);


    @Override
    public void draw(Graphics2D g, int x, int y, int width, int height) {
        g.setColor(Color.lightGray);
        g.fillRect(x, y, width, height);

        int gridSize = (int)(width * 0.58);

        int gridX = x + width / 2 - gridSize / 2;
        int gridY = y + height / 2 - gridSize / 2;

        int depth = (int)(gridSize * 0.8);
        int thickness = depth / 7 * 3;

        grid.draw(g, gridX, gridY, gridSize, gridSize);

        top.draw(g, gridX + gridSize / 2 - depth / 2, gridY / 2 - thickness / 2, depth, thickness); // top
        bottom.draw(g, gridX + gridSize / 2 - depth / 2, gridY + gridSize + gridY / 2 - thickness / 2, depth, thickness); // bottom

        left.draw(g, gridX / 2 - thickness / 2, gridY + gridSize / 2 - depth / 2, thickness, depth); // left
        right.draw(g, gridX + gridSize + gridX / 2 - thickness / 2, gridY + gridSize / 2 - depth / 2, thickness, depth); // right
    }

    @Override
    public void mouseMoved(int x, int y) {
        grid.mouseMoved(x, y);
    }

    @Override
    public void mouseRightClicked(int x, int y) {
        grid.mouseRightClicked(x, y);
    }

    @Override
    public void mouseClicked(int x, int y) {
        grid.mouseClicked(x, y);
    }
}
