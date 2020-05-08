package engine;

import blokus.Blokus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private int width, height;

    private Thread thread;
    private Graphics2D g;
    private BufferedImage image; // the image the game's graphics are drawn to
    private static final long TARGET_TIME = 1000 / 144; // denominator is FPS you want the game to run at
    private volatile boolean running;

    private final Blokus blokus;

    public GamePanel(int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        requestFocus();

        blokus = new Blokus();
        blokus.layout(0, 0, width, height);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if(thread == null) {
            thread = new Thread(this);
            requestFocus();
            addMouseListener(this);
            addMouseMotionListener(this);
            addKeyListener(this);
            thread.start();
        }
    }

    public void init() {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        running = true;
    }

    public void notifyResize(int width, int height) {
        this.width = width;
        this.height = height;

        blokus.layout(0, 0, width, height);
        init();
    }

    @Override
    public void run() {
        init();

        long start, elapsed, wait;

        while(running) {
            start = System.nanoTime();

            // Clear the screen
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, width, height);

            // draw board
            blokus.draw(g);

            Toolkit.getDefaultToolkit().sync();

            // Draw to screen
            Graphics screen = getGraphics();
            screen.drawImage(image, 0, 0, width, height, null);
            screen.dispose();

            elapsed = System.nanoTime() - start;
            wait = TARGET_TIME - elapsed / 1000000;
            if(wait < 0) wait = 0;
            try {
                //noinspection BusyWait
                Thread.sleep(wait);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void kill() {
        running = false;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        blokus.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            blokus.mouseClicked(e.getX(), e.getY());
        } else if(e.getButton() == MouseEvent.BUTTON3) {
            blokus.mouseRightClicked(e.getX(), e.getY());
        } else if(e.getButton() == MouseEvent.BUTTON2) {
            blokus.mouseMiddleClicked(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        blokus.mouseMoved(e.getX(), e.getY());
    }
}
