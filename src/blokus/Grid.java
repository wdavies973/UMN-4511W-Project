package blokus;

import engine.View;

import java.awt.*;

public class Grid extends View {

    private static final int WIDTH_CELLS = 20, HEIGHT_CELLS = 20;

    private static final int PADDING = 1;

    // contains the color of a piece at the cell, defaults to null for nothing at that
    // cell
    public Color[][] cells = new Color[HEIGHT_CELLS][WIDTH_CELLS];

    private Piece inHand = new Piece(Color.blue, 0);

    private int mouseX, mouseY;

    private boolean drawDots;
    private Color dotTopLeft, dotTopRight, dotBottomLeft, dotBottomRight;

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
        for(int row = 0; row < HEIGHT_CELLS + 1; row++) {
            g.drawLine(x, y + row * cellHeightPX, x + WIDTH_CELLS * cellWidthPX, y + row * cellHeightPX);
        }

        // Draw vertical gridlines
        for(int col = 0; col < WIDTH_CELLS + 1; col++) {
            g.drawLine(x + col * cellWidthPX, y, x + col * cellWidthPX, y + HEIGHT_CELLS * cellHeightPX);
        }

        // Draw all the cells
        for(int row = 0; row < HEIGHT_CELLS; row++) {
            for(int col = 0; col < WIDTH_CELLS; col++) {
                Rectangle rect = new Rectangle(x + col * cellWidthPX + PADDING, y + row * cellHeightPX + PADDING, cellWidthPX - PADDING, cellHeightPX - PADDING);

                if(drawDots) {
                    int dotSize = cellWidthPX / 2;

                    Color dotColor = null;

                    if(row == 0 && col == 0) {
                        dotColor = dotTopLeft;
                    } else if(row == HEIGHT_CELLS - 1 && col == 0) {
                        dotColor = dotBottomLeft;
                    } else if(row == 0 && col == WIDTH_CELLS - 1) {
                        dotColor = dotTopRight;
                    } else if(row == HEIGHT_CELLS - 1 && col == WIDTH_CELLS - 1){
                        dotColor = dotBottomRight;
                    }

                    if(dotColor != null) {
                        g.setColor(dotColor);
                        g.fillOval(x + col * cellWidthPX + cellWidthPX / 2 - dotSize / 2, y + row * cellHeightPX + cellHeightPX / 2 - dotSize / 2, dotSize, dotSize);
                    }
                }

                if(cells[row][col] != null) {
                    g.setColor(cells[row][col]);
                    g.fillRect(rect.x, rect.y, rect.width, rect.height);
                }
            }
        }

        // Draw the in hand piece
        if(inHand != null) {
            // Translate the mouse screen position to a cell reference
            int hoverCellX = mouseX / cellWidthPX;
            int hoverCellY = mouseY / cellHeightPX;

            // bound it to within the bounds of the map
            hoverCellX = Math.min(hoverCellX, WIDTH_CELLS - 1);
            hoverCellX = Math.max(hoverCellX, 0);
            hoverCellY = Math.min(hoverCellY, HEIGHT_CELLS - 1);
            hoverCellY = Math.max(hoverCellY, 0);

            inHand.drawHover(cells, g, hoverCellX, hoverCellY, x, y, cellWidthPX, cellHeightPX);
        }
    }

    public void setDrawDots(Color topLeft, Color topRight, Color bottomLeft, Color bottomRight) {
        this.dotTopLeft = topLeft;
        this.dotTopRight = topRight;
        this.dotBottomLeft = bottomLeft;
        this.dotBottomRight = bottomRight;
        this.drawDots = true;
    }

    public void setDrawDots(boolean drawDots) {
        this.drawDots = drawDots;
    }

    public void setInHand(Piece inHand) {
        this.inHand = inHand;
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
            int cellWidthPX = getWidth() / WIDTH_CELLS;
            int cellHeightPX = getHeight() / HEIGHT_CELLS;

            // Translate the mouse screen position to a cell reference
            int cellX = mouseX / cellWidthPX;
            int cellY = mouseY / cellHeightPX;

            // bound it to within the bounds of the map
            cellX = Math.min(cellX, WIDTH_CELLS - 1);
            cellX = Math.max(cellX, 0);
            cellY = Math.min(cellY, HEIGHT_CELLS - 1);
            cellY = Math.max(cellY, 0);

            inHand.place(cells, cellX, cellY);
        }
    }
}
