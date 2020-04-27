package main;

import entities.ExampleCrate;

import java.awt.*;

public class GameState {

    ExampleCrate crate = new ExampleCrate();

    public void tick() {
        crate.tick();
    }

    public void draw(Graphics2D g) {
        crate.draw(g);
    }
    
}
