package Objects;

import Physics.Vector2D;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

import java.util.ArrayList;

public interface Obstacle{

    ModelInstance createModel();
    ArrayList<Obstacle> createInstance(int n);

}