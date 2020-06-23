package com.mygdx.game;

import AI.MazeAI;
import Course.PuttingCourse;
import Maze.MazeGenerator;
import Maze.Solver;
import Objects.Wall;
import Objects.Ball;
import Physics.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Menus.Congrat;
import java.util.ArrayList;

/**
 * Object that render the golf game with the maze.
 */
public class GolfGameMaze implements Screen{
    private static PuttingCourse course;    // All the physic of the game.
    private PuttingSimulator simulator;
    private boolean ballReachedFlag = false;// If the ball has reached the flag or not.
    private PhysicsEngine engine;           // Physic engine that act on the world
    private MazeAI ai;                      // AI that calculates the ball to reach
    private ArrayList<Vector2D> locations;  // ArrayList of useful location for the AI.
    private int count = -1;                 // Index of the position in the Location arrayList.

    private Environment environment;

    private PerspectiveCamera camera;
    private CameraInputController cameraInputController;    // Allow the user to move the camera.

    private Model model;
    public ModelInstance ball;
    private ModelBatch modelBatch;
    private static ModelBuilder modelBuilder;
    private static ArrayList<ModelInstance> instances;
    private static ArrayList<ModelInstance> redwallsinstances;

    private Stage stage1;
    private Label label;
    private StringBuilder stringBuilder;

    private ScreenSpace game;

    private TextButton shoot;
    private TextField dirX;
    private TextField dirY;

    private Vector3 position;
    public int attempt = 0;

    public float myDelta = 0;
    public Vector2D myVector;


    private Wall wall;
   public static  boolean isCollision = false;
    /**
     * Contructor method of the GolfGameMaze object.
     * @param game ScreenSpace object on which the object is going to be rendered.
     */
    public GolfGameMaze(ScreenSpace game) {
        this.game = game;

        modelBuilder = new ModelBuilder();
        instances = new ArrayList<>();

        createWalls();

        Ball gameBall = new Ball(new Vector2D(Variables.startX, Variables.startY), Variables.ballMass);

        course = new PuttingCourse(Variables.function, new Vector2D(Variables.startX, Variables.startY),
                new Vector2D(Variables.goalX, Variables.goalY), gameBall, Variables.coefficientOfFriction, 7,
                Variables.tolerance);

        ai = new MazeAI();

        // If the user select euler solver, create eulerSolver object.
        if (Variables.euler) {
            engine = new EulerSolver();
            engine.set_step_size(0.01);
            engine.set_fric_coefficient(course.getFrictionCoefficient());
            engine.set_grav_constant(9.81);

        } else if (Variables.rungeKutta) {  // If the user select rungeKutta solver, create RungeKuttaSolver object.
            engine= new RungeKuttaSolver();
            engine.set_step_size(0.01);
            engine.set_fric_coefficient(course.getFrictionCoefficient());
            engine.set_grav_constant(9.81);

        } else if (Variables.verlet) {  // If the user select verlet solver, create VerletSolver object.
            engine = new VerletSolver();
            engine.set_step_size(0.01);
            engine.set_fric_coefficient(course.getFrictionCoefficient());
            engine.set_grav_constant(9.81);
        }

        simulator = new PuttingSimulator(course, engine);

        position = new Vector3((float) course.getStart().getX(),
                (float) course.evaluate(new Vector2D(course.getStart().getX(), course.getStart().getY())),
                (float) course.getStart().getY());

        stage1 = new Stage();
        BitmapFont font = new BitmapFont();
        label = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
        stage1.addActor(label);
        stringBuilder = new StringBuilder();

        // Instantiate the camera.
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set((float) course.getStart().getX() - 0.7f + 1f, 6.97f, 6f);
        camera.lookAt(0, 0, 0);
        camera.near = 1f;
        camera.far = 300f;
        camera.update();
        cameraInputController = new CameraInputController(camera);

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
        modelBuilder.begin();

        // Instantiate the ball object that is going to be rendered.
        modelBuilder.node().id = "ball";
        modelBuilder.part("sphere", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal,
                new Material(TextureAttribute.createDiffuse(ballTex))).sphere(0.5f, 0.5f, 0.5f, 10, 10);

        // Instantiate the flag object that is going to be rendered.
        modelBuilder.node().id = "flagPole";
        modelBuilder.part("cylinder", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal,
                new Material(ColorAttribute.createDiffuse(Color.PINK))).cylinder(Variables.tolerance, 0.1f, Variables.tolerance, 10);

        model = modelBuilder.end();
        ball = new ModelInstance(model, "ball");
        ModelInstance flag = new ModelInstance(model, "flagPole");

        // Sets the initial position of the ball and the position of the flag.
        ball.transform.setTranslation((float) course.getStart().getX(),
                (float) course.evaluate(new Vector2D(course.getStart().getX(), course.getStart().getY())),
                (float) course.getStart().getY());
        flag.transform.setTranslation((float) course.getFlag().getX(),
                (float) course.evaluate(new Vector2D(course.getFlag().getX(), course.getFlag().getY())),
                (float) course.getFlag().getY());

        instances.add(ball);
        instances.add(flag);

        createMesh();

        Variables.lowerBound = new Vector2D(-100, -100);    // Lower bound of the field
        Variables.upperBound = new Vector2D(100, 100);      // Upper bound of the field.

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.5f, 0.2f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        Skin skin1 = new Skin(Gdx.files.internal("uiskin.json"));

        shootStage(skin1);
        wall = new Wall();
    }

    @Override
    public void render(float delta) {
        myDelta += delta;
        // If the user's cursor is in the area of the stage1, allow the user to interact with it.
        if (Gdx.input.getX() < 150 && Gdx.input.getX() > 0 && ScreenSpace.HEIGHT - Gdx.input.getY() < ScreenSpace.HEIGHT &&
                ScreenSpace.HEIGHT - Gdx.input.getY() > ScreenSpace.HEIGHT - 150) {
            Gdx.input.setInputProcessor(stage1);
        } else {
            // if the user is not in the area of the stage 1, allow the user to interact with the camera.
            Gdx.input.setInputProcessor(cameraInputController);
        }
        // If the ball is in the hole, call the congrat screen.
        if (course.getFlag().add(course.getBall().getLocation().multiply(-1)).length() <= course.getTolerance()){
            game.setScreen(new Congrat(game, attempt));
        } else {
            // If the ball is not int the hole, render the ball at the Ball object's position.
            ball.transform.setTranslation((float) course.getBall().getLocation().getX(),
                    (float) course.evaluate(new Vector2D(course.getBall().getLocation().getX(),
                            course.getBall().getLocation().getY())) + 0.25f,
                    (float) course.getBall().getLocation().getY() + 1f);
        }
        if (ballReachedFlag) {
            try {
                if (myVector == null) {
                    if (Variables.ai) {
                        // If the user choose to use the AI, calcultate the shot with the AI.
                        Vector2D aiVec = ai.calculate_turn(course, 500, locations.get(count));
                        dirX.setText("" + aiVec.getX());
                        dirY.setText("" + aiVec.getY());
                        myVector = simulator.take_shotSlowly(new Vector2D(Float.parseFloat(dirX.getText()),
                                Float.parseFloat(dirY.getText())));

                    }
                    attempt++;
                    myVector = simulator.take_shotSlowly(new Vector2D(Float.parseFloat(dirX.getText()),
                            Float.parseFloat(dirY.getText())));
                }
                else {
                    myVector = simulator.take_shotSlowly(myVector);
                }
                isCollision = collision((float)course.getBall().getLocation().getX(),(float)course.getBall().getLocation().getY());
                       if(isCollision) {
                           engine.wall_collision(course.getBall());
                           System.out.println("collision");
                           }



            } catch (StackOverflowError s) {
                System.out.println("the AI couldn't shoot");
            }

            myDelta = 0;
            if (myVector == null) {
                ballReachedFlag = false;
                shoot.setVisible(true);
            }
        }

        if (myVector != null) {
            camera.translate((float) myVector.getX() / 100, 0, (float) myVector.getY() / 100);
        }

        // The camera looks at the ball.
        camera.lookAt((float) course.getBall().getLocation().getX(),
                -(float) course.evaluate(new Vector2D(course.getBall().getLocation().getX(),
                        course.getBall().getLocation().getY())), (float) course.getBall().getLocation().getY());
        camera.update();
        cameraInputController.update();
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(0f, 0.4f, 0.6f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(camera);
        for (ModelInstance instance : instances) {
            if (isVisible(camera)) {
                modelBatch.render(instance, environment);
            }
        }

        modelBatch.end();

        stringBuilder.setLength(0);
        stringBuilder.append("Equation : ").append(course.getEquation());
        label.setText(stringBuilder);
        stage1.draw();

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
        model.dispose();
    }

    @Override
    public void show() {}

    /**
     * this method determines if the given instance is visible on the screen
     * @param camera the game camera that makes the instances visible
     * @return true if the instance is visible
     */
    private boolean isVisible(final Camera camera) {
        ball.transform.getTranslation(position);
        return camera.frustum.pointInFrustum(position);
    }

    /**
     * method that builds a surface from Vector2Ds
     * @param start  is the start coordinate
     * @param goal   is the goal coordinate
     * @return an array of Vector2D's that represents the surface
     */
    private Vector2D[] getBase(Vector2D start, Vector2D goal) {
        double margin = 25;
        double xMin, xMax, yMin, yMax;
        if (start.getX() > goal.getX()) {
            xMax = start.getX() + margin;
            xMin = goal.getX() - margin;
        } else {
            xMin = start.getX() - margin;
            xMax = goal.getX() + margin;
        }
        if (start.getY() > goal.getY()) {
            yMax = start.getY() + margin;
            yMin = goal.getY() - margin;
        } else {
            yMin = start.getY() - margin;
            yMax = goal.getY() + margin;
        }
        return new Vector2D[]{new Vector2D(xMin, yMin), new Vector2D(xMax, yMax)};
    }

    /**
     * this model calls the overloaded getModel to construct the field
     * @param mesh     the parts of the field
     * @param gl       the primitive GL type
     * @param material is the texture of the field
     * @return a model that overloaded getmodel() returns
     */
    public static Model getModel(final Mesh mesh, int gl, final Material material) {
        return getModel(mesh, 0, mesh.getNumIndices(), gl, material);
    }

    /**
     * this method builds a Model instance from mesh parts and returns a field Model
     * @param mesh     the parts of the field
     * @param offset   an offset value to provide in mesh part
     * @param vertices vertices of the mesh
     * @param gl       the primitive GL type
     * @param material is the texture of the field
     * @return a field model
     */
    public static Model getModel(final Mesh mesh, int offset, int vertices, int gl, final Material material) {
        Model finalModel = new Model();
        MeshPart meshPart = new MeshPart();
        meshPart.set("meshNode", mesh, offset, vertices, gl);

        NodePart partMaterial = new NodePart(meshPart, material);
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

    /**
     * Create the stage that allow the user to enter the details of his shot.
     * @param skin1 "police" of the text in this stage.
     */
    public void shootStage(Skin skin1) {
        TextArea dirxText = new TextArea("Vector x:", skin1);
        dirxText.setDisabled(true);
        dirxText.setPosition(0, ScreenSpace.HEIGHT - 30);
        dirxText.setSize(100, 30);
        stage1.addActor(dirxText);
        TextArea diryText = new TextArea("Vector y:", skin1);
        diryText.setDisabled(true);
        diryText.setPosition(0, ScreenSpace.HEIGHT - 60);
        diryText.setSize(100, 30);
        stage1.addActor(diryText);

        shoot = new TextButton("Shoot", skin1);
        shoot.setDisabled(true);
        shoot.setPosition(0, ScreenSpace.HEIGHT - 90);
        shoot.setSize(150, 30);

        shoot.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                if (Variables.ai) {
                    // If the button is clicked, prepare the next shot for the AI.
                    count++;
                    shoot.setDisabled(true);
                    shoot.setVisible(false);
                }
                // Set the ball as hit.
                ballReachedFlag = true;
                shoot.setVisible(false);
                course.getBall().hit();
            }
        });
        stage1.addActor(shoot);

        // The fields where the user can enter the x and the y values of his shot vector.
        dirX = new TextField(String.valueOf(Variables.shootX), skin1);
        dirX.setPosition(100, ScreenSpace.HEIGHT - 30);
        dirX.setSize(50, 30);
        stage1.addActor(dirX);
        dirY = new TextField(String.valueOf(Variables.shootY), skin1);
        dirY.setPosition(100, ScreenSpace.HEIGHT - 60);
        dirY.setSize(50, 30);
        stage1.addActor(dirY);
    }

    /**
     * creates the mesh that represents the field of the game
     */
    public void createMesh() {
        Texture fieldTex = new Texture("groundTexture.jpg");
        Vector2D[] coverVectors = getBase(new Vector2D(Variables.startX, Variables.startY),
                new Vector2D(Variables.goalX, Variables.goalY));
        int chunkSize = 1;
        int numberX = (int) (coverVectors[1].getX() - coverVectors[0].getX()) / chunkSize;
        int numberY = (int) (coverVectors[1].getY() - coverVectors[0].getY()) / chunkSize;
        TerrainChunk chunk;
        Vector2D currentPos;
        Material material;

        for (int x = 0; x < numberX; x++) {
            for (int y = 0; y < numberY; y++) {
                currentPos = new Vector2D(coverVectors[0].getX() + chunkSize * x, coverVectors[0].getY() + chunkSize * y);
                chunk = new TerrainChunk(currentPos, chunkSize, course,false);
                chunk.setLocation((float) course.evaluate(new Vector2D(x * chunkSize, y * chunkSize)));

                Mesh mesh = new Mesh(true, chunk.vertices.length / 9, chunk.indices.length,
                        new VertexAttribute(VertexAttributes.Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE),
                        new VertexAttribute(VertexAttributes.Usage.Normal, 3, ShaderProgram.NORMAL_ATTRIBUTE),
                        new VertexAttribute(VertexAttributes.Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE),
                        new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE));
                mesh.setVertices(chunk.vertices);
                mesh.setIndices(chunk.indices);


                material = new Material(TextureAttribute.createDiffuse(fieldTex));
                ball.transform.setTranslation((float) course.getBall().getLocation().getX(),
                        (float) course.evaluate(new Vector2D(course.getBall().getLocation().getX(),
                        course.getBall().getLocation().getY())) - 1f, (float) course.getBall().getLocation().getY());

                Model terrain = getModel(mesh, GL20.GL_TRIANGLES, material);
                ModelInstance terrainInstance = new ModelInstance(terrain, 0, 0, 0);

                chunk.setModelInstance(terrainInstance);
                instances.add(terrainInstance);

                terrainInstance.transform.setTranslation((float) currentPos.getX(), 0, (float) currentPos.getY());
            }
        }
    }

    /**
     * Getter method
     * @return the model builder of the object.
     */
    public static ModelBuilder getModelBuilder() {
        return modelBuilder;
    }

    /**
     * Getter method
     * @return the Putting course of the world.
     */
    public static PuttingCourse getCourse() {
        return course;
    }

    /**
     * Method that creates the walls of the maze.
     */
    public void createWalls() {
       MazeGenerator maze = new MazeGenerator(Variables.mazeX, Variables.mazeY);    // Instiate the mazeGenerator Object.
        maze.updateGrid();
        maze.addStartAndEnd();
        Wall wallGenerator = new Wall();
        redwallsinstances = new ArrayList<>();

        // Goes through the array of integers that represent the maze and draw the walls when needed.
        for (int i=0; i< maze.getGrid().length; i++){
            for(int j=0; j<maze.getGrid()[i].length; j++){
                if (maze.getGrid()[i][j] == 1){
                    // If the integer at that position is 1, create a wall.
                    ModelInstance[] wallInstances = wallGenerator.createModel(i, (float) (j*2)+0.25f);
                    instances.add(wallInstances[0]);
                    redwallsinstances.add(wallInstances[0]);
                }else if(maze.getGrid()[i][j] == 8){
                    // Set the start position as the one in the array.
                    Variables.startX = i-20 + 1;
                    Variables.startY = (float) ((j*2)-20.5);
                }else if (maze.getGrid()[i][j] == 9) {
                    // Set the hole position as the one in the array.
                    Variables.goalX = i - 21;
                    Variables.goalY = (float) ((j * 2) - 20);
                }
            }
        }
        Solver solver = new Solver(maze.getCells());    // Create the solver array.
        solver.solve();                                 // Solve the maze.
        locations = solver.getLocations();              // Location of all the steps needed to solve the maze.
    }

    /**
     * wall collision
     * @param x x positzion of the obstacle
     * @param y y position of the obstacle
     * @return true if the ball enter in collision with a wall.
     */
    public static boolean collision(float x, float y) {
        for (ModelInstance instance : redwallsinstances) {
            Vector2 wallLocation = new Vector2(instance.transform.getTranslation(new Vector3()).x, instance.transform.getTranslation(new Vector3()).z);
            if ( (x <= wallLocation.x +0.75f && x+0.75f  >= wallLocation.x) && (y<=wallLocation.y+0.25f && y+2.25f>=wallLocation.y) ) {
                isCollision = true;
                return true;
            }
        }
        return false;
    }
}