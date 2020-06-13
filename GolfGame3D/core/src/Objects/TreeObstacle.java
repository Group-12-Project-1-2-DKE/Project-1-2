package Objects;

import Physics.Vector2D;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.mygdx.game.Variables;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;


public class TreeObstacle implements Obstacle{

    private final double width = 0.3f;
    private final double height= 0.7f;
    private final double diameter = 0.5f;

    private Vector2D location;

    /**
     * constructor
     */
    public TreeObstacle(){

    }
    /**
     * cretaes a model instance to be used in the graphics
     * @return model instance
     */
    @Override
    public ModelInstance createModel() {
        Model model = new Model();
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.node().id = "tree";
        modelBuilder.part("cylinder", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal,
                new Material(ColorAttribute.createDiffuse(Color.PINK))).cylinder((float)width, (float)height,(float) diameter, 10);
        model = modelBuilder.end();
        ModelInstance instance = new ModelInstance(model);
        return instance;
    }

    @Override
    /**
     * creates an arraylist with multiple obstacles located in a random position
     * @param n is the number of obstacles to be created
     */
    public ArrayList<Obstacle> createInstance(int n) {

        Random random = new Random();
        ArrayList<Obstacle> obstacles = new ArrayList<>();
        for(int i = 0; i <=n; i++){
            TreeObstacle obstacle = new TreeObstacle();
            obstacle.setLocation(new Vector2D(random.nextInt(50),random.nextInt(50)));
            obstacles.add(obstacle);

        }
        return obstacles;
    }


    public void setLocation(Vector2D location){
        this.location = location;
    }
    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDiameter() {
        return diameter;
    }

    public Vector2D getLocation() {
        return location;
    }
}
