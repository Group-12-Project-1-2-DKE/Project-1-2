package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import states.PlayState;
import states.State;
import states.StateManager;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private StateManager manager;
	private OrthographicCamera camera;
	private Animation ball;
	private Vector2 ballPosition;
	private float time = 0;

	private Texture ballFrame1, ballFrame2, ballFrame3;
	private static final float startX = 400;
	private  static final float startY = 250;


	private static final float fluctuation = -30;
	//private static final float velocity = output of the velocity vector maybe?
	//privater static final accelaration = a(x)
	//private static height = h(x)

	private PlayState playstate;


	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false,800,800);
		batch = new SpriteBatch();
		img = new Texture("green.jpeg");

		ballFrame1 = new Texture("golfBall.png");
		ballFrame2 = new Texture("golfBall2.png");
		ballFrame3 = new Texture("golfBall3.png");

		ball = new Animation(0.05f, new TextureRegion(ballFrame1),new TextureRegion(ballFrame2), new TextureRegion(ballFrame3) );
		ball.setPlayMode(Animation.PlayMode.LOOP);

		ballPosition = new Vector2();

		resetWorld();
	}

	private void resetWorld() {
		ballPosition.set(startX,startY);
		camera.position.x = 750;
	}

	@Override
	public void render () {
		/*Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();*/
		Gdx.gl.glClearColor(1,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		updateWorld();
		drawWorld();

	}

	private void updateWorld() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		time += deltaTime;

		if(Gdx.input.justTouched()){
			System.out.println("a new play is created");
			manager.pushState(new PlayState(manager));

		}
	}

	private void drawWorld() {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(img,0,0);
		batch.draw((TextureRegion) ball.getKeyFrame(time),ballPosition.x,ballPosition.y);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
