package com.mygdx.game.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.ScreenSpace;

public class Congrat implements Screen {
    // Dimensions of the pictures and the buttons that are in this screen.
    private static final int OVER_IMAGE_WIDTH = 500;
    private static final int OVER_IMAGE_HEIGHT = 350;
    private static final int EXIT_WIDTH = 250;
    private static final int EXIT_HEIGHT = 120;
    private static final int EXIT_Y = 50;
    private static final int REPLAY_WIDTH = 300;
    private static final int REPLAY_HEIGHT = 120;
    private static final int REPLAY_Y = 50;

    // Number of times the user pushed the button shoot.
    private int attempt;

    final ScreenSpace game;
    BitmapFont font;

    // Pictures used in the object.
    Texture gameOverPicture;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture replayButtonActive;
    Texture replayButtonInactive;

    /**
     * Constructor method of the object.
     * @param game Element on which the object is going to be rendered.
     * @param attempts The number of attempts to reach the goal.
     */
    public Congrat(ScreenSpace game, int attempts){
        this.game = game;
        this.attempt = attempts;
        gameOverPicture = new Texture ("congratulation.png");
        exitButtonActive = new Texture("exit_logo_active.png");
        exitButtonInactive = new Texture("exit_logo_inactive.png");
        replayButtonActive = new Texture("replay_button_active.png");
        replayButtonInactive = new Texture ("replay_button_inactive.png");

        // "Police" of the text on the screen.
        FreeTypeFontGenerator font1 = new FreeTypeFontGenerator(Gdx.files.internal("Courier_New.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter1.size = 80;
        font = font1.generateFont(parameter1);
        font.setColor(Color.WHITE);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        
        game.batch.draw(gameOverPicture, ScreenSpace.WIDTH / 2 - OVER_IMAGE_WIDTH / 2, 450, OVER_IMAGE_WIDTH, OVER_IMAGE_HEIGHT);

        font.draw(game.batch,"You shot :", 170,450);
        font.draw(game.batch, Integer.toString(attempt) , ScreenSpace.WIDTH / 2,350);
        font.draw(game.batch,"times !!", 200,250);

        // Create and display the exit button.
        int x = ScreenSpace.WIDTH -350;
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

        // Create and display the replay button.
        int y = 75;
        if (Gdx.input.getX() < y + REPLAY_WIDTH && Gdx.input.getX() > y){
            if ( ScreenSpace.HEIGHT - Gdx.input.getY() < REPLAY_Y + REPLAY_HEIGHT && ScreenSpace.HEIGHT - Gdx.input.getY() > REPLAY_Y){
                game.batch.draw(replayButtonActive, y, REPLAY_Y, REPLAY_WIDTH, REPLAY_HEIGHT);
                // If the replay button is clicked, create an option screen.
                if (Gdx.input.isTouched()){
                    this.dispose();
                    game.setScreen(new OptionScreen(game));
                }
            } else {
                game.batch.draw(replayButtonInactive, y, REPLAY_Y, REPLAY_WIDTH, REPLAY_HEIGHT);
            }
        } else {
            game.batch.draw(replayButtonInactive, y, REPLAY_Y, REPLAY_WIDTH, REPLAY_HEIGHT);
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}