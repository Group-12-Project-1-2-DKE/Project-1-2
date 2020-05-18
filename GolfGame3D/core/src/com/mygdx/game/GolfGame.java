package com.mygdx.game;

import AI.StraighGreedy;
import Course.PuttingCourse;
import Objects.Ball;
import Physics.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import java.util.ArrayList;

/**
 * class that contains the main game graphics
 */
public class GolfGame extends Game implements ApplicationListener, Screen {

	private PuttingCourse course;
	private PuttingSimulator simulator;
	private Ball gameBall;
	private Boolean ballReachedFlag = false;
	private EulerSolver eulerSolver;
	private RungeKuttaSolver rungeKuttaSolver;
	private PhysicsEngine engine;
	private VerletSolver verletSolver;
	private StraighGreedy ai;

	private Environment environment;

	private PerspectiveCamera camera;
	private CameraInputController cameraInputController;

	private Model model;
	public static ModelInstance ball;
	private ModelInstance flag;
	private ModelInstance groundPieces;
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

	private Vector3 position = new Vector3();
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
		ai = new StraighGreedy();

		if (Variables.euler == true) {
			eulerSolver = new EulerSolver();
			eulerSolver.set_step_size(0.01);
			eulerSolver.set_fric_coefficient(course.getFrictionCoefficient());
			eulerSolver.set_grav_constant(9.81);
			engine = eulerSolver;
		} else if (Variables.rungeKutta == true) {
			rungeKuttaSolver = new RungeKuttaSolver();
			rungeKuttaSolver.set_step_size(0.01);
			rungeKuttaSolver.set_fric_coefficient(course.getFrictionCoefficient());
			rungeKuttaSolver.set_grav_constant(9.81);
			engine = rungeKuttaSolver;
		} else if (Variables.verlet == true) {
			verletSolver = new VerletSolver();
			verletSolver.set_step_size(0.01);
			verletSolver.set_fric_coefficient(course.getFrictionCoefficient());
			verletSolver.set_grav_constant(9.81);
			engine = verletSolver;
		}

		simulator = new PuttingSimulator(course, engine);

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

		Pixmap pixmap200 = new Pixmap(Gdx.files.internal("groundTexture.jpg"));
		Pixmap pixmap100 = new Pixmap(5000, 5000, pixmap200.getFormat());
		pixmap100.drawPixmap(pixmap200,
				12, 12, pixmap200.getWidth(), pixmap200.getHeight(),
				0, 0, pixmap100.getWidth(), pixmap100.getHeight()
		);
		Texture fieldTex = new Texture(pixmap100);
		pixmap200.dispose();
		pixmap100.dispose();

		Pixmap pixmap2001 = new Pixmap(Gdx.files.internal("ball.jpg"));
		Pixmap pixmap1001 = new Pixmap(7000, 7000, pixmap2001.getFormat());
		pixmap1001.drawPixmap(pixmap2001,
				0, 0, pixmap2001.getWidth(), pixmap2001.getHeight(),
				0, 0, pixmap1001.getWidth(), pixmap1001.getHeight()
		);
		Texture ballTex = new Texture(pixmap1001);
		pixmap2001.dispose();
		pixmap1001.dispose();

		modelBatch = new ModelBatch();
		ModelBuilder modelBuilder = new ModelBuilder();
		modelBuilder.begin();

		modelBuilder.node().id = "ball";
		modelBuilder.part("sphere", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal,
				new Material(TextureAttribute.createDiffuse(ballTex))).sphere(0.5f, 0.5f, 0.5f, 10, 10);

		modelBuilder.node().id = "flagPole";
		modelBuilder.part("cylinder", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal,
				new Material(ColorAttribute.createDiffuse(Color.PINK))).cylinder(Variables.tolerance, 0.1f, Variables.tolerance, 10);

		modelBuilder.node().id = "groundPieces";
		modelBuilder.part("sphere", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal,
				new Material(TextureAttribute.createDiffuse(fieldTex))).sphere(0.5f, 0.5f, 0.5f, 5, 5);

		modelBuilder.node().id = "flag";
		modelBuilder.part("box", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.YELLOW)))
				.box(0.5f, 0.4f, 0.1f);


		model = modelBuilder.end();
		ball = new ModelInstance(model, "ball");
		flag = new ModelInstance(model, "flagPole");
		ModelInstance flagg = new ModelInstance(model,"flag");

        flagg.transform.setTranslation((float) Variables.goalX + 0.15f, (float) course.evaluate(new Vector2D((float)Variables.goalX * 0.1f, (float)Variables.goalY))  + 2.62f, (float)Variables.goalY);
		ball.transform.setTranslation((float) Variables.startX, (float) course.evaluate(new Vector2D(course.getStart().getX(), course.getStart().getY())), (float) Variables.startY);
		flag.transform.setTranslation((float) course.getFlag().getX(), (float) course.evaluate(new Vector2D(course.getFlag().getX(), course.getFlag().getY())) + 1f, (float) course.getFlag().getY());
		instances = new ArrayList<>();
		instances.add(ball);
		instances.add(flag);


		Vector2D[] coverVectors = getBase(new Vector2D(Variables.startX,Variables.startY), new Vector2D(Variables.goalX,Variables.goalY),25);
		int chunkSize = 5;
		int numberX =(int)(coverVectors[1].getX()-coverVectors[0].getX())/chunkSize;
		int numberY=(int)(coverVectors[1].getY()-coverVectors[0].getY())/chunkSize;
		TerrainChunk chunk;
		Vector2D currentPos;
		TerrainChunk.setFunction(Variables.function);
		TerrainChunk[][] terrainChunks = new TerrainChunk[numberX][numberY];
		Material material = new Material(TextureAttribute.createDiffuse(fieldTex));


        int count = 0;
		for(int x = 0; x < numberX; x++){
			for(int y = 0; y < numberY; y++){
				currentPos = new Vector2D(coverVectors[0].getX() + chunkSize * x , coverVectors[0].getY() + chunkSize * y);
				chunk = new TerrainChunk(currentPos, chunkSize);
				chunk.setLocation((float)course.evaluate(new Vector2D(x * chunkSize, y * chunkSize)));
				terrainChunks[x][y] = chunk;

				Mesh mesh = new Mesh(true, chunk.vertices.length/9 , chunk.indices.length,
						new VertexAttribute(VertexAttributes.Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE),
						new VertexAttribute(VertexAttributes.Usage.Normal, 3, ShaderProgram.NORMAL_ATTRIBUTE),
						new VertexAttribute(VertexAttributes.Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE),
						new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2,  ShaderProgram.TEXCOORD_ATTRIBUTE));
				mesh.setVertices(chunk.vertices);
				mesh.setIndices(chunk.indices);

				Model terrain = getModel(mesh,GL20.GL_TRIANGLES,material);
				ModelInstance terrainInstance = new ModelInstance(terrain, 0,0,0);

				chunk.setModelInstance(terrainInstance);
				instances.add(terrainInstance);

				terrainInstance.transform.setTranslation((float)currentPos.getX(),0,(float)currentPos.getY());
				count++;
			}
		}
		
		Variables.lowerBound = new Vector2D(-100,-100);
		Variables.upperBound = new Vector2D(100,100);

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

		shoot = new TextButton("Shoot", skin1);
		shoot.setDisabled(true);
		shoot.setPosition(0, ScreenSpace.HEIGHT - 90);
		shoot.setSize(150, 30);

		shoot.addListener(new ClickListener() {
			@Override
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				if(Variables.ai== true){
					shoot.setDisabled(true);
					shoot.setVisible(false);
				}
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

			System.out.println("Ball reached to the flag");
		} else {
			ball.transform.setTranslation((float) course.getBall().getLocation().getX(), (float) course.evaluate(new Vector2D(course.getBall().getLocation().getX(), course.getBall().getLocation().getY())) + 1f,
					(float) course.getBall().getLocation().getY() + 2);

		}

		if (true && ballReachedFlag) {
			try {
				long start = System.currentTimeMillis();
				if (myVector == null) {
					if(Variables.ai == true){
						Vector2D aiVec = ai.calculate_turn(course,500);
						dirX.setText("" + aiVec.getX());
						dirY.setText("" + aiVec.getY());
						myVector = simulator.take_shotSlowly(new Vector2D(Float.parseFloat(dirX.getText()), Float.parseFloat(dirY.getText())), 50);
						System.out.println("ai worksss");
					}
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
			camera.translate((float) myVector.getX() / 100,0, (float) myVector.getY()/100);
		}

		camera.lookAt((float) course.getBall().getLocation().getX(),-(float) course.evaluate(new Vector2D(course.getBall().getLocation().getX(),course.getBall().getLocation().getY())), (float)course.getBall().getLocation().getY());
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

		stringBuilder.setLength(0);
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
	/**
	 * method that builds a surface from Vector2Ds
	 * @param start is the start coordinate
	 * @param goal is the goal coordinate
	 * @param margin is the margin between each surface vector
	 * @return an array of Vector2D's that represents the surface
	 */
	private Vector2D[] getBase(Vector2D start, Vector2D goal, double margin){
		double xMin,xMax, yMin, yMax;
		if(start.getX()>goal.getX()){
			xMax = start.getX()+margin;
			xMin=goal.getX()-margin;
		}else{
			xMin = start.getX()-margin;
			xMax=goal.getX()+margin;
		}
		if(start.getY()>goal.getY()){
			yMax = start.getY()+margin;
			yMin=goal.getY()-margin;
		}else{
			yMin = start.getY()-margin;
			yMax=goal.getY()+margin;
		}
		return new Vector2D[]{new Vector2D(xMin,yMin), new Vector2D(xMax,yMax)};
	}

	/**
	 * this model calls the overloaded getModel to construct the field
	 * @param mesh the parts of the field
	 * @param gl the primitive GL type
	 * @param material is the texture of the field
	 * @return a model that overloaded getmodel() returns
	 */
	public static Model getModel (final Mesh mesh, int gl , final Material material) {
		return getModel(mesh, 0, mesh.getNumIndices(), gl, material);
	}


	/**
	 * this method builds a Model instance from mesh parts and returns a field Model
	 * @param mesh the parts of the field
	 * @param offset an offset value to provide in mesh part
	 * @param vertices vertices of the mesh
	 * @param gl the primitive GL type
	 * @param material is the texture of the field
	 * @return a field model
	 */
	public static Model getModel (final Mesh mesh, int offset, int vertices, int gl,
										final Material material) {
		Model finalModel = new Model();
		MeshPart meshPart = new MeshPart();
		meshPart.set("meshNode",mesh,offset,vertices, gl);

		NodePart partMaterial = new NodePart(meshPart,material);
		partMaterial.material = material;
		partMaterial.meshPart = meshPart;
		Node node = new Node();
		node.id = "meshNode";
		node.parts.add(partMaterial);

		finalModel.meshes.add(mesh);
		finalModel.materials.add(material);
		finalModel.nodes.add(node);
		finalModel.meshParts.add(meshPart);
		finalModel.manageDisposable(mesh);
		return finalModel;
	}

}
