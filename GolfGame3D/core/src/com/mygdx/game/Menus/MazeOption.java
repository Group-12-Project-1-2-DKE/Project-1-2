package com.mygdx.game.Menus;

import Reader.FileReaders;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.game.GolfGame;
import com.mygdx.game.ScreenSpace;
import com.mygdx.game.Variables;

public class MazeOption implements Screen {
    private ScreenSpace game;
    private Stage stage;
    private BitmapFont font;

    // The texture (picture) of the buttons.
    private Texture playButtonActive;
    private Texture playButtonInactive;
    private Texture backButtonActivated;
    private Texture backButtonInactivated;
    private Texture mazePicture;

    public MazeOption(ScreenSpace game) {
        this.game = game;

        Variables.maze = true;

        FileReaders reader = new FileReaders();
        reader.read("banana.txt");
        Variables.function = "1";

        // Create a new stage, where the buttons will be drawn.
        this.stage = new Stage();
        // Allow the user to interact with the stage (push on buttons).
        Gdx.input.setInputProcessor(stage);

        // The setting of the text (police, size, color)
        FreeTypeFontGenerator font1 = new FreeTypeFontGenerator(Gdx.files.internal("Courier_New.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter1.size = 40;
        font = font1.generateFont(parameter1);
        font.setColor(Color.WHITE);

        // Give to the variables a value (picture from assets folder).
        playButtonActive = new Texture("play_button_active.png");
        playButtonInactive = new Texture("play_button_inactive.png");
        backButtonActivated = new Texture("back_arrow_activated.png");
        backButtonInactivated = new Texture("back_arrow_inactivated.png");
        mazePicture = new Texture("mazeLogo.png");

        // Create the skin of the textfields
        Skin customizedMenuSkin = new Skin(Gdx.files.internal("uiskin.json"));
        // Create the textFields and add them to the "frame".
        // The textfields on which the user will enter its values.
        TextField mazeX = new TextField(String.valueOf(10), customizedMenuSkin);
        mazeX.setPosition(25,675);
        mazeX.setSize(200, 30);
        mazeX.setColor(Color.WHITE);
        stage.addActor(mazeX);

        TextField mazeY = new TextField(String.valueOf(10), customizedMenuSkin);
        mazeY.setPosition(25,525);
        mazeY.setSize(200, 30);
        mazeY.setColor(Color.WHITE);
        stage.addActor(mazeY);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        // Set the color of the background.
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        // Write on the screen the different elements.
        font.draw(game.batch,"Maze length x:", 25,750);
        font.draw(game.batch,"Maze length y:", 25,600);

        int MAZE_X = 375;
        int MAZE_WIDTH = 300;
        int MAZE_HEIGHT = 300;
        game.batch.draw(mazePicture, MAZE_X, 250 , MAZE_WIDTH, MAZE_HEIGHT);

        // Create the play button.
        int x = ScreenSpace.WIDTH / 2;
        // The position and size of the pictures.
        int PLAY_WIDTH = 300;
        int PLAY_HEIGHT = 120;
        int PLAY_Y = 75;
        if (Gdx.input.getX() < x + PLAY_WIDTH && Gdx.input.getX() > x && ScreenSpace.HEIGHT - Gdx.input.getY() < PLAY_Y + PLAY_HEIGHT && ScreenSpace.HEIGHT - Gdx.input.getY() > PLAY_Y){
            // If the cursor is around the button, draw the activated button.
            game.batch.draw(playButtonActive, x, PLAY_Y, PLAY_WIDTH, PLAY_HEIGHT);
            if (Gdx.input.isTouched()){
                this.dispose();
                Variables.function = "1";
                game.setScreen(new GolfGame(game));

            }
        } else {
            // If the cursor is not around the button, draw the inactivated button.
            game.batch.draw(playButtonInactive, x, PLAY_Y, PLAY_WIDTH, PLAY_HEIGHT);
        }

        // Create the go back button.
        int z = 35;
        int BACK_WIDTH = 125;
        int BACK_HEIGHT = 125;
        if (Gdx.input.getX() < z + BACK_WIDTH && Gdx.input.getX() > z && ScreenSpace.HEIGHT - Gdx.input.getY() < PLAY_Y + BACK_HEIGHT && ScreenSpace.HEIGHT - Gdx.input.getY() > PLAY_Y){
            // If the cursor is around the button, draw the activated button.
            game.batch.draw(backButtonActivated, z, PLAY_Y, BACK_WIDTH, BACK_HEIGHT);
            // If the button id clicked, go back to the main menu.
            if (Gdx.input.isTouched()){
                // If the button is pushed, dispose this screen and go back to the main page.
                this.dispose();
                game.setScreen(new MainMenu(game));
            }
        } else {
            // If the cursor is not around the button, draw the inactivated button.
            game.batch.draw(backButtonInactivated, z, PLAY_Y, BACK_WIDTH, BACK_HEIGHT);
        }

        game.batch.end();

        // draw the stage containing some buttons.
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}