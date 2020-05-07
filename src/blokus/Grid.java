package blokus;

import engine.Component;

import java.awt.*;

public class Grid implements Component {

    private static final int WIDTH_CELLS = 20, HEIGHT_CELLS = 20;

    // contains the color of a piece at the cell, defaults to null for nothing at that
    // cell
    public Color[][] cells = new Color[HEIGHT_CELLS][WIDTH_CELLS];



    @Override
    public void draw(Graphics2D g, int x, int y, int width, int height) {
        // Draw background
        g.setColor(Color.lightGray);
        g.fillRect(x, y, width, height);

        // Draw gridlines
        int cellWidthPX = width / WIDTH_CELLS;
        int cellHeightPX = height / HEIGHT_CELLS;

        // Draw horizontal gridlines
        g.setColor(Color.BLACK);
        for(int row = 0; row < HEIGHT_CELLS; row++) {
            g.drawLine(x, y + row * cellHeightPX, x + width, y + row * cellHeightPX);
        }

        // Draw vertical gridlines
        for(int col = 0; col < WIDTH_CELLS; col++) {
            g.drawLine(x + col * cellWidthPX, y, x + col * cellWidthPX, y + height);
        }

    }
}
