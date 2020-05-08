package blokus;

import java.awt.*;
import java.util.ArrayList;

public class Piece {

    // a three by three grid, 1 where a block exists, 0 for none.
    // any shape should be representable with the correct values
    private final int[][] shape; //= new int[5][5];

    private final int[][] shapeCopy = new int [5][5]; // shape copy that won't get rotated

    private static final int GRID_PADDING = 1;
    private static final int BANK_PADDING = 4;

    private final Color color;
    private final int kind;

    private boolean placed; // helper variable for piece bank

    // the corner the piece must start at
    private int cornerX, cornerY;

    // this should setup what kind of piece this should be, you should come up with some sort of a system
    // that will assign the values in "shape" correctly. So for example, there are 21 pieces, so I should be able
    // to create each one of them using an id, or whatever you think is easy. the id will map to a bunch of
    // pre specified arrays for every piece in the game, and these arrays will be copied into "shape"
    public Piece(int cornerX, int cornerY, Color color, int kind) {
        this.cornerX = cornerX;
        this.cornerY = cornerY;
        this.color = color;
        this.kind = kind;

        AllPieces pieceInst = new AllPieces();
        shape = pieceInst.piece_type(kind);

        // Copy the shape
        for(int row = 0; row < shape.length; row++) {
            System.arraycopy(shape[row], 0, shapeCopy[row], 0, shape[row].length);
        }

    }

    // returns an arraylist containing every possible piece kind, just a convenience method,
    // return them from largest to smallest size
    public static ArrayList<Piece> CREATE_ALL_PIECES(Color color) {
        return new ArrayList<>();
    }

    // perform a matrix rotate transform on the "shape" 2D array
    public void rotateClockwise() {
        int[][] flipped = new int[5][5];

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                flipped[x][4-y] = shape[y][x];
                //flipped[y][x] = shape[x][4 - y];
            }
        }

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                shape[y][x] = flipped[y][x];
            }
        }
    }

    // perform a matrix rotate transform on the "shape" 2D array
    public void rotateCounterClockwise() {
        int[][] flipped = new int[5][5];

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                flipped[y][x] = shape[x][4 - y];
            }
        }

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                shape[y][x] = flipped[y][x];
            }
        }
    }

    // flips the x-coordinates of the given piece
    public void flip() {
        int[][] flipped = new int[5][5];

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                flipped[y][4-x] = shape[y][x];
            }
        }

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                shape[y][x] = flipped[y][x];
            }
        }
    }

    // determines if the current piece is in a corner
    public boolean isCorner (Color[][] grid, int cellX, int cellY) {
        boolean cornered = false;
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                if (shape[y][x] == 1) {
                    if ((cellX + x - 2) == 0 && (cellY + y - 2) == 0) {
                        cornered = true;
                    }
                    if ((cellX + x - 2) == 19 && (cellY + y - 2) == 0) {
                        cornered = true;
                    }
                    if ((cellX + x - 2) == 19 && (cellY + y - 2) == 19) {
                        cornered = true;
                    }
                    if ((cellX + x - 2) == 0 && (cellY + y - 2) == 19) {
                        cornered = true;
                    }
                }

            }
        }
        return cornered;
    }


    // returns whether the following placement of the piece is valid,
    // you'll need to access the grid.cells attribute to inspect cells,
    // the x,y coordinate will be the coordinate of the center of the shape array
    // overlayed with the map.
    public boolean isValid(Color[][] grid, int cellX, int cellY) {
        boolean connects = false;
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                if (shape[y][x] == 1) {
                    if ((cellX + x - 2) < 0 || (cellY + y - 2) < 0) { // outside of the borders of the board
                        return false;
                    }
                    if ((cellX + x - 2) >= 20 || (cellY + y - 2) >= 20) { // outside of the borders of the board
                        return false;
                    }

                    if (grid[cellY + y - 2][cellX + x - 2] != null) {   // laying on top of another piece
                        return false;
                    }

                    // if there is a piece of the same color directly below return false
                    if (cellY + y - 3 >= 0) {
                        if (grid[cellY + y - 3][cellX + x - 2] == color) {
                            return false;
                        }
                    }

                    // if there is a piece of the same color directly to the left side return false
                    if (cellX + x - 3 >= 0) {
                        if (grid[cellY + y - 2][cellX + x - 3] == color) {
                            return false;
                        }
                    }

                    // if there is a piece of the same color directly above return false
                    if (cellY + y - 1 < 20) {
                        if (grid[cellY + y - 1][cellX + x - 2] == color) {
                            return false;
                        }
                    }

                    // if there is a piece of the same color directly to the right side return false
                    if (cellX + x - 1 < 20) {
                        if (grid[cellY + y - 2][cellX + x - 1] == color) {
                            return false;
                        }
                    }

                    // This section sees if the piece is diagonally attached to any other pieces
                    if (!connects) {
                        if (y < 4 || x < 4) {
                            if (cellX + x - 1 < 20 && cellY + y - 1 < 20) { //lower right
                                if (grid[cellY + y - 1][cellX + x - 1] == color) {
                                    connects = true;
                                }
                            }
                            if (cellX + x - 3 >= 0 && cellY + y - 3 >= 0) { // upper left
                                if (grid[cellY + y - 3][cellX + x - 3] == color) {
                                    connects = true;
                                }
                            }
                            if (cellX + x - 1 < 20 && cellY + y - 3 >= 0) { //upper right
                                if (grid[cellY + y - 3][cellX + x - 1] == color) {
                                    connects = true;
                                }
                            }
                            if (cellX + x - 3 >= 0 && cellY + y - 1 < 20) { // lower left
                                if (grid[cellY + y - 1][cellX + x - 3] == color) {
                                    connects = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (isCorner(grid, cellX, cellY)) {
            connects = true;
        }
        if (!connects) { // if not diagonally connected return false
            return false;
        }
        return true;
    }

    // is there any valid location to put the piece? hint: use
    // isValid on every cell
    public boolean anyValid(Color[][] grid) {
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                if (isValid(grid, x, y)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Places the piece at position x,y on the map, the piece should be centered
    // at this location (i.e. shape[1][1] should correspond to x,y). Use the color
    // attribute of this class to set the grid position. This method should check
    // if the position is valid and if not should do nothing
    // returns whether a piece was actually placed or not
    boolean place(Color[][] grid, int cellX, int cellY) {
        if (!placed) {
            if (isValid(grid, cellX, cellY)) { // if you're about to return true, also set "placed" to true
                placed = true;

                for (int y = 0; y < 5; y++) {
                    for (int x = 0; x < 5; x++) {
                        if (shape[y][x] == 1) {
                            grid[cellY + y - 2][cellX + x - 2] = color;
                        }
                    }
                }
            }
        }

        return true;
    }

    // return the number of 1s in the "shape" array
    public int getSize() {
        int size = 0;

        for(int[] ints : shape) {
            for(int col = 0; col < shape.length; col++) {
                size += ints[col];
            }
        }

        return size;
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
