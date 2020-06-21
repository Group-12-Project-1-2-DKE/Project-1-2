package Objects;

import Physics.Vector2D;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.mygdx.game.GolfGameNoMaze;

/**
 * Class that implements Object interface.
 * This class creates a tree at a certain position.
 */
public class TreeObstacle implements Obstacle{
    private Vector2D location;

    /**
     * default constructor method
     */
    public TreeObstacle(){}

    /**
     * Creates a model instance that can be used in the graphics
     * @return model instance that contains the tree.
     */
    @Override
    public ModelInstance[] createModel(float x , float z) {
        Model tree = GolfGameNoMaze.getModelBuilder().createCylinder(0.5f, 6, 0.5f, 20,
                new Material(ColorAttribute.createDiffuse(Color.BROWN)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        ModelInstance treeInstance = new ModelInstance(tree, x, (float)GolfGameNoMaze.getCourse().evaluate(x,z), z);

        Model branch = GolfGameNoMaze.getModelBuilder().createCone(2,3,2,20,
                new Material(ColorAttribute.createDiffuse(Color.FOREST)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        ModelInstance branchInstance = new ModelInstance(branch, x, (float)GolfGameNoMaze.getCourse().evaluate(x,z) + 3.5f,z);
        ModelInstance[] instance = new ModelInstance[2];
        instance[0] = treeInstance;
        instance[1] = branchInstance;
        this.setLocation(new Vector2D(x,z));
        return instance;
    }

    /**
     * Set the location as a new location.
     * @param location Vector2D that represent the location of the obstacle.
     */
    public void setLocation(Vector2D location){
        this.location = location;
    }

    /**
     * Getter method
     * @return a double that represent the height of the obstacle.
     */
    public double getHeight() {
        return 6f;
    }

    /**
     * Getter method
     * @return a Vector2D object that represent the location of the obstacle
     */
    public Vector2D getLocation() {
        return location;
    }
}