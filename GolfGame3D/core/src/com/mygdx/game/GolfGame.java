package com.mygdx.game;

import Course.PuttingCourse;
import Objects.Ball;
import Physics.EulerSolver;
import Physics.PhysicsEngine;
import Physics.PuttingSimulator;
import Physics.Vector2D;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.sun.org.apache.xpath.internal.operations.Mod;

import java.util.ArrayList;

public class GolfGame extends InputAdapter implements ApplicationListener {

	private PuttingCourse course;
	private PuttingSimulator simulator;
	private Ball gameBall;
	private Vector2D ballPos;
	private EulerSolver eulerSolver;//i just added this in case we need it
	private PhysicsEngine engine;

	private Environment environment;//environment to adjust the lights

	private PerspectiveCamera camera;
	private CameraInputController cameraInputController;// to rotate around the screen

	private Model model; //keeps information about our objects to be rendered
	private ModelInstance ball; //we use this to render our model
	private ModelInstance ground;
	private ModelInstance flag;
	private ModelInstance groundPieces;
	private ModelBatch modelBatch; //this is a kind of brush to draw our object
	private ArrayList<ModelInstance> instances; // arraylist to store the models we're going to render to the screen

	private Stage stage; //stage is for the text fields, etc.
	private Label label;
	private BitmapFont font;
	private StringBuilder stringBuilder;


	private int visibleCount;//keeps track of the number of model instances that are visible
	private Vector3 position = new Vector3(); //this one also will be used to check if an instance is visible

	private final int WIDTH = 800;
	private final int HEIGHT = 800;

	public GolfGame(PuttingCourse course, PuttingSimulator simulator) {
		this.course = course;
		this.simulator = simulator;

	}

	public GolfGame() {
	}

	@Override
	public void create() {
		//we initialize everything we have here

		gameBall = new Ball(new Vector2D(0, 0), 10, 5); //i entered some random values
		course = new PuttingCourse("0.02*x^2 + 0.02*y^2", new Vector2D(3, 5), new Vector2D(8, 9), gameBall, 0, 7, 4);//again some  random values
		simulator = new PuttingSimulator(course,engine);
		eulerSolver = new EulerSolver();
		eulerSolver.set_step_size(0.01);
		eulerSolver.set_fric_coefficient(course.getFrictionCoefficient());
		engine = eulerSolver;

		ballPos = new Vector2D(course.getFlag().getX(),course.getFlag().getY());

		stage = new Stage(); //to show the texts on the screen
		font = new BitmapFont();
		label = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
		stage.addActor(label);
		stringBuilder = new StringBuilder();

		//here we set our camera
		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//67 is just a common value for the angle
		camera.position.set(10f, 10f, 10f);
		camera.lookAt(0, 0, 0);//to start from the origin
		camera.near = 1f;
		camera.far = 300f;
		camera.update();
		//input controller to see the objects from different perspectives
		cameraInputController = new CameraInputController(camera);
		Gdx.input.setInputProcessor(cameraInputController);

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
		modelBuilder.part("parcel" , GL20.GL_TRIANGLES, VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal,
				new Material(ColorAttribute.createDiffuse(Color.GREEN))).box(0.5f,1f,1f);//sphere(0.5f,0.5f,0.5f,5,5);

		model = modelBuilder.end();
		//create instances of  models
		ball = new ModelInstance(model, "ball");
		ground = new ModelInstance(model, "ground");
		flag = new ModelInstance(model, "flag");

		//set ball position according to the height function
		ball.transform.setTranslation((float) course.getStart().getX(), (float) course.evaluate(new Vector2D(course.getStart().getX(), course.getStart().getY())) + 1f, (float) course.getStart().getY());
		flag.transform.setTranslation((float)course.getFlag().getX(), 2.5f + (float)course.evaluate(new Vector2D(course.getFlag().getX(),course.getFlag().getY())),(float)course.getFlag().getY());

		instances = new ArrayList<>();
		instances.add(ball);
		instances.add(flag);
		for(float j = -5f; j <= 5f; j = j+ 0.3f){
			for(float i = 0; i <= 199; i = i+ 0.3f){
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
	}

	@Override
	public void render() {
		//if ball position is at the flag position - tolerance which means that the ball reached its target position
		if (((((course.getFlag().getX() - course.getTolerance() <= this.ballPos.getX()) &&
				(this.ballPos.getX() <= course.getFlag().getX() + course.getTolerance())))
				&& (course.getFlag().getY() - course.getTolerance() <= this.ballPos.getY())
				&& (this.ballPos.getY() <= course.getFlag().getY() + course.getTolerance()))) {
			//maybe also add when the velocity is too small to the if statement
			//TODO : add game over screen
		} else {
			//here we should add the ball position update stuff to move the ball
			System.out.println();
		}


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

		stringBuilder.setLength(0);
		stringBuilder.append("FPS : ").append(Gdx.graphics.getFramesPerSecond());//to see the frame per second
		//stringBuilder.append("Visible : ").append(0);  //to see the number of visible instances
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
	public void dispose() {
		model.dispose();
		modelBatch.dispose();
	}

	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);

	}

	public boolean isVisible(final Camera camera, final ModelInstance instance) {
		ball.transform.getTranslation(position);
		return camera.frustum.pointInFrustum(position); //we should also modify this method
	}

	/*public void setCourse(Menu menu){
		//TODO : add the preferences selected from the menu
}*/


}
