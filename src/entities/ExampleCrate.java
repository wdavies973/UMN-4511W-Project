package entities;

import java.awt.*;

public class ExampleCrate extends Entity {

    public ExampleCrate() {
        super(100, 100);
    }

    boolean up = false, right = true;

    @Override
    public void tick() {
        if(up) {
            y -= 5;
        } else {
            y += 5;
        }

        if(right) {
            x += 5;
        } else {
            x -= 5;
        }

        if(x < 0) {
            right = true;
        } else if(x > 500) {
            right = false;
        }

        if(y < 0) {
            up = false;
        } else if(y > 500){
            up = true;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillRect(getDrawX(), getDrawY(), getDrawWidth(), getDrawHeight());
    }
}
