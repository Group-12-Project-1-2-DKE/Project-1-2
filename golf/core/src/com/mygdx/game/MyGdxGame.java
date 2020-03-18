package com.mygdx.game;

import Objects.Ball;
import Objects.Obstacle;
import Physics.Vector2D;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import states.PlayState;
import states.State;
import states.StateManager;



public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private StateManager manager;
	private OrthographicCamera camera;
	private Animation ball;
	private Vector2 ballPos;
	private Vector2 ballPosition = new Vector2(40,50);
	private float time = 0;
	private Array<Obstacle> obstacles = new Array<>();

	private Texture ballFrame1, ballFrame2, ballFrame3;
	private static final float startX = 400;
	private  static final float startY = 250;

	private Ball myBall;

	private Texture hole;
	private Vector2 holePosition; //flag position

   private TextureRegion ballreg ;
	private static final float fluctuation = -30;

	private Texture flag,flag2;
	private Vector2 flagPos, flag2Pos;
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

		myBall = new Ball(new Vector2D(50,50),15,70);

		ballFrame1 = new Texture("golfBall.png");
		ballFrame2 = new Texture("golfBall2.png");
		ballFrame3 = new Texture("golfBall3.png");

		hole = new Texture("hole.png");
		holePosition = new Vector2(875, 600);

		ball = new Animation(0.05f, new TextureRegion(ballFrame1),new TextureRegion(ballFrame2), new TextureRegion(ballFrame3) );
		ball.setPlayMode(Animation.PlayMode.LOOP);
		ballreg = new TextureRegion(ballFrame1,50,400,150,60 );

		ballPosition = new Vector2();
		flag = new Texture("flag.png");
		flag2= new Texture("flag.png");
		flagPos = new Vector2(ballPosition.x + 40,ballPosition.y + 40);
		flag2Pos = new Vector2(holePosition.x + 40, holePosition.y + 40);
		TextureRegion ballR = new TextureRegion(new Texture("golfBall.png"));
		resetWorld();
	}

	private void resetWorld() {
		ballPosition.set(startX,startY);
		camera.position.x = 750;

		/*obstacles.clear();
		for(int i = 0; i < obstacles.size(); i++){
			obstacles.add(new Obstacle())*/
		//}
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
         // add ball movement
		if(Gdx.input.justTouched()){
			System.out.println("a new play is created");
			manager.pushState(new PlayState(manager));
		}
		//if statemnt for camera to follow the ball
		if(camera.position.x - ballPosition.x >700 + ballreg.getRegionWidth()){
			camera.position.x = ballPosition.x - 20; //change the numbers if necessary
		}
	}

	private void drawWorld() {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(img,0,0);
		//batch.draw((TextureRegion) ball.getKeyFrame(time),ballPosition.x,ballPosition.y);
		//batch.draw(ballreg, ballPosition.x,ballPosition.y);
		batch.draw(ballFrame1,ballPosition.x,ballPosition.y,35,50); ///*myBall.getDiameter()*/,/*myBall.getDiameter()*/
		batch.draw(hole,holePosition.x,holePosition.y,95,75);
		batch.draw(flag,flagPos.x,flagPos.y,100,100);
		batch.draw(flag2,flag2Pos.x,flag2Pos.y,150,100);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
