package com.mygdx.game;

import AI.StraighGreedy;
import Course.PuttingCourse;
import Objects.Ball;
import Physics.*;
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

/**
 * class that contains the main game graphics
 */
public class GolfGame extends Game implements ApplicationListener, Screen {

	private PuttingCourse course;
	private PuttingSimulator simulator;
	private Ball gameBall;
	private Boolean ballReachedFlag = false;
	private Vector2D ballPos;
	private EulerSolver eulerSolver;
	private RungeKuttaSolver rungeKuttaSolver;
	private PhysicsEngine engine;
	private StraighGreedy ai;

	private Environment environment;

	private PerspectiveCamera camera;
	private CameraInputController cameraInputController;

	private Model model;
	public static ModelInstance ball;
	private ModelInstance ground;
	private ModelInstance flag;
	private ModelInstance groundPieces;
	private ModelInstance fl;
	private ModelBatch modelBatch;
	private ArrayList<ModelInstance> instances;


	private Stage stage;
	private Label label;
	BitmapFont font;
	private StringBuilder stringBuilder;

	private ScreenSpace game;

	private TextButton shoot;
	private TextField dirX;
	private TextField dirY;
	private TextField speed;

	private Vector3 position = new Vector3();
	private boolean gameOver = false;
	public int attempt = 0;


	public GolfGame() {
	}

	@Override
	public void create() {
		game = new ScreenSpace();
		game.setScreen(new MainMenu(game));
		gameBall = new Ball(new Vector2D(Variables.startX, Variables.startY), 10, 5); //i entered some random values
		gameBall = new Ball(new Vector2D(4, 0), 0.9, 1);

		course = new PuttingCourse(Variables.function, new Vector2D(Variables.startX, Variables.startY), new Vector2D(Variables.goalX, Variables.goalY), gameBall, Variables.coefficientOfFriction, 7, Variables.tolerance);//again some  random values


		if (Variables.euler == true) {
			eulerSolver = new EulerSolver();
			eulerSolver.set_step_size(0.01);
			eulerSolver.set_fric_coefficient(course.getFrictionCoefficient());
			eulerSolver.set_grav_constant(9.81);
			engine = eulerSolver;
			System.out.println("euler solver is selected");
		} else if (Variables.rungeKutta == true) {
			rungeKuttaSolver = new RungeKuttaSolver();
			rungeKuttaSolver.set_step_size(0.01);
			rungeKuttaSolver.set_fric_coefficient(course.getFrictionCoefficient());
			rungeKuttaSolver.set_grav_constant(9.81);
			engine = rungeKuttaSolver;
			System.out.println("runge kutta solver is selected");
		}

		simulator = new PuttingSimulator(course, engine);

		ballPos = course.getStart();
		position = new Vector3((float) course.getStart().getX(), (float) course.evaluate(new Vector2D(course.getStart().getX(), course.getStart().getY())), (float) course.getStart().getY());

		stage = new Stage();
		font = new BitmapFont();
		label = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
		stage.addActor(label);
		stringBuilder = new StringBuilder();


		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set((float) course.getStart().getX() - 0.7f + 1f, 6.97f, 6f);
		camera.lookAt(0, 0, 0);
		camera.near = 1f;
		camera.far = 300f;
		camera.update();
		cameraInputController = new CameraInputController(camera);

		modelBatch = new ModelBatch();
		ModelBuilder modelBuilder = new ModelBuilder();
		modelBuilder.begin();

		modelBuilder.node().id = "ball";
		modelBuilder.part("sphere", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal,
				new Material(ColorAttribute.createDiffuse(Color.WHITE))).sphere(0.5f, 0.5f, 0.5f, 10, 10);

		modelBuilder.node().id = "flagPole";
		modelBuilder.part("cylinder", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal,
				new Material(ColorAttribute.createDiffuse(Color.PINK))).cylinder(0.3f, 2f, 0.5f, 10);

		modelBuilder.node().id = "groundPieces";
		modelBuilder.part("sphere", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal,
				new Material(ColorAttribute.createDiffuse(Color.FOREST))).sphere(0.5f, 0.5f, 0.5f, 5, 5);

		modelBuilder.node().id = "flag";
		modelBuilder.part("box", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.YELLOW)))
				.box(0.25f, 1f, 1f);


		model = modelBuilder.end();
		ball = new ModelInstance(model, "ball");
		ground = new ModelInstance(model, "ground");
		flag = new ModelInstance(model, "flagPole");


		ball.transform.setTranslation((float) Variables.startX, (float) course.evaluate(new Vector2D(course.getStart().getX(), course.getStart().getY())), (float) Variables.startY);
		flag.transform.setTranslation((float) course.getFlag().getX(), (float) course.evaluate(new Vector2D(course.getFlag().getX(), course.getFlag().getY())) + 1.5f, (float) course.getFlag().getY());
		instances = new ArrayList<>();
		instances.add(ball);
		instances.add(flag);


		for (float j = -50f; j <= 99; j = j + 0.3f) {
			for (float i = -50f; i <= 99; i = i + 0.3f) {
				Vector3 pos = new Vector3(i, (float) course.evaluate(new Vector2D(i, j)), j);
				groundPieces = new ModelInstance(model, "groundPieces");
				if (pos.equals(new Vector3((float) course.getFlag().getX(), (float) course.evaluate(new Vector2D(course.getFlag().getX(), course.getFlag().getY())) + 4f, (float) course.getFlag().getY()))) {
					groundPieces.materials.get(0).set(ColorAttribute.createDiffuse(Color.BLACK));
				}
				groundPieces.transform.setTranslation(i, (float) course.evaluate(new Vector2D(i, j)) - 0.25f, j);
				instances.add(groundPieces);
			}
		}

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.5f, 0.2f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		Skin skin1 = new Skin(Gdx.files.internal("uiskin.json"));


		TextArea dirxText = new TextArea("Direction x:", skin1);
		dirxText.setDisabled(true);
		dirxText.setPosition(0, ScreenSpace.HEIGHT - 30);
		dirxText.setSize(100, 30);
		stage.addActor(dirxText);
		TextArea diryText = new TextArea("Direction y:", skin1);
		diryText.setDisabled(true);
		diryText.setPosition(0, ScreenSpace.HEIGHT - 60);
		diryText.setSize(100, 30);
		stage.addActor(diryText);
//		TextArea speedText = new TextArea("speed:", skin1);
//		speedText.setDisabled(true);
//		speedText.setPosition(0, ScreenSpace.HEIGHT - 90);
//		speedText.setSize(100, 30);
//		stage.addActor(speedText);


		shoot = new TextButton("Shoot", skin1);
		shoot.setDisabled(true);
		shoot.setPosition(0, ScreenSpace.HEIGHT - 90);
		shoot.setSize(150, 30);

		shoot.addListener(new ClickListener() {
			@Override
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				ballReachedFlag = true;
				shoot.setVisible(false);
				course.getBall().hit();

			}
		});
		stage.addActor(shoot);

		dirX = new TextField(String.valueOf(Variables.shootX), skin1);
		dirX.setPosition(100, ScreenSpace.HEIGHT - 30);
		dirX.setSize(50, 30);
		stage.addActor(dirX);
		dirY = new TextField(String.valueOf(Variables.shootY), skin1);
		dirY.setPosition(100, ScreenSpace.HEIGHT - 60);
		dirY.setSize(50, 30);
		stage.addActor(dirY);
//		speed = new TextField("12", skin1);
//		speed.setPosition(100, ScreenSpace.HEIGHT - 90);
//		speed.setSize(50, 30);
//		stage.addActor(speed);
	}

	public float myDelta = 0;
	public Vector2D myVector;

	@Override
	public void render(float delta) {
		super.render();
		myDelta += delta;
		if (Gdx.input.getX() < 150 && Gdx.input.getX() > 0 && ScreenSpace.HEIGHT - Gdx.input.getY() < ScreenSpace.HEIGHT && ScreenSpace.HEIGHT - Gdx.input.getY() > (ScreenSpace.HEIGHT) - 150) {
			Gdx.input.setInputProcessor(stage);
		} else {
			Gdx.input.setInputProcessor(cameraInputController);
		}

		if (((((course.getFlag().getX() - course.getTolerance() <= course.getBall().getLocation().getX()) &&
				(course.getBall().getLocation().getX() <= course.getFlag().getX() + course.getTolerance())))
				&& (course.getFlag().getY() - course.getTolerance() <= course.getBall().getLocation().getY())
				&& (course.getBall().getLocation().getY() <= course.getFlag().getY() + course.getTolerance()))) {

			this.dispose();
			game.setScreen(new Congrat(game, attempt));
			gameOver = true;
			System.out.println("Ball reached to the flag");
		} else {
			ball.transform.setTranslation((float) course.getBall().getLocation().getX(), (float) course.evaluate(new Vector2D(course.getBall().getLocation().getX(), course.getBall().getLocation().getY())) + 1f,
					(float) course.getBall().getLocation().getY() + 2);

		}

		if (true && ballReachedFlag) {
			/*if(Variables.ai == true){
				try{
					long start = System.currentTimeMillis();
					if (myVector==null) {
						myVector=simulator.take_shotSlowly(ai.calculate_turn(course,,)50);
					}else {
						myVector=simulator.take_shotSlowly(myVector, 50);
					}
				} catch (StackOverflowError s){

				}
			}else*/
			try {
				long start = System.currentTimeMillis();
				if (myVector == null) {
					myVector = simulator.take_shotSlowly(new Vector2D(Float.parseFloat(dirX.getText()), Float.parseFloat(dirY.getText())), 50);
				} else {
					myVector = simulator.take_shotSlowly(myVector, 50);
				}
			} catch (StackOverflowError s) {

			}

			myDelta = 0;
			if (myVector == null) {
				ballReachedFlag = false;
				shoot.setVisible(true);
			}
		}

		if (myVector != null) {
			camera.translate((float) myVector.getX() / 100, 0, 0);
		}

		camera.lookAt((float) course.getBall().getLocation().getX(), -(float) course.getBall().getLocation().getY(), 0.0f);
		camera.update();
		cameraInputController.update();
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0f, 0.4f, 0.6f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(camera);
		for (ModelInstance instance : instances) {
			if (isVisible(camera, instance)) {
				modelBatch.render(instance, environment);
			}
		}
		modelBatch.end();

		if (gameOver) {
			this.dispose();

			game.setScreen(new Congrat(game, attempt));
		}

		stringBuilder.setLength(0);
		stringBuilder.append("FPS : ").append(Gdx.graphics.getFramesPerSecond());
		stringBuilder.append("Equation : ").append(course.getEquation());
		label.setText(stringBuilder);
		stage.draw();

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
	}

	@Override
	public void show() {

	}

	/**
	 * this method determines if the given instance is visible on the screen
	 *
	 * @param camera   the game camera that makes the instances visible
	 * @param instance an object that is to be rendered on the screen
	 * @return true if the instance is visible
	 */
	private boolean isVisible(final Camera camera, final ModelInstance instance) {
		ball.transform.getTranslation(position);
		return camera.frustum.pointInFrustum(position);
	}

	/**
	 * this method determines if all of the given instances RE visible on the screen
	 *
	 * @param camera   the game camera that makes the instances visible
	 * @param instance an object that is to be rendered on the screen
	 * @return true if all instances are visible
	 */
	private boolean isVisibleAll(final Camera camera, final ModelInstance instance) {
		instance.transform.getTranslation(position);
		return camera.frustum.pointInFrustum(position);
	}

	/**
	 * This method will be called when the user will click on the shoot button
	 * It will recalculate the position of the ball and display it.
	 */
	private void takeShot() {
		try {
			long start = System.currentTimeMillis();
			simulator.take_shot(new Vector2D(Float.parseFloat(dirX.getText()), Float.parseFloat(dirY.getText())));
			System.out.println(System.currentTimeMillis() - start);
		} catch (StackOverflowError s) {
			System.out.println(s);
		}
	}
}
