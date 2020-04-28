package Physics;

import Objects.Ball;

public class VerletSolver implements PhysicsEngine{
    private double step_size = 0.1;
    private double fric_coefficient = 0.1; //Typical 0.065<=mu<=0.196
    private double grav_constant = 9.81;

    @Override
    public Vector2D calculateShot(Vector2D initial_v, Ball ball, Function2D course) {
        return null;
    }

    @Override
    public void set_step_size(double h) {
        step_size = h;
    }

    @Override
    public void set_fric_coefficient(double f) {
        fric_coefficient = f;
    }

    @Override
    public void set_grav_constant(double g) {
        grav_constant = g;
    }
}
