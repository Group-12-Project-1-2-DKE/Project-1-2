package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class MainMenu implements Screen{
    private static final int EXIT_WIDTH = 250;
    private static final int EXIT_HEIGHT = 120;
    private static final int PLAY_WIDTH = 300;
    private static final int PLAY_HEIGHT = 120;
    private static final int EXIT_Y = 400;
    private static final int PLAY_Y = 600;
    private static final int GOLF_IMAGE_WIDTH = 500;
    private static final int GOLF_IMAGE_HEIGHT = 250;
    private static final int GOLF_IMAGE_Y = 100;
    final ScreenSpace game;

    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture golfImage;

    public MainMenu (final ScreenSpace game) {
        this.game = game;

        // Give to the variables a value (picture from assets folder).
        playButtonActive = new Texture("play_button_active.png");
        playButtonInactive = new Texture("play_button_inactive.png");
        exitButtonActive = new Texture("exit_button_active.png");
        exitButtonInactive = new Texture("exit_button_inactive.png");
        golfImage = new Texture ("golf_logo.png");
    }

    @Override
    public void show () {

    }

    @Override
    public void render (float delta) {
        // Change the color of the background.
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        // Draw a picture on the screen.
        game.batch.draw(golfImage, ScreenSpace.WIDTH / 2 - GOLF_IMAGE_WIDTH / 2, GOLF_IMAGE_Y, GOLF_IMAGE_WIDTH, GOLF_IMAGE_HEIGHT);

        // Add the exit button.
        int x = ScreenSpace.WIDTH / 2 - EXIT_WIDTH / 2;
        if (Gdx.input.getX() < x + EXIT_WIDTH && Gdx.input.getX() > x){
            if(ScreenSpace.HEIGHT - Gdx.input.getY()< EXIT_Y + EXIT_HEIGHT && ScreenSpace.HEIGHT - Gdx.input.getY()> EXIT_Y) {
                game.batch.draw(exitButtonActive, x, EXIT_Y, EXIT_WIDTH, EXIT_HEIGHT);
                // If the button is clicked, the program is going to exit.
                if (Gdx.input.isTouched()){
                    Gdx.app.exit();
                }
            } else {
                game.batch.draw(exitButtonInactive, x, EXIT_Y, EXIT_WIDTH, EXIT_HEIGHT);
            }
        } else {
            game.batch.draw(exitButtonInactive, x, EXIT_Y, EXIT_WIDTH, EXIT_HEIGHT);
        }

            // Add the Play button.
        int y = ScreenSpace.WIDTH / 2 - PLAY_WIDTH / 2;
        if (Gdx.input.getX() < y + PLAY_WIDTH && Gdx.input.getX() > y){
            if ( ScreenSpace.HEIGHT - Gdx.input.getY() < PLAY_Y + PLAY_HEIGHT && ScreenSpace.HEIGHT - Gdx.input.getY() > PLAY_Y){
                game.batch.draw(playButtonActive, y, PLAY_Y, PLAY_WIDTH, PLAY_HEIGHT);
                // If the play button is clicked, create an option screen.
                if (Gdx.input.isTouched()){
                    this.dispose();
                    game.setScreen(new OptionScreen(game));
                }
            } else {
                game.batch.draw(playButtonInactive, y, PLAY_Y, PLAY_WIDTH, PLAY_HEIGHT);
            }
        } else {
            game.batch.draw(playButtonInactive, y, PLAY_Y, PLAY_WIDTH, PLAY_HEIGHT);
        }
        game.batch.end();
    }

    @Override
    public void resize (int width, int height) {

    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
    }
}