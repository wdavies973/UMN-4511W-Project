package blokus;

import engine.View;
import strategies.HumanStrategy;
import strategies.Strategy;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends View {

    private static final int TEXT_PADDING = 5;

    public enum Style {
        Top,
        Bottom,
        Left,
        Right
    }

    public interface Listener {
        void pieceSelected(Piece piece);
    }

    private final Listener listener;
    private final ArrayList<Piece> pieces = new ArrayList<>();
    private final Style style;

    private final String name;

    private boolean isTurn;

    private final Strategy strategy;

    private final Color turnHighlight = Color.green;

    private final BasicStroke basicStroke = new BasicStroke(5);

    private boolean outOfMoves;

    public Player(String name, Color color, Strategy strategy, Listener listener, Style style) {
        this.name = name;
        this.strategy = strategy;

        this.listener = listener;
        this.style = style;

        int cornerX, cornerY;
        if(style == Style.Bottom) {
            cornerX = cornerY = 19;
        } else if(style == Style.Right) {
            cornerX = 19;
            cornerY = 0;
        } else if(style == Style.Top) {
            cornerX = cornerY = 0;
        } else {
            cornerX = 0;
            cornerY = 19;
        }

        for(int i = 0; i < 21; i++)
            pieces.add(new Piece(cornerX, cornerY, color, i));
    }

    public int getScore() {
        int score = 0;

        for(Piece piece : pieces) {
            if(!piece.isPlaced()) {
                score += piece.getSize();
            }
        }

        return score;
    }

    public boolean startTurn(Grid grid) {
        // Get all possible moves
        ArrayList<Node> nodes = getAllPossibleMoves(grid.cells);

        System.out.println("Found "+nodes.size()+" potential moves");

        boolean canStart = nodes.size() > 0;

        if(canStart) strategy.turnStarted(grid, nodes);

        return canStart;
    }

    public boolean canPlay(Color[][] grid) {
        return getAllPossibleMoves(grid).size() > 0;
    }

    public ArrayList<Node> getAllPossibleMoves(Color[][] grid) {
        ArrayList<Node> moves = new ArrayList<>();

        for(Piece piece : pieces) {
            if(piece.isPlaced()) continue;

            moves.addAll(piece.getPossibleMoves(grid));
        }

        return moves;
    }

    public void draw(Graphics2D g, int x, int y, int width, int height) {
        if(style == Style.Top || style == Style.Bottom) {
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
                p.drawInBank(g, x + (i % 7) * pieceWidth, y + row * pieceHeight, pieceWidth, pieceHeight);
            }

            // Draw "is turn" indicator
            if(isTurn) {
                g.setColor(turnHighlight);
                Stroke s = g.getStroke();
                g.setStroke(basicStroke);
                g.drawRect(x - 2, y - 2, 7 * pieceWidth + 2, height + 2);
                g.setStroke(s);
            }
        } else if(style == Style.Left || style == Style.Right) {
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
                p.drawInBank(g, x + col * pieceWidth, y + (i % 7) * pieceHeight, pieceWidth, pieceHeight);
            }

            // Draw "is turn" indicator
            if(isTurn) {
                g.setColor(turnHighlight);
                Stroke s = g.getStroke();
                g.setStroke(basicStroke);
                g.drawRect(x - 2, y - 2, 3 * pieceWidth + 4, 7 * pieceHeight + 2);
                g.setStroke(s);
            }
        }

        // Draw the score indicator
        String label = name+" ("+getScore()+")" + (outOfMoves ? " - NO MOVES LEFT" : "");

        g.setColor(Color.BLACK);
        if(style == Style.Left) {
            g.drawString(label, x, y - TEXT_PADDING);
        } else if(style == Style.Right) {
            int aiWidth = g.getFontMetrics().stringWidth(label);

            g.drawString(label, x + width - aiWidth, y - TEXT_PADDING);
        } else if(style == Style.Top) {
            int aiWidth = g.getFontMetrics().stringWidth(label);

            g.drawString(label, x - aiWidth - TEXT_PADDING * 2, y + g.getFontMetrics().getHeight());
        } else if(style == Style.Bottom) {
            g.drawString(label, x + width + TEXT_PADDING * 2, y + height - TEXT_PADDING);
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(!(strategy instanceof HumanStrategy) || !isTurn) return;

        int pieceX, pieceY;

        int pieceWidth, pieceHeight;

        if(style == Style.Top || style == Style.Bottom) {
            pieceHeight = getHeight() / 3;
            pieceWidth = (int) Math.ceil(getWidth() / 7.0);

            pieceX = x / pieceWidth;
            pieceY = y / pieceHeight;

            pieceX = Math.min(pieceX, 6);
            pieceX = Math.max(pieceX, 0);
            pieceY = Math.min(pieceY, 2);
        } else {
            pieceHeight = (int) Math.ceil(getHeight() / 7.0);
            pieceWidth = getWidth() / 3;

            pieceX = x / pieceWidth;
            pieceY = y / pieceHeight;

            pieceX = Math.min(pieceX, 2);
            pieceX = Math.max(pieceX, 0);
            pieceY = Math.min(pieceY, 6);
        }
        pieceY = Math.max(pieceY, 0);

        int index;

        if(style == Style.Top || style == Style.Bottom) {
            index = pieceX + pieceY * 7;
        } else {
            index = pieceY + pieceX * 7;
        }

        if(!pieces.get(index).isPlaced()) {
            listener.pieceSelected(pieces.get(index));
        }
    }

    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    @Override
    public void keyPressed(KeyEvent key) {
        if(key.getKeyCode() == KeyEvent.VK_F12) {
            for(Piece c : pieces) {
                c.setPlaced(false);
            }
            outOfMoves = false;
        }
    }

    public void setOutOfMoves(boolean outOfMoves) {
        this.outOfMoves = outOfMoves;
    }
}
