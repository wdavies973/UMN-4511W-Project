package blokus;

import java.awt.*;

public class Action {

    public Piece piece; // null if unable to move
    public int rotation;
    public boolean flip;
    public int cellX, cellY;

    public boolean human;

    private Action() {

    }

    public Action(Piece piece, int cellX, int cellY) {
        this.piece = piece;
        this.cellX = cellX;
        this.cellY = cellY;
        this.human = true;
    }

    public Action(Piece piece, int rotation, boolean flip, int cellX, int cellY) {
        this.piece = piece;
        this.rotation = rotation;
        this.flip = flip;
        this.cellX = cellX;
        this.cellY = cellY;
    }

    public boolean perform(boolean simulated, Color[][] grid) {
        if(!human) piece.setLayout(flip, rotation);
        boolean result = piece.place(simulated, grid, cellX, cellY);
        if(!human) piece.reset();

        return result;
    }

    public int getKind () {
        return this.piece.getKind();
    }

    public void setAction(Piece piece, int rotation, boolean flip, int cellX, int cellY) {
        this.piece = piece;
        this.rotation = rotation;
        this.flip = flip;
        this.cellX = cellX;
        this.cellY = cellY;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public boolean isValid(Color[][] grid, int cellX, int cellY) {
        return this.piece.isValid(grid, cellX, cellY);
    }

    @Override
    public String toString() {
        return "Node{" +
                "piece=" + piece.getKind() +
                ", rotation=" + rotation +
                ", flip=" + flip +
                ", cellX=" + cellX +
                ", cellY=" + cellY +
                '}';
    }
}
