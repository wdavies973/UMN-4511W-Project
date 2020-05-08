package engine;

import java.awt.*;

public interface Component {

    void draw(Graphics2D g, int x, int y, int width, int height);

    // normalized to the width and height of the component
    void mouseMoved(int x, int y);

}
