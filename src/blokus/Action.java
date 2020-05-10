package blokus;

public class Action {

    public Piece piece; // null if unable to move
    public int rotation;
    public boolean flip;
    public int cellX, cellY;

    private Action() {

    }

    public Action(Piece piece, int rotation, boolean flip, int cellX, int cellY) {
        this.piece = piece;
        this.rotation = rotation;
        this.flip = flip;
        this.cellX = cellX;
        this.cellY = cellY;
    }

    public boolean equals(Action move) {
        if(move.piece.getKind() != piece.getKind()) {
            return false;
        }

        // In this case, each piece is a reference to the same object, so use apply
        // to return the affect matrix
        int[][] matrix = move.piece.apply(true, flip, rotation);

        return piece.equals(matrix);
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
