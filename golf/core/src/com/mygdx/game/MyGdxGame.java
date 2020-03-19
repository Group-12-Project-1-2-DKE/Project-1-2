
package com.mygdx.game;

import Course.PuttingCourse;
import Objects.Ball;
import Objects.Obstacle;
import Physics.Vector2D;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
//import com.sun.tools.corba.se.idl.constExpr.Or;
import states.PlayState;
import states.State;
import states.StateManager;



public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	
	private StateManager manager;
	private OrthographicCamera camera;
	private Animation ball;
	private Vector2D ballPos;
	private Vector2 ballPosition = new Vector2(40,50);
	private float time = 0;
	private Array<Obstacle> obstacles = new Array<>();

	private double tempVectorX;
	private double tempVectorY;

	private Texture ballFrame1, ballFrame2, ballFrame3;
	private static final float startX = 400;
	private  static final float startY = 250;

	private static final int xMax = 890;
	private static final int yMax = 700;
	private Ball myBall;

	private Texture hole;
	private Vector2 holePosition; //flag position

	private TextureRegion start;

	private TextureRegion lightGreen;
	private TextureRegion water; //for minus height

   private TextureRegion ballreg ;
	private static final float fluctuation = -30;


	private Texture flag,flag2;
	private Vector2 flagPos, flag2Pos;

	private  boolean collision = false;
	private PuttingCourse course;
	private double holeTolerance;
	private double height;
	private double friction;
	private double maxVelocity;
	private  double velocity = 0;

	//private static final float velocity = output of the velocity vector maybe?
	//privater static final accelaration = a(x)
	//private static height = h(x)


	private PlayState playstate;

	private Rectangle ballRect = new Rectangle();
	private Rectangle holeRect = new Rectangle();
	private OrthographicCamera intCam;

	private ShapeRenderer shapeRenderer;
	private BitmapFont font;



	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false,800,800);
		batch = new SpriteBatch();
		img = new Texture("green.jpeg");
		water  = new TextureRegion(new Texture("water.jpeg"));


		myBall = new Ball(new Vector2D(50,50),15,70);

		ballFrame1 = new Texture("golfBall.png");
		ballFrame2 = new Texture("golfBall2.png");
		ballFrame3 = new Texture("golfBall3.png");

		start = new TextureRegion(new Texture("start.png"));

		hole = new Texture("hole.png");
		holePosition = new Vector2(875, 600);

		intCam = new OrthographicCamera();
		intCam.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		intCam.update();



		ball = new Animation(0.05f, new TextureRegion(ballFrame1),new TextureRegion(ballFrame2), new TextureRegion(ballFrame3) );
		ball.setPlayMode(Animation.PlayMode.LOOP);
		ballreg = new TextureRegion(ballFrame1,50,400,150,60 );

		ballPosition = new Vector2();
		flag = new Texture("flag.png");
		flag2= new Texture("flag.png");
		flagPos = new Vector2(ballPosition.x + 40,ballPosition.y + 40);
		flag2Pos = new Vector2(holePosition.x + 40, holePosition.y + 40);
		TextureRegion ballR = new TextureRegion(new Texture("golfBall.png"));

		shapeRenderer = new ShapeRenderer();

		resetWorld();
	}

	private void getVector(){

		getVector.createAndShowGUI();

	}

	public void setTempVectorX(double tempVectorX){
        this.tempVectorX = tempVectorX;
    }

    public void setTempVectorY(double tempVectorY){
        this.tempVectorY = tempVectorY;
    }

	private void setBallPosition(Vector2D newVector){

		ballPosition.x = (float)newVector.getX();
		ballPosition.y = (float)newVector.getY();

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

		ballRect.set(ballPosition.x,ballPosition.y,ballFrame1.getWidth(),ballFrame1.getHeight());
		holeRect.set(holePosition.x,holePosition.y,hole.getWidth(),hole.getHeight());

		while(ballPosition.x != xMax && ballPosition.y != yMax){
			ballPosition
		}

		if(collisionCheck()){
			System.out.println("ball reached to the flag position");
		}


	}

	private void drawWorld() {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(img,0,0);
		if(height < 0){
			batch.draw(water,startX,startY,ballFrame1.getWidth()+30,ballFrame1.getHeight()-20);
		}
		if(height > 0 && height <= 30){
			//batch.
		}
		//batch.draw((TextureRegion) ball.getKeyFrame(time),ballPosition.x,ballPosition.y,35,50);
		//batch.draw(ballreg, ballPosition.x,ballPosition.y);
		batch.draw(ballFrame1,ballPosition.x,ballPosition.y,35,50); ///*myBall.getDiameter()*/,/*myBall.getDiameter()*/
		batch.draw(hole,holePosition.x,holePosition.y,95,75);
		batch.draw(flag,flagPos.x,flagPos.y,100,100);
		batch.draw(flag2,flag2Pos.x,flag2Pos.y,150,100);
		batch.end();

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(1,0,0,1);
		shapeRenderer.rect(ballRect.x,ballRect.y,ballRect.width - 175,ballRect.height-150);
		shapeRenderer.rect(holeRect.x + 10 ,holeRect.y,65,85);
		shapeRenderer.end();
	}

	public boolean collisionCheck(){
		if(ballRect.overlaps(holeRect)) {
			collision = true;
			return collision;
		}
		return collision;
	}



	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
