package com.mygdx.game.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.ScreenSpace;

/**
 * Screen that allows the user to choose between different maze options.
 */
public class MainMenu implements Screen{
    final ScreenSpace game;

    // The different texture (picture) of the buttons.
    private Texture playButtonActive;
    private Texture playButtonInactive;
    private Texture exitButtonActive;
    private Texture exitButtonInactive;
    private Texture golfImage;
    private Texture optionButtonInactive;
    private Texture optionButtonActive;
    private Texture mazeButtonActive;
    private Texture mazeButtonInactive;

    private Stage stage;

    /**
     * Constructor method of the MainMenu object.
     * @param game ScreenSpace object on which the object is going to be rendered.
     */
    public MainMenu (ScreenSpace game) {
        this.game = game;

        // Create a new stage, where the buttons will be drawn.
        stage = new Stage();
        // Allow the user to interact with the stage (push on buttons).
        Gdx.input.setInputProcessor(stage);

        // The setting of the text (police, size, color)
        FreeTypeFontGenerator font1 = new FreeTypeFontGenerator(Gdx.files.internal("Courier_New.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter1.size = 40;
        BitmapFont font = font1.generateFont(parameter1);
        font.setColor(Color.WHITE);

        // Give to the variables a value (picture from assets folder).
        playButtonActive = new Texture("play_button_active.png");
        playButtonInactive = new Texture("play_button_inactive.png");
        exitButtonActive = new Texture("exit_button_active.png");
        exitButtonInactive = new Texture("exit_button_inactive.png");
        golfImage = new Texture ("golf_logo.png");
        optionButtonInactive = new Texture("gearwheelInActive.png");
        optionButtonActive = new Texture("gearwheelActive.png");
        mazeButtonActive = new Texture("mazeButtonActive.png");
        mazeButtonInactive = new Texture("mazeButtonInactive.png");
    }

    @Override
    public void show () {}

    @Override
    public void render (float delta) {
        // Set the color of the background.
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        // Draw a picture on the screen.
        int GOLF_IMAGE_WIDTH = 500;
        int GOLF_IMAGE_HEIGHT = 250;
        int GOLF_IMAGE_Y = 25;
        game.batch.draw(golfImage, ScreenSpace.WIDTH / 2 - GOLF_IMAGE_WIDTH / 2, GOLF_IMAGE_Y, GOLF_IMAGE_WIDTH, GOLF_IMAGE_HEIGHT);

        // Add the exit button.
        // Different position and size of the pictures
        int EXIT_WIDTH = 250;
        int x = ScreenSpace.WIDTH / 2 - EXIT_WIDTH / 2;
        int EXIT_HEIGHT = 120;
        int EXIT_Y = 300;
        if (Gdx.input.getX() < x + EXIT_WIDTH && Gdx.input.getX() > x){
            if(ScreenSpace.HEIGHT - Gdx.input.getY()< EXIT_Y + EXIT_HEIGHT && ScreenSpace.HEIGHT - Gdx.input.getY()> EXIT_Y) {
                // If the cursor is close to the button, draw the same button with a different color
                game.batch.draw(exitButtonActive, x, EXIT_Y, EXIT_WIDTH, EXIT_HEIGHT);
                // If the button is clicked, the program is going to exit.
                if (Gdx.input.isTouched()){
                    // If the button is pushed, exit the game
                    Gdx.app.exit();
                }
            } else {
                // If the cursor is not around the button, draw the inactivated button.
                game.batch.draw(exitButtonInactive, x, EXIT_Y, EXIT_WIDTH, EXIT_HEIGHT);
            }
        } else {
            // If the cursor is not around the button, draw the inactivated button.
            game.batch.draw(exitButtonInactive, x, EXIT_Y, EXIT_WIDTH, EXIT_HEIGHT);
        }

            // Add the Play button.
        int PLAY_WIDTH = 300;
        int y = ScreenSpace.WIDTH / 2 - PLAY_WIDTH / 2;
        int PLAY_HEIGHT = 120;
        int PLAY_Y = 400;
        if (Gdx.input.getX() < y + PLAY_WIDTH && Gdx.input.getX() > y){
            if ( ScreenSpace.HEIGHT - Gdx.input.getY() < PLAY_Y + PLAY_HEIGHT && ScreenSpace.HEIGHT - Gdx.input.getY() > PLAY_Y){
                // If the cursor is close to the button, draw the same button with a different color
                game.batch.draw(playButtonActive, y, PLAY_Y, PLAY_WIDTH, PLAY_HEIGHT);
                // If the play button is clicked, create an option screen.
                if (Gdx.input.isTouched()){
                    // If the button is pushed, dispose this window and create an OptionScreen.
                    this.dispose();
                    //game.setScreen(new OptionScreen(game));
                    game.setScreen(new OptionScreen(game));
                }
            } else {
                // If the cursor is not around the button, draw the inactivated button.
                game.batch.draw(playButtonInactive, y, PLAY_Y, PLAY_WIDTH, PLAY_HEIGHT);
            }
        } else {
            // If the cursor is not around the button, draw the inactivated button.
            game.batch.draw(playButtonInactive, y, PLAY_Y, PLAY_WIDTH, PLAY_HEIGHT);
        }

        // Add the option button.
        int z = 15;
        int OPTION_WIDTH = 200;
        int OPTION_HEIGHT = 175;
        int OPTION_Y = 600;
        if (Gdx.input.getX() < z + OPTION_WIDTH && Gdx.input.getX() > z){
            if(ScreenSpace.HEIGHT - Gdx.input.getY()< OPTION_Y + OPTION_HEIGHT && ScreenSpace.HEIGHT - Gdx.input.getY()> OPTION_Y) {
                // If the cursor is close to the button, draw the same button with a different color
                game.batch.draw(optionButtonActive, z, OPTION_Y, OPTION_WIDTH, OPTION_HEIGHT);
                // If the button is clicked, the program is going to exit.
                if (Gdx.input.isTouched()){
                    // If the button is pushed, dispose this window and create a GameMode window.
                    this.dispose();
                    game.setScreen(new GameMode(game));
                }
            } else {
                // If the cursor is not around the button, draw the inactivated button.
                game.batch.draw(optionButtonInactive, z, OPTION_Y, OPTION_WIDTH, OPTION_HEIGHT);
            }
        } else {
            // If the cursor is not around the button, draw the inactivated button.
            game.batch.draw(optionButtonInactive, z, OPTION_Y, OPTION_WIDTH, OPTION_HEIGHT);
        }

        // Add the maze button.
        int MAZE_WIDTH = 250;
        int w = (ScreenSpace.WIDTH / 2 - MAZE_WIDTH / 2) -5;
        int MAZE_HEIGHT = 90;
        int MAZE_Y = 540;
        if (Gdx.input.getX() < w + MAZE_WIDTH && Gdx.input.getX() > w){
            if ( ScreenSpace.HEIGHT - Gdx.input.getY() < MAZE_Y + MAZE_HEIGHT && ScreenSpace.HEIGHT - Gdx.input.getY() > MAZE_Y){
                // If the cursor is close to the button, draw the same button with a different color
                game.batch.draw(mazeButtonActive, w, MAZE_Y, MAZE_WIDTH, MAZE_HEIGHT);
                // If the play button is clicked, create an option screen.
                if (Gdx.input.isTouched()){
                    // If the button is pushed, dispose this window and create an OptionScreen.
                    this.dispose();
                    //game.setScreen(new OptionScreen(game));
                    game.setScreen(new MazeOption(game));
                }
            } else {
                // If the cursor is not around the button, draw the inactivated button.
                game.batch.draw(mazeButtonInactive, w, MAZE_Y, MAZE_WIDTH, MAZE_HEIGHT);
            }
        } else {
            // If the cursor is not around the button, draw the inactivated button.
            game.batch.draw(mazeButtonInactive, w, MAZE_Y, MAZE_WIDTH, MAZE_HEIGHT);
        }

        game.batch.end();

        stage.draw();
    }

    @Override
    public void resize (int width, int height) {}

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