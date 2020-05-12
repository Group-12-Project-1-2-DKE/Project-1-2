package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainMenu implements Screen{
    private static final int EXIT_WIDTH = 250;
    private static final int EXIT_HEIGHT = 120;
    private static final int PLAY_WIDTH = 300;
    private static final int PLAY_HEIGHT = 120;
    private static final int EXIT_Y = 325;
    private static final int PLAY_Y = 450;
    private static final int GOLF_IMAGE_WIDTH = 500;
    private static final int GOLF_IMAGE_HEIGHT = 250;
    private static final int GOLF_IMAGE_Y = 50;
    final ScreenSpace game;

    private Texture playButtonActive;
    private Texture playButtonInactive;
    private Texture exitButtonActive;
    private Texture exitButtonInactive;
    private Texture golfImage;

    private TextButton button1;
    private TextButton button2;
    private TextButton.TextButtonStyle textButtonStyle;

    private BitmapFont font;

    private Stage stage;

    public MainMenu (ScreenSpace game) {
        this.game = game;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        FreeTypeFontGenerator font1 = new FreeTypeFontGenerator(Gdx.files.internal("Courier_New.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter1.size = 40;
        font = font1.generateFont(parameter1);
        font.setColor(Color.WHITE);

        // Give to the variables a value (picture from assets folder).
        playButtonActive = new Texture("play_button_active.png");
        playButtonInactive = new Texture("play_button_inactive.png");
        exitButtonActive = new Texture("exit_button_active.png");
        exitButtonInactive = new Texture("exit_button_inactive.png");
        golfImage = new Texture ("golf_logo.png");

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        // Create the buttons.
        button1 = new TextButton("AI", textButtonStyle);
        button2 = new TextButton("Single player", textButtonStyle);
        button1.setPosition(50,  650);
        button2.setPosition(ScreenSpace.WIDTH/2-30,  650);
        stage.addActor(button1);
        stage.addActor(button2);

        // Set the default values.
        button1.getLabel().setColor(Color.YELLOW);
        //button2.getLabel().setColor(Color.YELLOW);
        //Variables.euler = true;
        //Variables.rungeKutta = false;

        // Create the actionListener and add them to the specific button.
        button1.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                // If the button is clicked, mark the physic engine mode as true and the other as false
                // also change the color to see the difference.
                Variables.ai = true;
                Variables.singlePlayer = false;
                button1.getLabel().setColor(Color.YELLOW);
                button2.getLabel().setColor(Color.WHITE);
                System.out.println("hi hi");
            }
        });
        button2.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                // If the button is clicked, mark the physic engine mode as true and the other as false
                // also change the color to see the difference.
                Variables.singlePlayer = true;
                Variables.ai = false;
                button2.getLabel().setColor(Color.YELLOW);
                button1.getLabel().setColor(Color.WHITE);
                System.out.println("hi");
            }
        });
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

        font.draw(game.batch,"Game Mode:", 25,750);

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

        stage.draw();
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