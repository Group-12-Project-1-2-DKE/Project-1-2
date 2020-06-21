package com.mygdx.game.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.ScreenSpace;
import com.mygdx.game.Variables;

public class GameMode implements Screen {
    final ScreenSpace game;

    // All the buttons
    private TextButton button1;
    private TextButton button2;
    private TextButton button3;
    private TextButton button4;
    private TextButton button5;
    private BitmapFont font;
    private BitmapFont font2;
    private Stage stage;

    private static final int BACK_WIDTH = 125;
    private static final int BACK_HEIGHT = 125;
    private static final int BACK_Y = 15;
    private Texture backButtonActivated;
    private Texture backButtonInactivated;
    private Texture littleMenLogo;
    private static final int LOGO_WIDTH = 350;
    private static final int LOGO_HEIGHT = 350;

    public GameMode(ScreenSpace game){
        this.game = game;

        stage = new Stage();

        // The picture corresponding to the different back arrows.
        backButtonInactivated = new Texture("back_arrow_inactivated.png");
        backButtonActivated = new Texture("back_arrow_activated.png");
        littleMenLogo = new Texture("men_with_gearwheel.png");

        Gdx.input.setInputProcessor(stage);

        // Create the font of the text, it correspond to the police, the style and the color for the buttons.
        FreeTypeFontGenerator font1 = new FreeTypeFontGenerator(Gdx.files.internal("Courier_New.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter1.size = 40;
        font = font1.generateFont(parameter1);
        font.setColor(Color.WHITE);

        // Create the font of the text, it correspond to the police, the style and the color of the text.
        FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 45;
        font2 = font1.generateFont(parameter2);
        font2.setColor(Color.WHITE);

        // Create the style of the button.
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;

        // BUTTON OF THE GAME MODE.
        // Create the buttons.
        button1 = new TextButton("Single player", textButtonStyle);
        button2 = new TextButton("AI", textButtonStyle);
        button1.setPosition(75,  650);
        button2.setPosition(75,  575);
        // Add the buttons to the stage.
        stage.addActor(button1);
        stage.addActor(button2);

        // Set the default values.
        button1.getLabel().setColor(Color.YELLOW);

        // Create the actionListener and add them to the corresponding button.
        button1.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                // If the button is clicked, mark the physic engine mode as true and the other as false
                // also change the color to see the difference.
                Variables.singlePlayer = true;
                Variables.ai = false;
                button1.getLabel().setColor(Color.YELLOW);
                button2.getLabel().setColor(Color.WHITE);
            }
        });
        button2.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                // If the button is clicked, mark the physic engine mode as true and the other as false
                // also change the color to see the difference.
                Variables.ai = true;
                Variables.singlePlayer = false;
                button2.getLabel().setColor(Color.YELLOW);
                button1.getLabel().setColor(Color.WHITE);
            }
        });

        // BUTTONS OF THE PHYSIC ENGINE.
        // Create the buttons.
        button3 = new TextButton("EulerSolver", textButtonStyle);
        button4 = new TextButton("RungeKutta", textButtonStyle);
        button5 = new TextButton("VerletSolver", textButtonStyle);
        button3.setPosition(75,ScreenSpace.HEIGHT/2);
        button4.setPosition(75,  (ScreenSpace.HEIGHT/2)-75);
        button5.setPosition(75,  (ScreenSpace.HEIGHT/2)-150);
        stage.addActor(button3);
        stage.addActor(button4);
        stage.addActor(button5);

        // Set the default values.
        button3.getLabel().setColor(Color.YELLOW);

        // Create the actionListener and add them to the corresponding button.
        button3.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                // If the button is clicked, mark the physic engine mode as true and the other as false
                // also change the color to see the difference.
                Variables.euler = true;
                Variables.rungeKutta = false;
                Variables.verlet = false;
                button3.getLabel().setColor(Color.YELLOW);
                button4.getLabel().setColor(Color.WHITE);
                button5.getLabel().setColor(Color.WHITE);
            }
        });
        button4.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                // If the button is clicked, mark the physic engine mode as true and the other as false
                // also change the color to see the difference.
                Variables.euler = false;
                Variables.rungeKutta = true;
                Variables.verlet = false;
                button3.getLabel().setColor(Color.WHITE);
                button4.getLabel().setColor(Color.YELLOW);
                button5.getLabel().setColor(Color.WHITE);
            }
        });
        button5.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                // If the button is clicked, mark the physic engine mode as true and the other as false
                // also change the color to see the difference.
                Variables.euler = false;
                Variables.rungeKutta = false;
                Variables.verlet = true;
                button3.getLabel().setColor(Color.WHITE);
                button4.getLabel().setColor(Color.WHITE);
                button5.getLabel().setColor(Color.YELLOW);
            }
        });
}
    @Override
    public void show() {}

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        font2.draw(game.batch,"Game Mode:", 25,750);
        font2.draw(game.batch,"Physic Engine:", 25,(ScreenSpace.HEIGHT/2)+100);
        game.batch.draw(littleMenLogo, 400, 100, LOGO_WIDTH, LOGO_HEIGHT);

        int z = 35;
        if (Gdx.input.getX() < z + BACK_WIDTH && Gdx.input.getX() > z &&
                ScreenSpace.HEIGHT - Gdx.input.getY() < BACK_Y + BACK_HEIGHT &&
                ScreenSpace.HEIGHT - Gdx.input.getY() > BACK_Y){
            game.batch.draw(backButtonActivated, z, BACK_Y, BACK_WIDTH, BACK_WIDTH);
            // If the button id clicked, go back to the main menu.
            if (Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new MainMenu(game));
            }
        } else {
            game.batch.draw(backButtonInactivated, z, BACK_Y, BACK_WIDTH, BACK_WIDTH);
        }

        game.batch.end();

        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {}

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