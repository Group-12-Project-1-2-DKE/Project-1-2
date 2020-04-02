package Physics;

import Objects.*;

public interface PhysicsEngine{
    public Vector2D calculateShot(Vector2D initial_v, Ball ball, Function2D course);
}
