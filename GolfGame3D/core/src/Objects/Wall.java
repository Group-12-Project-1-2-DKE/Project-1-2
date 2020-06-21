package Objects;

import Objects.Obstacle;
import Physics.Vector2D;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.mygdx.game.GolfGameMaze;

/**
 * Class that implements Object interfrace.
 * This class creates a walls at a certain position.
 */
public class Wall implements Obstacle {
    Vector2D location;

    /**
     * Default constructor of the Wall object.
     */
    public Wall() {}

    /**
     * Creates a model instance that can be used in the graphics
     * @return model instance that contains the wall.
     */
    @Override
    public ModelInstance[] createModel(float x, float z) {
        Model wall = GolfGameMaze.getModelBuilder().createBox(1f,1f,2f,
                new Material(ColorAttribute.createDiffuse(Color.RED)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        ModelInstance wallInstance = new ModelInstance(wall, x-20, 1, z-20);

        ModelInstance[] instance = new ModelInstance[1];
        instance[0] = wallInstance;
        this.setLocation(new Vector2D(x,z));

        return instance;
    }

    /**
     * Setter method. Change the location of the wall.
     * @param location Vector2D that represent the new location of the wall.
     */
    private void setLocation(Vector2D location) {
        this.location = location;
    }

    /**
     * Getter method
     * @return a Vector2D object that represent the location of the obstacle
     */
    @Override
    public Vector2D getLocation() {
        return null;
    }
}
