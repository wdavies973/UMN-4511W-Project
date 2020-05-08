package blokus;

import java.awt.*;

public class Piece {

    // a three by three grid, 1 where a block exists, 0 for none.
    // any shape should be representable with the correct values
    private int[][] shape = new int[5][5];

    private static final int PADDING = 1;

    Color color;
    private int kind;

    // this should setup what kind of piece this should be, you should come up with some sort of a system
    // that will assign the values in "shape" correctly. So for example, there are 21 pieces, so I should be able
    // to create each one of them using an id, or whatever you think is easy. the id will map to a bunch of
    // pre specified arrays for every piece in the game, and these arrays will be copied into "shape"
    public Piece(Color color, int kind) {
        this.color = color;
        this.kind = kind;

        // draws the cross shape
        shape = new int[][]{
                {0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0},
        };
    }

    // perform a matrix rotate transform on the "shape" 2D array
    public void rotateClockwise() {

    }

    // perform a matrix rotate transform on the "shape" 2D array
    public void rotateCounterClockwise() {

    }

    // returns whether the following placement of the piece is valid,
    // you'll need to access the grid.cells attribute to inspect cells,
    // the x,y coordinate will be the coordinate of the center of the shape array
    // overlayed with the map.
    public boolean isValid(Grid grid, int x, int y) {
        return false;
    }

    // is there any valid location to put the piece? hint: use
    // isValid on every cell
    public boolean anyValid(Grid grid) {
        return false;
    }

    // Places the piece at position x,y on the map, the piece should be centered
    // at this location (i.e. shape[1][1] should correspond to x,y). Use the color
    // attribute of this class to set the grid position. This method should check
    // if the position is valid and if not should do nothing
    public void place(Grid grid, int x, int y) {

    }

    // return the number of 1s in the "shape" array
    public int getSize() {
        return 0;
    }

    // cellX, cellY is center of the shape
    public void drawHover(Graphics2D g, int cellX, int cellY, int drawX, int drawY, int width, int height) {
        g.setColor(color);

        int x1 = Math.max(cellX - 2, 0);
        int x2 = Math.min(cellX + 2, 19);
        int y1 = Math.max(cellY - 2, 0);
        int y2 = Math.min(cellY + 2, 19);

        for(int row = y1; row <= y2; row++) {
            for(int col = x1; col <= x2; col++) {
                Rectangle rect = new Rectangle(drawX + col * width + PADDING, drawY + row * height + PADDING, width - PADDING, height - PADDING);

                if(shape[row - (cellY - 2)][col - (cellX - 2)] == 1) {
                    g.fillRect(rect.x, rect.y, rect.width, rect.height);
                }
            }
        }

    }

}
