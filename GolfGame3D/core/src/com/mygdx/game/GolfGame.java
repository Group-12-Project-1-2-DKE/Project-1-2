package com.mygdx.game;

import Course.PuttingCourse;
import Objects.Ball;
import Physics.EulerSolver;
import Physics.PhysicsEngine;
import Physics.PuttingSimulator;
import Physics.Vector2D;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ui.FileReaders;


import java.util.ArrayList;

public class GolfGame extends Game implements ApplicationListener, Screen {

	private PuttingCourse course;
	private PuttingSimulator simulator;
	private Ball gameBall;
	private Boolean ballReachedFlag = false;
	private Vector2D ballPos;
	private EulerSolver eulerSolver;//i just added this in case we need it
	private PhysicsEngine engine;

	private Environment environment;//environment to adjust the lights

	private PerspectiveCamera camera;
	private CameraInputController cameraInputController;// to rotate around the screen

	private Model model; //keeps information about our objects to be rendered
	public static ModelInstance ball; //we use this to render our model
	private ModelInstance ground;
	private ModelInstance flag;
	private ModelInstance groundPieces;
	private ModelBatch modelBatch; //this is a kind of brush to draw our object
	public SpriteBatch batch;
	private ArrayList<ModelInstance> instances; // arraylist to store the models we're going to render to the screen

	private Stage stage; //stage is for the text fields, etc.
	private Label label;
	BitmapFont font;
	private StringBuilder stringBuilder;

	private ScreenSpace game;

	private TextButton shoot;
	private TextField dirX;
	private TextField dirY;
	private TextField speed;

	private Texture groundTexture;

	private int visibleCount;//keeps track of the number of model instances that are visible
	private Vector3 position = new Vector3(); //this one also will be used to check if an instance is visible

	private int visCount;//keeps track of the number of model instances that are visible
	private Vector3 pos = new Vector3(); //this one also will be used to check if an instance is visible


	private boolean gameOver = false;

	public int attempt = 0;

	private FileReaders reader;

	public GolfGame(PuttingCourse course, PuttingSimulator simulator) {
		this.course = course;
		this.simulator = simulator;

	}

	public GolfGame() {}

	@Override
	public void create() {
		//we initialize everything we have here
        game = new ScreenSpace();
        game.setScreen(new MainMenu(game));
		gameBall = new Ball(new Vector2D(Variables.startX,Variables.startY), 10, 5); //i entered some random values
		gameBall = new Ball(new Vector2D(4, 0), 0.9, 1);
		reader = new FileReaders();

		if(Variables.mode == 2){
			course = new PuttingCourse(reader.getFunction(),new Vector2D(reader.getXStart(),reader.getYStart()),
					new Vector2D(reader.getXGoal(),reader.getYGoal()),gameBall,reader.getCoefficientOfFriction(),reader.getMaxVelocity(),reader.getTolerance());
		}else {
			course = new PuttingCourse(Variables.function, new Vector2D(Variables.startX, Variables.startY), new Vector2D(Variables.goalX, Variables.goalY), gameBall, Variables.coefficientOfFriction, 7, 4);//again some  random values

		}

		eulerSolver = new EulerSolver();
		eulerSolver.set_step_size(0.01);
		eulerSolver.set_fric_coefficient(course.getFrictionCoefficient());
		eulerSolver.set_grav_constant(9.81);
		engine = eulerSolver;
		simulator = new PuttingSimulator(course,engine);

		ballPos = course.getStart();
		//ballPos = new Vector2D(course.getStart().getX(),course.getStart().getY());
		position = new Vector3((float)course.getStart().getX(),(float)course.evaluate(new Vector2D(course.getStart().getX(),course.getStart().getY())), (float)course.getStart().getY());

		stage = new Stage(); //to show the equation on the screen
		font = new BitmapFont();
		label = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
		stage.addActor(label);
		stringBuilder = new StringBuilder();


		//here we set our camera
		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//67 is just a common value for the angle
		camera.position.set((float)course.getStart().getX() - 0.7f + 1f, 6.97f, 6f);
		camera.lookAt(0, 0, 0);//to start from the origin
		camera.near = 1f;
		camera.far = 300f;
		camera.update();
		//input controller to see the objects from different perspectives
		cameraInputController = new CameraInputController(camera);

		modelBatch = new ModelBatch();
		//here we build our models to be rendered
		ModelBuilder modelBuilder = new ModelBuilder();
		modelBuilder.begin();

		modelBuilder.node().id = "ball";
		modelBuilder.part("sphere", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal,
				new Material(ColorAttribute.createDiffuse(Color.WHITE))).sphere(0.5f, 0.5f, 0.5f, 10, 10);

		modelBuilder.node().id = "flag";
		modelBuilder.part("cylinder", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal,
				new Material(ColorAttribute.createDiffuse(Color.PINK))).cylinder(0.5f,2f,0.5f,10);

		modelBuilder.node().id = "groundPieces";
		modelBuilder.part("sphere" , GL20.GL_TRIANGLES, VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal,
				new Material(ColorAttribute.createDiffuse(Color.GREEN))).sphere(0.5f,0.5f,0.5f,5,5);

		model = modelBuilder.end();
		//create instances of  models
		ball = new ModelInstance(model, "ball");
		ground = new ModelInstance(model, "ground");
		flag = new ModelInstance(model, "flag");


		//set ball position according to the height function
		ball.transform.setTranslation((float) Variables.startX, (float) course.evaluate(new Vector2D(course.getStart().getX(), course.getStart().getY())) , (float) Variables.startY);
		flag.transform.setTranslation((float)course.getFlag().getX(),  (float)course.evaluate(new Vector2D(course.getFlag().getX(),course.getFlag().getY())) + 4f,(float)course.getFlag().getY());
		instances = new ArrayList<>();
		instances.add(ball);
		instances.add(flag);
		groundTexture = new Texture("groundTexture.jpg");
		for(float j = -15f; j <= 15f; j = j+ 0.3f){
			for(float i = -50f; i <= 99; i = i+ 0.3f){
				groundPieces = new ModelInstance(model, "groundPieces");
				groundPieces.transform.setTranslation(i  , (float)course.evaluate(new Vector2D(i,j)) - 0.25f, j);
				instances.add(groundPieces);
			}
		}
		//instead of the ground above, i added the ground that is generated according to the equation in puttingCourse class
		//instances.addAll(course.getCourseShape(model));

		environment = new Environment();//some lightning stuff
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.5f, 0.2f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		//simulator.take_shot(new Vector2D(40, 50));//we need to add the initial velocity vector but idk from where);

		Skin skin1 = new Skin(Gdx.files.internal("uiskin.json"));

		// Create the text area.
		TextArea dirxText = new TextArea( "Direction x:",skin1);
		dirxText.setDisabled(true);
		dirxText.setPosition(0,ScreenSpace.HEIGHT-30);
		dirxText.setSize(100, 30);
		stage.addActor(dirxText);
		TextArea diryText = new TextArea( "Direction y:",skin1);
		diryText.setDisabled(true);
		diryText.setPosition(0,ScreenSpace.HEIGHT-60);
		diryText.setSize(100, 30);
		stage.addActor(diryText);
		TextArea speedText = new TextArea( "speed:",skin1);
		speedText.setDisabled(true);
		speedText.setPosition(0,ScreenSpace.HEIGHT-90);
		speedText.setSize(100, 30);
		stage.addActor(speedText);

		// Create the play button.
		shoot = new TextButton("Shoot", skin1);
		shoot.setDisabled(true);
		shoot.setPosition(0,ScreenSpace.HEIGHT-120);
		shoot.setSize(150, 30);

		shoot.addListener(new ClickListener(){
			@Override
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//takeShot();
				ballReachedFlag=true;
				shoot.setVisible(false);
				course.getBall().hit();

			}
		});
		stage.addActor(shoot);

		// Create the text field.
		dirX = new TextField("0",skin1);
		dirX.setPosition(100,ScreenSpace.HEIGHT-30);
		dirX.setSize(50, 30);
		stage.addActor(dirX);
		dirY = new TextField("0",skin1);
		dirY.setPosition(100,ScreenSpace.HEIGHT-60);
		dirY.setSize(50, 30);
		stage.addActor(dirY);
		speed = new TextField("12",skin1);
		speed.setPosition(100,ScreenSpace.HEIGHT-90);
		speed.setSize(50, 30);
		stage.addActor(speed);
	}

	public float myDelta = 0;
	public Vector2D myVector;
	@Override
	public void render(float delta) {
		super.render();
		//Stage stage1 = new Stage();
		myDelta += delta;
		//move to the map, but because the camera is following the ball it is strange
		if(Gdx.input.getX() < 150 && Gdx.input.getX() > 0 && ScreenSpace.HEIGHT - Gdx.input.getY() < ScreenSpace.HEIGHT && ScreenSpace.HEIGHT - Gdx.input.getY() > (ScreenSpace.HEIGHT) -150){
			Gdx.input.setInputProcessor(stage);
		}else{
			Gdx.input.setInputProcessor(cameraInputController);
		}

		//if ball position is at the flag position - tolerance which means that the ball reached its target position
		if (((((course.getFlag().getX() - course.getTolerance() <= course.getBall().getLocation().getX()) &&
				(course.getBall().getLocation().getX() <= course.getFlag().getX() + course.getTolerance())))
				&& (course.getFlag().getY() - course.getTolerance() <= course.getBall().getLocation().getY())
				&& (course.getBall().getLocation().getY() <= course.getFlag().getY() + course.getTolerance()))) {
			//maybe also add when the velocity is too small to the if statement
			//TODO : add game over screen
			this.dispose();
			//game.setScreen(new Congrat(game, attempt));
			gameOver = true;
			System.out.println("Ball reached to the flag");
		} else {
				ball.transform.setTranslation((float) course.getBall().getLocation().getX(), (float) course.evaluate(new Vector2D(course.getBall().getLocation().getX(), course.getBall().getLocation().getY())) + 1f,
						(float) course.getBall().getLocation().getY() + 2);

		}

		if (true&& ballReachedFlag) {
			try{
				long start = System.currentTimeMillis();
				if (myVector==null) {
					myVector=simulator.take_shotSlowly(new Vector2D(Float.parseFloat(dirX.getText()), Float.parseFloat(dirY.getText())),50);
				}else {
					myVector=simulator.take_shotSlowly(myVector, 50);
				}
				//System.out.println(System.currentTimeMillis() - start);
			} catch (StackOverflowError s){
				//System.out.println(s);
			}



			myDelta=0;
			if ( myVector==null) {
				ballReachedFlag=false;
				shoot.setVisible(true);
				//System.out.println("My vec null");
			}
		}

		if ( myVector!=null) {
			camera.translate((float)myVector.getX()/100,0,0);
		}

		camera.lookAt((float)course.getBall().getLocation().getX(),-(float)course.getBall().getLocation().getY(),0.0f);
		camera.update();
		cameraInputController.update();
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//some default stuff
		Gdx.gl.glClearColor(0f,0.4f,0.6f,0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(camera);
		for (ModelInstance instance : instances) {
			if (isVisible(camera, instance)) { //if the instances are visible  we render them
				modelBatch.render(instance, environment);
				//visibleCount++;
			}
		}
		modelBatch.end();

		if (gameOver) {
			this.dispose();

			game.setScreen(new Congrat(game,attempt));
		}

		stringBuilder.setLength(0);
		stringBuilder.append("FPS : ").append(Gdx.graphics.getFramesPerSecond());//to see the frame per second
		//stringBuilder.append("Visible : ").append(0);  //to see the number of visible instances
		stringBuilder.append("Equation : ").append(course.getEquation());
		label.setText(stringBuilder);
		stage.draw();
		//stage1.draw();

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
		model.dispose();
		//modelBatch.dispose();
	}

	@Override
	public void show() {

	}



	/*public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);

	}*/

	public boolean isVisible(final Camera camera, final ModelInstance instance) {
		ball.transform.getTranslation(position);
		return camera.frustum.pointInFrustum(position); //we should also modify this method
	}

	public boolean isVisibleAll(final Camera camera, final ModelInstance instance) {
		instance.transform.getTranslation(position);
		return camera.frustum.pointInFrustum(position); //we should also modify this method
	}

	// This method will be called when the user will click on the shoot button
	// It will recalculate the position of the ball and display it.
	public void takeShot (){
		try{
			long start = System.currentTimeMillis();
			simulator.take_shot(new Vector2D(Float.parseFloat(dirX.getText()), Float.parseFloat(dirY.getText())));
			System.out.println(System.currentTimeMillis() - start);
		} catch (StackOverflowError s){
			System.out.println(s);
		}
	}

}
