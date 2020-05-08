package blokus;

import engine.Component;

import java.awt.*;
import java.util.ArrayList;

public class PieceBank implements Component {

    public enum Style {
        Vertical,
        Horizontal
    }

    private final ArrayList<Piece> pieces = new ArrayList<>();
    private final Style style;
    private boolean editable;

    public PieceBank(Color color, Style style, boolean editable) {
        this.style = style;

        for(int i = 0; i < 21; i++)
            pieces.add(new Piece(color, 0));
    }

    @Override
    public void draw(Graphics2D g, int x, int y, int width, int height) {
        if(style == Style.Horizontal) {
            int pieceHeight = height / 3;
            int pieceWidth = (int)Math.ceil(width / 7.0);

            // Draw horizontal gridlines
            g.setColor(Color.darkGray);
            for(int row = 0; row < 4; row++) {
                g.drawLine(x, y + row * pieceHeight, x + pieceWidth * 7, y + row * pieceHeight);
            }

            // Draw vertical gridlines
            for(int col = 0; col < 8; col++) {
                g.drawLine(x + col * pieceWidth, y, x + col * pieceWidth, y + height);
            }

            for(int i = 0; i < pieces.size(); i++) {
                Piece p = pieces.get(i);

                int row = i / 7;
                p.drawFull(g, x + (i % 7) * pieceWidth, y + row * pieceHeight, pieceWidth, pieceHeight, false);
            }
        } else if(style == Style.Vertical) {
            int pieceHeight = (int)Math.ceil(height / 7.0);
            int pieceWidth = width / 3;

            // Draw horizontal gridlines
            g.setColor(Color.darkGray);
            for(int row = 0; row < 8; row++) {
                g.drawLine(x, y + row * pieceHeight, x + pieceWidth * 3, y + row * pieceHeight);
            }

            // Draw vertical gridlines
            for(int col = 0; col < 4; col++) {
                g.drawLine(x + col * pieceWidth, y, x + col * pieceWidth, y + 7 * pieceHeight);
            }

            for(int i = 0; i < pieces.size(); i++) {
                Piece p = pieces.get(i);

                int col = i / 7;
                p.drawFull(g, x + col * pieceWidth, y + (i % 7) * pieceHeight, pieceWidth, pieceHeight, false);
            }
        }
    }

    @Override
    public void mouseMoved(int x, int y) {

    }

    @Override
    public void mouseRightClicked(int x, int y) {

    }

    @Override
    public void mouseClicked(int x, int y) {

    }
}
