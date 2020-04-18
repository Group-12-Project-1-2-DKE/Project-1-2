package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ShootScreen extends Game {
    public static final int WIDTH = 200;
    public static final int HEIGHT = 300;

    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();

        this.setScreen(new Shoot(this));
    }
    @Override
    public void render () {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(WIDTH, HEIGHT);
    }
}