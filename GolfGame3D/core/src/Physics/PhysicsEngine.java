package Physics;

import Objects.*;

public interface PhysicsEngine{
    Vector2D calculateShot(Vector2D initial_v, Ball ball, Function2D course);
    void set_step_size(double h);
    void set_fric_coefficient(double f);
    void set_grav_constant(double g);
}
