package Physics;

import Objects.*;

public interface PhysicsEngine{
    Vector2D calculateShot(Vector2D initial_v, Ball ball, Function2D course);
    void set_step_size(double h);
    void set_fric_coefficient(double f);
    void set_grav_constant(double g);
    void set_max_error(double e);
    void wall_collision(Ball ball);
    Vector2D tree_collision(Ball ball, TreeObstacle tree, Vector2D final_v);
}
