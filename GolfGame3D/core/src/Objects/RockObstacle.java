
package Objects;

import Physics.Vector2D;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.mygdx.game.GolfGame;

import java.util.ArrayList;
import java.util.Random;

public class RockObstacle implements Obstacle  {

    private final double width = 0.5f;
    private final double height = 0.5f;
    private final double depth = 0.5f;
    private Vector2D location;
    private Vector2D p1,p2,p3,p4,p5,p6,p7,p8;//corners of the obstacle
    private ArrayList<Vector2D> edges;

    public RockObstacle(){

    }


/**
     * cretaes a model instance to be used in the graphics
     * @return model instance
     */

    @Override
    public ModelInstance[] createModel(float x, float z) {
        Model tree = GolfGame.getModelBuilder().createBox(1f, 1f, 1f, 20,
                new Material(ColorAttribute.createDiffuse(Color.GRAY)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        ModelInstance rockInstance = new ModelInstance(tree, x, (float)GolfGame.getCourse().evaluate(x,z), z);

        ModelInstance[] instances = {rockInstance};

        this.setLocation(new Vector2D(x,z));
        return instances;
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
            RockObstacle obstacle = new RockObstacle();
            obstacle.setLocation(new Vector2D(random.nextInt(50),random.nextInt(50)));
            obstacles.add(obstacle);

        }
        return obstacles;
    }

    public void setLocation(Vector2D location){
        this.location = location;
    }

/**
     * accessor for the position of the obstacle
     * @return the location
     */

    public Vector2D getLocation(){
        return this.location;
    }


/**
     * getter for the width of the obstacle
     * @return the width
     */

    public double getWidth(){
        return this.width;
    }


/**
     * getter for the depth of the obstacle
     * @return the depth of the obstacle
     */

    public double getDepth(){
        return this.depth;
    }


/**
     * getter for height
     * @return the height of the obstacle
     */

    public double getHeight(){
        return this.height;
    }


/**
     * might be useful for the collision
     * @return an arraylist containing all the edges of the obstacle
     */

    public ArrayList<Vector2D> getEdges(){
        return edges;

    }
}

