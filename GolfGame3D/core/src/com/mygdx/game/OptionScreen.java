package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import Reader.FileReaders;


public class OptionScreen implements Screen {
    private ScreenSpace game;

    // The texture (picture) of the buttons.
    private Texture playButtonActive;
    private Texture playButtonInactive;
    private Texture backButtonActivated;
    private Texture backButtonInactivated;

    // The position and size of the pictures.
    private static final int PLAY_WIDTH = 300;
    private static final int PLAY_HEIGHT = 120;
    private static final int BACK_WIDTH = 125;
    private static final int BACK_HEIGHT = 125;
    private static final int PLAY_Y = 75;

    private BitmapFont font;

    // The textfields on which the user will enter its values.
    private TextField gravity;
    private TextField ballMass;
    private TextField tolerance;
    private TextField function;
    private TextField goalX;
    private TextField goalY;
    private TextField startX;
    private TextField startY;

    private Stage stage;

    public GolfGame hold;
    private FileReaders reader;


    public OptionScreen(ScreenSpace game){
        this.game = game;

        // Create a reader object and make it read a certain file with the default values on it.
        reader = new FileReaders();
        reader.read("banana.txt");

        // Give to the variables a value (picture from assets folder).
        playButtonActive = new Texture("play_button_active.png");
        playButtonInactive = new Texture("play_button_inactive.png");
        backButtonActivated = new Texture("back_arrow_activated.png");
        backButtonInactivated = new Texture("back_arrow_inactivated.png");
        stage = new Stage();

        // Allows the user to interact with the stage (click on buttons in this case).
        Gdx.input.setInputProcessor(stage);

        // Create the textFields and add them to the "frame".
        Skin customizedMenuSkin = new Skin(Gdx.files.internal("uiskin.json"));
        gravity = new TextField(String.valueOf(Variables.gravity), customizedMenuSkin);
        gravity.setPosition(ScreenSpace.WIDTH/2,725);
        gravity.setSize(200, 30);
        gravity.setColor(Color.WHITE);
        stage.addActor(gravity);

        ballMass = new TextField(String.valueOf(Variables.ballMass), customizedMenuSkin);
        ballMass.setPosition(ScreenSpace.WIDTH/2,650);
        ballMass.setSize(200, 30);
        ballMass.setColor(Color.WHITE);
        stage.addActor(ballMass);

        tolerance = new TextField(String.valueOf(Variables.tolerance), customizedMenuSkin);
        tolerance.setPosition(ScreenSpace.WIDTH/2,575);
        tolerance.setSize(200, 30);
        tolerance.setColor(Color.WHITE);
        stage.addActor(tolerance);

        //0.02*x^2 + 0.02*y^2
        function = new TextField(Variables.function, customizedMenuSkin);
        function.setPosition(ScreenSpace.WIDTH/2,500);
        function.setSize(200, 30);
        function.setColor(Color.WHITE);
        stage.addActor(function);

        goalX = new TextField(String.valueOf(Variables.goalX), customizedMenuSkin);
        goalX.setPosition(25,375);
        goalX.setSize(200, 30);
        goalX.setColor(Color.WHITE);
        stage.addActor(goalX);

        goalY = new TextField(String.valueOf(Variables.goalY), customizedMenuSkin);
        goalY.setPosition(250,375);
        goalY.setSize(200, 30);
        goalY.setColor(Color.WHITE);
        stage.addActor(goalY);

        startX = new TextField(String.valueOf(Variables.startX), customizedMenuSkin);
        startX.setPosition(25,275);
        startX.setSize(200, 30);
        startX.setColor(Color.WHITE);
        stage.addActor(startX);

        startY = new TextField(String.valueOf(Variables.startY), customizedMenuSkin);
        startY.setPosition(250,275);
        startY.setSize(200, 30);
        startY.setColor(Color.WHITE);
        stage.addActor(startY);

        // The setting of the text (police, size, color).
        FreeTypeFontGenerator font1 = new FreeTypeFontGenerator(Gdx.files.internal("Courier_New.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter1.size = 40;
        font = font1.generateFont(parameter1);
        font.setColor(Color.WHITE);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // Set the color of the background.
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        // Write on the screen the different elements.
        font.draw(game.batch,"Gravity:", 25,750);
        font.draw(game.batch,"Ball Mass:", 25,675);
        font.draw(game.batch,"tolerance:", 25,600);
        font.draw(game.batch,"Function:", 25,525);
        font.draw(game.batch,"Goal Coordinates(x,y):", 25,450);
        font.draw(game.batch,"Start Coordinates (x,y):", 25,350);

        // Create the play button.
        int x = ScreenSpace.WIDTH / 2;
        if (Gdx.input.getX() < x + PLAY_WIDTH && Gdx.input.getX() > x && ScreenSpace.HEIGHT - Gdx.input.getY() < PLAY_Y + PLAY_HEIGHT && ScreenSpace.HEIGHT - Gdx.input.getY() > PLAY_Y){
            // If the cursor is around the button, draw the activated button.
            game.batch.draw(playButtonActive, x, PLAY_Y, PLAY_WIDTH, PLAY_HEIGHT);
            if (Gdx.input.isTouched()){
                Variables.gravity = Float.parseFloat(gravity.getText());
                Variables.ballMass = Float.parseFloat(ballMass.getText());
                Variables.tolerance = Float.parseFloat(tolerance.getText());
                Variables.function = function.getText();
                Variables.goalX = Float.parseFloat(goalX.getText());
                Variables.goalY = Float.parseFloat(goalY.getText());
                Variables.startX = Float.parseFloat(startX.getText());
                Variables.startY = Float.parseFloat(startY.getText());


                this.dispose();
                game.setScreen(new GolfGame(game));

            }
        } else {
            // If the cursor is not around the button, draw the inactivated button.
            game.batch.draw(playButtonInactive, x, PLAY_Y, PLAY_WIDTH, PLAY_HEIGHT);
        }

        // Create the go back button.
        int z = 35;
        if (Gdx.input.getX() < z + BACK_WIDTH && Gdx.input.getX() > z && ScreenSpace.HEIGHT - Gdx.input.getY() < PLAY_Y + BACK_HEIGHT && ScreenSpace.HEIGHT - Gdx.input.getY() > PLAY_Y){
            // If the cursor is around the button, draw the activated button.
            game.batch.draw(backButtonActivated, z, PLAY_Y, BACK_WIDTH, BACK_WIDTH);
            // If the button id clicked, go back to the main menu.
            if (Gdx.input.isTouched()){
                // If the button is pushed, dispose this screen and go back to the main page.
                this.dispose();
                game.setScreen(new MainMenu(game));
            }
        } else {
            // If the cursor is not around the button, draw the inactivated button.
            game.batch.draw(backButtonInactivated, z, PLAY_Y, BACK_WIDTH, BACK_WIDTH);
        }

        game.batch.end();

        // draw the stage containing some buttons.
        stage.draw();
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