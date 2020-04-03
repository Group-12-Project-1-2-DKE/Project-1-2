package com.mygdx.game;

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


public class OptionScreen implements Screen {
    final ScreenSpace game;
    Texture playButtonActive;
    Texture playButtonInactive;
    Texture backButtonActivated;
    Texture backButtonInactivated;

    private static final int PLAY_WIDTH = 300;
    private static final int PLAY_HEIGHT = 120;
    private static final int BACK_WIDTH = 125;
    private static final int BACK_HEIGHT = 125;
    private static final int PLAY_Y = 75;

    BitmapFont font;

    TextField gravity;
    TextField ballDiameter;
    TextField ballMass;
    TextField coefficientOfFriction;
    TextField function;
    TextField goalX;
    TextField goalY;
    TextField startX;
    TextField startY;

    Stage stage;

    public OptionScreen(ScreenSpace game){
        this.game = game;

        // Give to the variables a value (picture from assets folder).
        playButtonActive = new Texture("play_button_active.png");
        playButtonInactive = new Texture("play_button_inactive.png");
        backButtonActivated = new Texture("back_arrow_activated.png");
        backButtonInactivated = new Texture("back_arrow_inactivated.png");
        stage = new Stage();

        // Create the textFields and add them to the "frame".
        Gdx.input.setInputProcessor(stage);
        Skin customizedMenuSkin = new Skin(Gdx.files.internal("uiskin.json"));
        gravity = new TextField("", customizedMenuSkin);
        gravity.setPosition(ScreenSpace.WIDTH - 210,725);
        gravity.setSize(200, 30);
        gravity.setColor(Color.WHITE);
        stage.addActor(gravity);

        ballDiameter = new TextField("", customizedMenuSkin);
        ballDiameter.setPosition(ScreenSpace.WIDTH - 210,675);
        ballDiameter.setSize(200, 30);
        ballDiameter.setColor(Color.WHITE);
        stage.addActor(ballDiameter);

        ballMass = new TextField("", customizedMenuSkin);
        ballMass.setPosition(ScreenSpace.WIDTH - 210,625);
        ballMass.setSize(200, 30);
        ballMass.setColor(Color.WHITE);
        stage.addActor(ballMass);

        coefficientOfFriction = new TextField("", customizedMenuSkin);
        coefficientOfFriction.setPosition(ScreenSpace.WIDTH - 210,575);
        coefficientOfFriction.setSize(200, 30);
        coefficientOfFriction.setColor(Color.WHITE);
        stage.addActor(coefficientOfFriction);

        function = new TextField("", customizedMenuSkin);
        function.setPosition(ScreenSpace.WIDTH - 210,525);
        function.setSize(200, 30);
        function.setColor(Color.WHITE);
        stage.addActor(function);

        goalX = new TextField("", customizedMenuSkin);
        goalX.setPosition(25,425);
        goalX.setSize(200, 30);
        goalX.setColor(Color.WHITE);
        stage.addActor(goalX);

        goalY = new TextField("", customizedMenuSkin);
        goalY.setPosition(250,425);
        goalY.setSize(200, 30);
        goalY.setColor(Color.WHITE);
        stage.addActor(goalY);

        startX = new TextField("", customizedMenuSkin);
        startX.setPosition(25,325);
        startX.setSize(200, 30);
        startX.setColor(Color.WHITE);
        stage.addActor(startX);

        startY = new TextField("", customizedMenuSkin);
        startY.setPosition(250,325);
        startY.setSize(200, 30);
        startY.setColor(Color.WHITE);
        stage.addActor(startY);
    }

    public float getGravity() {
        return Float.parseFloat(gravity.getText());
    }

    public float getBallDiameter() {
        return Float.parseFloat(ballDiameter.getText());
    }

    public float getBallMass() {
        return Float.parseFloat(ballMass.getText());
    }

    public float getCoefficientOfFriction() {
        return Float.parseFloat(coefficientOfFriction.getText());
    }

    public String getFunction() {
        return function.getText();
    }

    public float getGoalX() {
        return Float.parseFloat(goalX.getText());
    }

    public float getGoalY() {
        return Float.parseFloat(goalY.getText());
    }

    public float getStartX() {
        return Float.parseFloat(startX.getText());
    }

    public float getStartY() {
        return Float.parseFloat(startY.getText());
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
        FreeTypeFontGenerator font1 = new FreeTypeFontGenerator(Gdx.files.internal("Courier_New.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter1.size = 40;
        font = font1.generateFont(parameter1);
        font.setColor(Color.WHITE);
        font1.dispose();
        font.draw(game.batch,"Gravity:", 25,750);

        FreeTypeFontGenerator font2 = new FreeTypeFontGenerator(Gdx.files.internal("Courier_New.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 40;
        font = font2.generateFont(parameter2);
        font.setColor(Color.WHITE);
        font2.dispose();
        font.draw(game.batch,"Ball Diameter:", 25,700);

        FreeTypeFontGenerator font3 = new FreeTypeFontGenerator(Gdx.files.internal("Courier_New.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter3.size = 40;
        font = font3.generateFont(parameter3);
        font.setColor(Color.WHITE);
        font3.dispose();
        font.draw(game.batch,"Ball Mass:", 25,650);

        FreeTypeFontGenerator font4 = new FreeTypeFontGenerator(Gdx.files.internal("Courier_New.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter4 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter4.size = 40;
        font = font4.generateFont(parameter4);
        font.setColor(Color.WHITE);
        font4.dispose();
        font.draw(game.batch,"Coefficient of Friction:", 25,600);

        FreeTypeFontGenerator font5 = new FreeTypeFontGenerator(Gdx.files.internal("Courier_New.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter5 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter5.size = 40;
        font = font5.generateFont(parameter5);
        font.setColor(Color.WHITE);
        font5.dispose();
        font.draw(game.batch,"Function:", 25,550);

        FreeTypeFontGenerator font6 = new FreeTypeFontGenerator(Gdx.files.internal("Courier_New.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter6 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter6.size = 40;
        font = font6.generateFont(parameter6);
        font.setColor(Color.WHITE);
        font6.dispose();
        font.draw(game.batch,"Goal Coordinates(x,y):", 25,500);

        FreeTypeFontGenerator font7 = new FreeTypeFontGenerator(Gdx.files.internal("Courier_New.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter7 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter7.size = 40;
        font = font7.generateFont(parameter7);
        font.setColor(Color.WHITE);
        font7.dispose();
        font.draw(game.batch,"Start Coordinates (x,y):", 25,400);

        // Create the play button.
        int x = ScreenSpace.WIDTH / 2 - PLAY_WIDTH / 2;
        if (Gdx.input.getX() < x + PLAY_WIDTH && Gdx.input.getX() > x && ScreenSpace.HEIGHT - Gdx.input.getY() < PLAY_Y + PLAY_HEIGHT && ScreenSpace.HEIGHT - Gdx.input.getY() > PLAY_Y){
            game.batch.draw(playButtonActive, x, PLAY_Y, PLAY_WIDTH, PLAY_HEIGHT);
            if (Gdx.input.isTouched()){
                this.dispose();
                Gdx.app.exit();
                new GolfGame();
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
