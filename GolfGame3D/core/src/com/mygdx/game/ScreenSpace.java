package com.mygdx.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScreenSpace extends Game {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    //public static boolean IS_MOBILE = false;

    public SpriteBatch batch;

    @Override
    public void create () {
        batch = new SpriteBatch();

        //if (Gdx.app.getType() == ApplicationType.Android || Gdx.app.getType() == ApplicationType.iOS)
        //    IS_MOBILE = true;
        // IS_MOBILE = true;

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