package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import ui.FileReaders;


public class OptionScreen implements Screen {
    private TextButton button1;
    private TextButton button2;
    private TextButton.TextButtonStyle textButtonStyle;
    private ScreenSpace game;
    private Texture playButtonActive;
    private Texture playButtonInactive;
    private Texture backButtonActivated;
    private Texture backButtonInactivated;

    private static final int PLAY_WIDTH = 300;
    private static final int PLAY_HEIGHT = 120;
    private static final int BACK_WIDTH = 125;
    private static final int BACK_HEIGHT = 125;
    private static final int PLAY_Y = 75;

    private BitmapFont font;

    private TextField gravity;
    //TextField ballDiameter;
    private TextField ballMass;
    //TextField coefficientOfFriction;
    private TextField tolerance;
    private TextField function;
    private TextField goalX;
    private TextField goalY;
    private TextField startX;
    private TextField startY;
    private TextField mode;

    private Stage stage;

    public GolfGame hold;
    private FileReaders reader;


    public OptionScreen(ScreenSpace game){
        this.game = game;

        hold = new GolfGame();

        reader = new FileReaders();
        reader.read("banana.txt");

        // Give to the variables a value (picture from assets folder).
        playButtonActive = new Texture("play_button_active.png");
        playButtonInactive = new Texture("play_button_inactive.png");
        backButtonActivated = new Texture("back_arrow_activated.png");
        backButtonInactivated = new Texture("back_arrow_inactivated.png");
        stage = new Stage();

        // Create the textFields and add them to the "frame".
        Gdx.input.setInputProcessor(stage);
        Skin customizedMenuSkin = new Skin(Gdx.files.internal("uiskin.json"));
        gravity = new TextField(String.valueOf(Variables.gravity), customizedMenuSkin);
        gravity.setPosition(ScreenSpace.WIDTH - 210,725);
        gravity.setSize(200, 30);
        gravity.setColor(Color.WHITE);
        stage.addActor(gravity);
//
//        ballDiameter = new TextField("2", customizedMenuSkin);
//        ballDiameter.setPosition(ScreenSpace.WIDTH - 210,675);
//        ballDiameter.setSize(200, 30);
//        ballDiameter.setColor(Color.WHITE);
//        stage.addActor(ballDiameter);

        ballMass = new TextField(String.valueOf(Variables.ballMass), customizedMenuSkin);
        ballMass.setPosition(ScreenSpace.WIDTH - 210,675);
        ballMass.setSize(200, 30);
        ballMass.setColor(Color.WHITE);
        stage.addActor(ballMass);

        tolerance = new TextField(String.valueOf(Variables.tolerance), customizedMenuSkin);
        tolerance.setPosition(ScreenSpace.WIDTH - 210,625);
        tolerance.setSize(200, 30);
        tolerance.setColor(Color.WHITE);
        stage.addActor(tolerance);

//        coefficientOfFriction = new TextField("0.131", customizedMenuSkin);
//        coefficientOfFriction.setPosition(ScreenSpace.WIDTH - 210,675);
//        coefficientOfFriction.setSize(200, 30);
//        coefficientOfFriction.setColor(Color.WHITE);
//        stage.addActor(coefficientOfFriction);

        //0.02*x^2 + 0.02*y^2
        function = new TextField(Variables.function, customizedMenuSkin);
        function.setPosition(ScreenSpace.WIDTH - 210,575);
        function.setSize(200, 30);
        function.setColor(Color.WHITE);
        stage.addActor(function);

        goalX = new TextField(String.valueOf(Variables.goalX), customizedMenuSkin);
        goalX.setPosition(25,475);
        goalX.setSize(200, 30);
        goalX.setColor(Color.WHITE);
        stage.addActor(goalX);

        goalY = new TextField(String.valueOf(Variables.goalY), customizedMenuSkin);
        goalY.setPosition(250,475);
        goalY.setSize(200, 30);
        goalY.setColor(Color.WHITE);
        stage.addActor(goalY);

        startX = new TextField(String.valueOf(Variables.startX), customizedMenuSkin);
        startX.setPosition(25,375);
        startX.setSize(200, 30);
        startX.setColor(Color.WHITE);
        stage.addActor(startX);

        startY = new TextField(String.valueOf(Variables.startY), customizedMenuSkin);
        startY.setPosition(250,375);
        startY.setSize(200, 30);
        startY.setColor(Color.WHITE);
        stage.addActor(startY);

        FreeTypeFontGenerator font1 = new FreeTypeFontGenerator(Gdx.files.internal("Courier_New.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter1.size = 40;
        font = font1.generateFont(parameter1);
        font.setColor(Color.WHITE);

        // Create and set the style of the button.
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        // Create the buttons.
        button1 = new TextButton("EulerSolver", textButtonStyle);
        button2 = new TextButton("RungeKutta", textButtonStyle);
        button1.setPosition(40,  250);
        button2.setPosition(ScreenSpace.WIDTH/2-30,  250);
        stage.addActor(button1);
        stage.addActor(button2);

        // Set the default values.
        button1.getLabel().setColor(Color.YELLOW);
        Variables.euler = true;
        Variables.rungeKutta = false;

        // Create the actionListener and add them to the specific button.
        button1.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                // If the button is clicked, mark the physic engine mode as true and the other as false
                // also change the color to see the difference.
                Variables.euler = true;
                Variables.rungeKutta = false;
                button1.getLabel().setColor(Color.YELLOW);
                button2.getLabel().setColor(Color.WHITE);
            }
        });
        button2.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                // If the button is clicked, mark the physic engine mode as true and the other as false
                // also change the color to see the difference.
                Variables.rungeKutta = true;
                Variables.euler = false;
                button2.getLabel().setColor(Color.YELLOW);
                button1.getLabel().setColor(Color.WHITE);
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        // Create all the label and add them to the "frame".
        font.draw(game.batch,"Gravity:", 25,750);
        //font.draw(game.batch,"Ball Diameter:", 25,700);
        font.draw(game.batch,"Ball Mass:", 25,700);
        //font.draw(game.batch,"Coefficient of Friction:", 25,700);
        font.draw(game.batch,"tolerance:", 25,650);
        font.draw(game.batch,"Function:", 25,600);
        font.draw(game.batch,"Goal Coordinates(x,y):", 25,550);
        font.draw(game.batch,"Start Coordinates (x,y):", 25,450);
        font.draw(game.batch,"Physic engine : ",25,350 );

        // Create the play button.
        int x = ScreenSpace.WIDTH / 2 - PLAY_WIDTH / 2;
        if (Gdx.input.getX() < x + PLAY_WIDTH && Gdx.input.getX() > x && ScreenSpace.HEIGHT - Gdx.input.getY() < PLAY_Y + PLAY_HEIGHT && ScreenSpace.HEIGHT - Gdx.input.getY() > PLAY_Y){
            game.batch.draw(playButtonActive, x, PLAY_Y, PLAY_WIDTH, PLAY_HEIGHT);
            if (Gdx.input.isTouched()){
                Variables.gravity = Float.parseFloat(gravity.getText());
                //Variables.ballDiameter = Float.parseFloat(ballDiameter.getText());
                Variables.ballMass = Float.parseFloat(ballMass.getText());
                //Variables.coefficientOfFriction = Float.parseFloat(coefficientOfFriction.getText());
                Variables.function = function.getText();
                Variables.goalX = Float.parseFloat(goalX.getText());
                Variables.goalY = Float.parseFloat(goalY.getText());
                Variables.startX = Float.parseFloat(startX.getText());
                Variables.startY = Float.parseFloat(startY.getText());

                this.dispose();
                hold.create();
                game.setScreen(hold);

            }
        } else {
            game.batch.draw(playButtonInactive, x, PLAY_Y, PLAY_WIDTH, PLAY_HEIGHT);
        }

        // Create the go back button.
        int z = 35;
        if (Gdx.input.getX() < z + BACK_WIDTH && Gdx.input.getX() > z && ScreenSpace.HEIGHT - Gdx.input.getY() < PLAY_Y + BACK_HEIGHT && ScreenSpace.HEIGHT - Gdx.input.getY() > PLAY_Y){
            game.batch.draw(backButtonActivated, z, PLAY_Y, BACK_WIDTH, BACK_WIDTH);
            // If the button id clicked, go back to the main menu.
            if (Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new MainMenu(game));
            }
        } else {
            game.batch.draw(backButtonInactivated, z, PLAY_Y, BACK_WIDTH, BACK_WIDTH);
        }

        game.batch.end();

        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


}
