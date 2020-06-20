package Maze;

import Objects.Obstacle;
import Physics.Vector2D;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.mygdx.game.GolfGameMaze;

import java.util.ArrayList;

public class Wall implements Obstacle {
    Vector2D location;

    public Wall() {
    }

    @Override
    public ModelInstance[] createModel(float x, float z) {
//        Model wall = GolfGame.getModelBuilder().createBox(1f,4f,1.5f,
//                new Material(ColorAttribute.createDiffuse(Color.GRAY)),
//                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        Model wall = GolfGameMaze.getModelBuilder().createBox(1f,1f,2f,
                new Material(ColorAttribute.createDiffuse(Color.RED)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        ModelInstance wallInstance = new ModelInstance(wall, x-20, 1, z-20);

        ModelInstance[] instance = new ModelInstance[1];
        instance[0] = wallInstance;
        this.setLocation(new Vector2D(x,z));

        return instance;
    }

    private void setLocation(Vector2D location) {
        this.location = location;
    }

    @Override
    public ArrayList<Obstacle> createInstance(int n) {
        return null;
    }

    @Override
    public Vector2D getLocation() {
        return null;
    }
}
