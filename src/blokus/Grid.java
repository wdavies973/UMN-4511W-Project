package blokus;

import engine.Component;

import java.awt.*;

public class Grid implements Component {

    private static final int WIDTH_CELLS = 20, HEIGHT_CELLS = 20;

    private static final int PADDING = 1;

    // contains the color of a piece at the cell, defaults to null for nothing at that
    // cell
    public Color[][] cells = new Color[HEIGHT_CELLS][WIDTH_CELLS];

    private Piece inHand = new Piece(Color.blue, 0);

    private int mouseX, mouseY;

    @Override
    public void draw(Graphics2D g, int x, int y, int width, int height) {
        // Draw background
        g.setColor(Color.lightGray);
        g.fillRect(x, y, width, height);

        // Draw gridlines
        int cellWidthPX = width / WIDTH_CELLS;
        int cellHeightPX = height / HEIGHT_CELLS;

        // Draw horizontal gridlines
        g.setColor(Color.darkGray);
        for(int row = 0; row < HEIGHT_CELLS; row++) {
            g.drawLine(x, y + row * cellHeightPX, x + width, y + row * cellHeightPX);
        }

        // Draw vertical gridlines
        for(int col = 0; col < WIDTH_CELLS; col++) {
            g.drawLine(x + col * cellWidthPX, y, x + col * cellWidthPX, y + height);
        }

        // Draw all the cells
        for(int row = 0; row < HEIGHT_CELLS; row++) {
            for(int col = 0; col < WIDTH_CELLS; col++) {
                Rectangle rect = new Rectangle(x + col * cellWidthPX + PADDING, y + row * cellHeightPX + PADDING, cellWidthPX - PADDING, cellHeightPX - PADDING);

                if(cells[row][col] != null) {
                    g.setColor(cells[row][col]);
                    g.fillRect(rect.x, rect.y, rect.width, rect.height);
                }
            }
        }

        // Draw the in hand piece
        if(inHand != null) {
            int normalizedMouseX = mouseX - x;
            int normalizedMouseY = mouseY - y;

            // Translate the mouse screen position to a cell reference
            int hoverCellX = normalizedMouseX / cellWidthPX;
            int hoverCellY = normalizedMouseY / cellHeightPX;

            // bound it to within the bounds of the map
            hoverCellX = Math.min(hoverCellX, WIDTH_CELLS - 1);
            hoverCellX = Math.max(hoverCellX, 0);
            hoverCellY = Math.min(hoverCellY, HEIGHT_CELLS - 1);
            hoverCellY = Math.max(hoverCellY, 0);

            inHand.drawHover(cells, g, hoverCellX, hoverCellY, x, y, cellWidthPX, cellHeightPX);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        this.mouseX = x;
        this.mouseY = y;
    }

    @Override
    public void mouseRightClicked(int x, int y) {
        if(inHand != null) {
            inHand.rotateClockwise();
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(inHand != null) {
            inHand.place(cells, x, y);
        }
    }
}
