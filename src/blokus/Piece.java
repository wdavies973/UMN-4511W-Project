package blokus;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Piece {

    // a three by three grid, 1 where a block exists, 0 for none.
    // any shape should be representable with the correct values
    private int[][] shape = new int[5][5];

    private final int[][] shapeCopy; // shape copy that won't get rotated

    private static final int GRID_PADDING = 1;
    private static final int BANK_PADDING = 4;

    Color color;
    private int kind;

    private boolean placed; // helper variable for piece bank

    // this should setup what kind of piece this should be, you should come up with some sort of a system
    // that will assign the values in "shape" correctly. So for example, there are 21 pieces, so I should be able
    // to create each one of them using an id, or whatever you think is easy. the id will map to a bunch of
    // pre specified arrays for every piece in the game, and these arrays will be copied into "shape"
    public Piece(Color color, int kind) {
        this.color = color;
        this.kind = kind;

////          random
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if(Math.random() <= 0.5) {
                    shape[i][j] = 1;
                }
            }
        }

        // Copy the shape array
        shapeCopy = new int[5][5];

        for(int i = 0; i < shape.length; i++) {
            for(int j = 0; j < shape[i].length; j++) {
                shapeCopy[i][j] = shape[i][j];
            }
        }
    }

    // returns an arraylist containing every possible piece kind, just a convenience method,
    // return them from largest to smallest size
    public static ArrayList<Piece> CREATE_ALL_PIECES(Color color) {
        return new ArrayList<>();
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
    public boolean isValid(Color[][] grid, int cellX, int cellY) {
        return false;
    }

    // is there any valid location to put the piece? hint: use
    // isValid on every cell
    public boolean anyValid(Color[][] grid) {
        return false;
    }

    // Places the piece at position x,y on the map, the piece should be centered
    // at this location (i.e. shape[1][1] should correspond to x,y). Use the color
    // attribute of this class to set the grid position. This method should check
    // if the position is valid and if not should do nothing
    // returns whether a piece was actually placed or not
    public boolean place(Color[][] grid, int cellX, int cellY) {
        if(true) { // if you're about to return true, also set "placed" to true
            placed = true;
        }

        return true;
    }

    // return the number of 1s in the "shape" array
    public int getSize() {
        return 0;
    }

    // kind is used as an ID of sorts and needs to be accessed by other parts
    // of the program
    public int getKind() {
        return kind;
    }

    // ignore below here

    // cellX, cellY is center of the shape
    public void drawHover(Color[][] grid, Graphics2D g, int cellX, int cellY, int drawX, int drawY, int width, int height) {
        if(!isValid(grid, cellX, cellY)) {
            g.setColor(Color.darkGray);
        } else {
            g.setColor(color);
        }

        int x1 = Math.max(cellX - 2, 0);
        int x2 = Math.min(cellX + 2, 19);
        int y1 = Math.max(cellY - 2, 0);
        int y2 = Math.min(cellY + 2, 19);

        for(int row = y1; row <= y2; row++) {
            for(int col = x1; col <= x2; col++) {
                Rectangle rect = new Rectangle(drawX + col * width + GRID_PADDING, drawY + row * height + GRID_PADDING, width - GRID_PADDING, height - GRID_PADDING);

                if(shape[row - (cellY - 2)][col - (cellX - 2)] == 1) {
                    g.fillRect(rect.x, rect.y, rect.width, rect.height);
                }
            }
        }
    }

    public void drawInBank(Graphics2D g, int drawX, int drawY, int width, int height) {
        if(!placed) {
            g.setColor(color);
        } else {
            g.setColor(Color.darkGray);
        }

        drawX = drawX + BANK_PADDING / 2;
        drawY = drawY + BANK_PADDING / 2;
        width = width - BANK_PADDING;
        height = height - BANK_PADDING;

        int cellWidth = width / 5;
        int cellHeight = height / 5;

        for(int row = 0; row < 5; row++) {
            for(int col = 0; col < 5; col++) {
                if(shapeCopy[row][col] == 1)
                    g.fillRect(drawX + col * cellWidth, drawY + row * cellHeight, cellWidth, cellHeight);
            }
        }
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    public boolean isPlaced() {
        return placed;
    }
}
