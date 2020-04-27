package entities;

import java.awt.*;

public abstract class Entity {

    public Entity(double width, double height) {
        this.width = width;
        this.height = height;
    }

    // Top left corner of entity
    protected double x, y;

    // Width and height of entity
    private final double width;
    private final double height;

    public abstract void tick();

    public abstract void draw(Graphics2D g);

    public int getDrawX() {
        return (int)x;
    }

    public int getDrawY() {
        return (int)y;
    }

    public int getDrawWidth() {
        return (int)width;
    }

    public int getDrawHeight() {
        return (int)height;
    }
}
