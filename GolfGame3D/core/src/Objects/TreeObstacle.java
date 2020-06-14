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



public class TreeObstacle implements Obstacle{

    private final double width = 0.5f;
    private final double height= 6f;
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
    public ModelInstance[] createModel(float x , float z) {
        Model tree = GolfGame.getModelBuilder().createCylinder(0.5f, 6, 0.5f, 20,
                new Material(ColorAttribute.createDiffuse(Color.BROWN)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        ModelInstance treeInstance = new ModelInstance(tree, x, (float)GolfGame.getCourse().evaluate(x,z), z);

        Model branch = GolfGame.getModelBuilder().createCone(2,3,2,20,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        ModelInstance branchInstance = new ModelInstance(branch, x, (float)GolfGame.getCourse().evaluate(x,z) + 3.5f,z);
        ModelInstance[] instance = new ModelInstance[2];
        instance[0] = treeInstance;
        instance[1] = branchInstance;
        this.setLocation(new Vector2D(x,z));
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

    public double getHeight() {
        return height;
    }

    public Vector2D getLocation() {
        return location;
    }
}
