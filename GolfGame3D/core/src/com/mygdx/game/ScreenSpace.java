package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Menus.MainMenu;

public class ScreenSpace extends Game {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;

    public SpriteBatch batch;

    @Override
    public void create () {
        batch = new SpriteBatch();

        this.setScreen(new MainMenu(this));
    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}