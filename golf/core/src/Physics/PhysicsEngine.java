package Physics;

import Objects.*;

public interface PhysicsEngine{
    public void calculateShot(Vector2D initial_v, Ball ball, Function2D course);
}