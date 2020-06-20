package com.mygdx.game;

import AI.MazeAI;
import Course.PuttingCourse;
import Maze.MazeGenerator;
import Maze.Solver;
import Maze.Wall;
import Objects.Ball;
import Objects.RockObstacle;
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
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Menus.Congrat;
import java.util.ArrayList;

import static com.mygdx.game.Variables.euler;

public class GolfGameMaze implements Screen{
    private static PuttingCourse course;
    private PuttingSimulator simulator;
    private boolean ballReachedFlag = false;
    private PhysicsEngine engine;
    private MazeAI ai;
    private ArrayList<Vector2D> locations;
    private int count = -1;

    private Environment environment;

    private PerspectiveCamera camera;
    private CameraInputController cameraInputController;

    private Model model;
    public ModelInstance ball;
    ModelInstance flag;
    private ModelBatch modelBatch;
    private static ModelBuilder modelBuilder;
    private static ArrayList<ModelInstance> instances;

    private Stage stage1;
    private Label label;
    BitmapFont font;
    private StringBuilder stringBuilder;

    private ScreenSpace game;

    private TextButton shoot;
    private TextField dirX;
    private TextField dirY;

    private Vector3 position;
    public int attempt = 0;

    public float myDelta = 0;
    public Vector2D myVector;

    private static float[] wallPositionX = new float[Variables.mazeX];
    private static  float[] wallPositionY = new float[Variables.mazeY];

    RockObstacle rock;

    public GolfGameMaze(ScreenSpace game) {
        EulerSolver eulerSolver = new EulerSolver();
        this.game = game;

        modelBuilder = new ModelBuilder();
        instances = new ArrayList<>();

        createWalls();

        Ball gameBall = new Ball(new Vector2D(Variables.startX, Variables.startY), Variables.ballMass, 0.5f); //i entered some random values

        course = new PuttingCourse(Variables.function, new Vector2D(Variables.startX, Variables.startY), new Vector2D(Variables.goalX, Variables.goalY), gameBall, Variables.coefficientOfFriction, 7, Variables.tolerance);//again some  random values
        ai = new MazeAI();

        if (euler) {
            EulerSolver euler = new EulerSolver();
            eulerSolver.set_step_size(0.01);
            eulerSolver.set_fric_coefficient(course.getFrictionCoefficient());
            eulerSolver.set_grav_constant(9.81);
            engine = eulerSolver;
        } else if (Variables.rungeKutta) {
            RungeKuttaSolver rungeKuttaSolver = new RungeKuttaSolver();
            rungeKuttaSolver.set_step_size(0.01);
            rungeKuttaSolver.set_fric_coefficient(course.getFrictionCoefficient());
            rungeKuttaSolver.set_grav_constant(9.81);
            engine = rungeKuttaSolver;
        } else if (Variables.verlet) {
            VerletSolver verletSolver = new VerletSolver();
            verletSolver.set_step_size(0.01);
            verletSolver.set_fric_coefficient(course.getFrictionCoefficient());
            verletSolver.set_grav_constant(9.81);
            engine = verletSolver;
        }

        simulator = new PuttingSimulator(course, engine);

        position = new Vector3((float) course.getStart().getX(), (float) course.evaluate(new Vector2D(course.getStart().getX(), course.getStart().getY())), (float) course.getStart().getY());

        Bullet.init();
        stage1 = new Stage();
        font = new BitmapFont();
        label = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
        stage1.addActor(label);
        stringBuilder = new StringBuilder();

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

        modelBuilder.node().id = "ball";
        modelBuilder.part("sphere", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal,
                new Material(TextureAttribute.createDiffuse(ballTex))).sphere(0.5f, 0.5f, 0.5f, 10, 10);

        modelBuilder.node().id = "flagPole";
        modelBuilder.part("cylinder", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal,
                new Material(ColorAttribute.createDiffuse(Color.PINK))).cylinder(Variables.tolerance, 0.1f, Variables.tolerance, 10);

        model = modelBuilder.end();
        ball = new ModelInstance(model, "ball");
        flag = new ModelInstance(model, "flagPole");

        ball.transform.setTranslation((float) course.getStart().getX(), (float) course.evaluate(new Vector2D(course.getStart().getX(), course.getStart().getY())), (float) course.getStart().getY());
        flag.transform.setTranslation((float) course.getFlag().getX(), (float) course.evaluate(new Vector2D(course.getFlag().getX(), course.getFlag().getY())), (float) course.getFlag().getY());

        instances.add(ball);
        instances.add(flag);

        createMesh();

        Variables.lowerBound = new Vector2D(-100, -100);
        Variables.upperBound = new Vector2D(100, 100);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.5f, 0.2f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        Skin skin1 = new Skin(Gdx.files.internal("uiskin.json"));

        shootStage(skin1);
        rock = new RockObstacle();
    }

    @Override
    public void render(float delta) {
        myDelta += delta;
        if (Gdx.input.getX() < 150 && Gdx.input.getX() > 0 && ScreenSpace.HEIGHT - Gdx.input.getY() < ScreenSpace.HEIGHT && ScreenSpace.HEIGHT - Gdx.input.getY() > ScreenSpace.HEIGHT - 150) {
            Gdx.input.setInputProcessor(stage1);
        } else {
            Gdx.input.setInputProcessor(cameraInputController);
        }
        if (((((course.getFlag().getX() - course.getTolerance() <= course.getBall().getLocation().getX()) &&
                (course.getBall().getLocation().getX() <= course.getFlag().getX() + course.getTolerance())))
                && (course.getFlag().getY() - course.getTolerance() <= course.getBall().getLocation().getY())
                && (course.getBall().getLocation().getY() <= course.getFlag().getY() + course.getTolerance()))) {
            game.setScreen(new Congrat(game, attempt));

        } else {
            ball.transform.setTranslation((float) course.getBall().getLocation().getX(), (float) course.evaluate(new Vector2D(course.getBall().getLocation().getX(), course.getBall().getLocation().getY())) + 0.25f,
                    (float) course.getBall().getLocation().getY() + 1f);
        }
        if (ballReachedFlag) {
            try {
                if (myVector == null) {
                    if (Variables.ai) {
                            System.out.println(locations.toString());
                            Vector2D aiVec = ai.calculate_turn(course, 500, locations.get(count));
                            dirX.setText("" + aiVec.getX());
                            dirY.setText("" + aiVec.getY());
                            myVector = simulator.take_shotSlowly(new Vector2D(Float.parseFloat(dirX.getText()), Float.parseFloat(dirY.getText())));

                    }
                    attempt++;
                    myVector = simulator.take_shotSlowly(new Vector2D(Float.parseFloat(dirX.getText()), Float.parseFloat(dirY.getText())));
                }
                else {
                    myVector = simulator.take_shotSlowly(myVector);
                }
                for(int i = 0; i < wallPositionX.length; i++){
                    if(collisionWalls(wallPositionX[i],wallPositionY[i])) {
                        EulerSolver eulerSolver = new EulerSolver();
                        eulerSolver.rock_collision(course.getBall() ,rock, course.getBall().getVelocity());
                        System.out.println("collision");
                    }
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

        camera.lookAt((float) course.getBall().getLocation().getX(), -(float) course.evaluate(new Vector2D(course.getBall().getLocation().getX(), course.getBall().getLocation().getY())), (float) course.getBall().getLocation().getY());
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
     *
     * @param camera the game camera that makes the instances visible
     * @return true if the instance is visible
     */
    private boolean isVisible(final Camera camera) {
        ball.transform.getTranslation(position);
        return camera.frustum.pointInFrustum(position);
    }

    /**
     * method that builds a surface from Vector2Ds
     *
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
     *
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
     *
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
                    count++;
                    shoot.setDisabled(true);
                    shoot.setVisible(false);
                }
                ballReachedFlag = true;
                shoot.setVisible(false);
                course.getBall().hit();
            }
        });
        stage1.addActor(shoot);

        dirX = new TextField(String.valueOf(Variables.shootX), skin1);
        dirX.setPosition(100, ScreenSpace.HEIGHT - 30);
        dirX.setSize(50, 30);
        stage1.addActor(dirX);
        dirY = new TextField(String.valueOf(Variables.shootY), skin1);
        dirY.setPosition(100, ScreenSpace.HEIGHT - 60);
        dirY.setSize(50, 30);
        stage1.addActor(dirY);
    }

    public void createMesh() {
        Texture fieldTex = new Texture("groundTexture.jpg");
        Vector2D[] coverVectors = getBase(new Vector2D(Variables.startX, Variables.startY), new Vector2D(Variables.goalX, Variables.goalY));
        int chunkSize = 1;
        int numberX = (int) (coverVectors[1].getX() - coverVectors[0].getX()) / chunkSize;
        int numberY = (int) (coverVectors[1].getY() - coverVectors[0].getY()) / chunkSize;
        TerrainChunk chunk;
        Vector2D currentPos;
        TerrainChunk.setFunction(Variables.function);
        TerrainChunk[][] terrainChunks = new TerrainChunk[numberX][numberY];
        Material material;

        for (int x = 0; x < numberX; x++) {
            for (int y = 0; y < numberY; y++) {
                currentPos = new Vector2D(coverVectors[0].getX() + chunkSize * x, coverVectors[0].getY() + chunkSize * y);
                chunk = new TerrainChunk(currentPos, chunkSize, course);
                chunk.setLocation((float) course.evaluate(new Vector2D(x * chunkSize, y * chunkSize)));
                terrainChunks[x][y] = chunk;

                Mesh mesh = new Mesh(true, chunk.vertices.length / 9, chunk.indices.length,
                        new VertexAttribute(VertexAttributes.Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE),
                        new VertexAttribute(VertexAttributes.Usage.Normal, 3, ShaderProgram.NORMAL_ATTRIBUTE),
                        new VertexAttribute(VertexAttributes.Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE),
                        new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE));
                mesh.setVertices(chunk.vertices);
                mesh.setIndices(chunk.indices);


                material = new Material(TextureAttribute.createDiffuse(fieldTex));
                ball.transform.setTranslation((float) course.getBall().getLocation().getX(), (float) course.evaluate(new Vector2D(course.getBall().getLocation().getX(), course.getBall().getLocation().getY())) - 1f, (float) course.getBall().getLocation().getY());

                Model terrain = getModel(mesh, GL20.GL_TRIANGLES, material);
                ModelInstance terrainInstance = new ModelInstance(terrain, 0, 0, 0);

                chunk.setModelInstance(terrainInstance);
                instances.add(terrainInstance);

                terrainInstance.transform.setTranslation((float) currentPos.getX(), 0, (float) currentPos.getY());
            }
        }
    }

    public static ModelBuilder getModelBuilder() {
        return modelBuilder;
    }

    public static PuttingCourse getCourse() {
        return course;
    }

    public void createWalls() {
        MazeGenerator maze = new MazeGenerator(Variables.mazeX, Variables.mazeY);
        maze.updateGrid();
        maze.addStartAndEnd();
        // System.out.print(maze);
        Wall wallGenerator = new Wall();

        for (int i=0; i< maze.getGrid().length; i++){
            for(int j=0; j<maze.getGrid()[i].length; j++){
                if (maze.getGrid()[i][j] == 1){
                    ModelInstance[] wallInstances = wallGenerator.createModel(i, (float) (j*2));
                    instances.add(wallInstances[0]);
                }else if(maze.getGrid()[i][j] == 8){
                    Variables.startX = i-20;
                    Variables.startY = (float) ((j*2)-20);
//				camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//				camera.position.set(-20f, 10f, -20f);
                    //ball.transform.setTranslation(Variables.startX , (float)course.evaluate(Variables.startX, Variables.startY) , Variables.startY);

                }else if (maze.getGrid()[i][j] == 9) {
                    Variables.goalX = i - 20;
                    Variables.goalY = (float) ((j * 2) - 20);
                    //flag.transform.setTranslation(Variables.goalX , (float)course.evaluate(Variables.startX, Variables.startY) , Variables.startY);
                }
            }
        }
        Solver solver = new Solver(maze.getCells());
        solver.solve();
        locations = solver.getLocations();
    }

    /**
     * wall collision
     * @param x x positzion of the obstacle
     * @param y y position of the obstacle
     * @return true if the ball enter in collision with a wall.
     */
    public static boolean collision(float x, float y) {
        //TODO : take corners into consideration to make it more accurate
        for (ModelInstance instance : instances) {
            Vector2 wallLocation = new Vector2(instance.transform.getTranslation(new Vector3()).x, instance.transform.getTranslation(new Vector3()).y);
            if ((x <= wallLocation.x + 0.5 / 3 && x >= wallLocation.x - 0.25 / 3) && (y <= wallLocation.y + 0.25 / 3 && y >= wallLocation.y - 0.25 / 3)) {
                return true;
            }
        }
        return false;
    }

    public static double euclideanDistance(float posX, float posY, int  i ){
      float wallX = wallPositionX[i];
      float wallZ  =wallPositionY[i];
      float euclideanDist = (float) Math.sqrt(Math.pow((posX - wallX), 2) + Math.pow((posY - wallZ), 2));
      return euclideanDist;
    }

    public static boolean collisionWalls(float x, float z){
        for(int i = 0; i < wallPositionX.length; i++){
            if(euclideanDistance((float)course.getBall().getLocation().getX(),(float)course.getBall().getLocation().getY(),i)< 0.5f){
                return true;
            }
        }
        return false;
    }
}