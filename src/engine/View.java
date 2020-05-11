package engine;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class View {

    private int x, y;
    private int width, height;

    private final ArrayList<View> children = new ArrayList<>();
    private final ArrayList<ViewWatcher> watchers = new ArrayList<>();

    // only the active watcher will get delivered events
    private int activeWatcher = -1;

    protected final void addChildren(View ... children) {
        this.children.addAll(Arrays.asList(children));
    }

    public final void addWatchers(ViewWatcher ... watchers) {
        this.watchers.addAll(Arrays.asList(watchers));
    }

    public void layout(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        layoutChildren(children, x, y, width, height);
    }

    public final void draw(Graphics2D g) {
        draw(g, x, y, width, height);

        // Draw all children
        for(View v : children) {
            v.draw(g);
        }
    }

    protected abstract void draw(Graphics2D g, int x, int y, int width, int height);

    public void setActiveWatcher(int activeWatcher) {
        this.activeWatcher = activeWatcher;
    }

    protected void layoutChildren(ArrayList<View> children, int x, int y, int width, int height) {

    }

    private boolean inBounds(View v, int x, int y) {
        return x >= v.getX() && x <= v.getX() + v.getWidth() && y >= v.getY() && y <= v.getY() + v.getHeight();
    }

    // relative to the view
    public void mouseMoved(int x, int y) {
        for(View v : children) {
            if(inBounds(v, x, y)) {
                v.mouseMoved(x - v.getX(), y - v.getY());
                break;
            }
        }
    }

    // relative to the view
    public void mouseRightClicked(int x, int y) {
        for(View v : children) {
            if(inBounds(v, x, y)) {
                v.mouseRightClicked(x - v.getX(), y - v.getY());
                break;
            }
        }

        if(activeWatcher != -1) {
            watchers.get(activeWatcher).mouseRightClicked(x, y);
        }
    }

    // relative to the view
    public void mouseClicked(int x, int y) {
        for(View v : children) {
            if(inBounds(v, x, y)) {
                v.mouseClicked(x - v.getX(), y - v.getY());
                break;
            }
        }

        if(activeWatcher != -1) {
            watchers.get(activeWatcher).mouseClicked(x, y);
        }
    }

    public void mouseMiddleClicked(int x, int y) {
        for(View v : children) {
            if(inBounds(v, x, y)) {
                v.mouseMiddleClicked(x - v.getX(), y - v.getY());
                break;
            }
        }

        if(activeWatcher != -1) {
            watchers.get(activeWatcher).mouseMiddleClicked(x, y);
        }
    }

    public void keyPressed(KeyEvent key) {
        for(View v : children) {
            v.keyPressed(key);
        }

        if(activeWatcher != -1) {
            watchers.get(activeWatcher).keyPressed(key);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
