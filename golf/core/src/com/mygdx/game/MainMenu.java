package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class MainMenu{
    MyGdxGame game;
    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    private static final int EXIT_WIDTH = 250;
    private static final int EXIT_HEIGHT = 120;
    private static final int PLAY_WIDTH = 300;
    private static final int PLAY_HEIGHT = 120;

    public MainMenu(MyGdxGame game){
    this.game = game;
    playButtonActive = new Texture("play_button_active.png");
    playButtonInactive = new Texture("play_button_inactive.png");
    exitButtonActive = new Texture("exit_button_active.png");
    exitButtonInactive = new Texture("exit_button_inactive.png");
    }

    public void show(){
    }

    public void render(){
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(exitButtonActive, x, EXITY, EXIT_WIDTH, EXIT_HEIGHT);
        game.batch.end();
    }
}
