package Physics;

import Objects.Ball;

public class RungeKuttaSolver implements PhysicsEngine{
    private double step_size = 0.1;
    private double fric_coefficient = 0.1; //Typical 0.065<=mu<=0.196
    private double grav_constant = 9.81;

    @Override
    public Vector2D calculateShot(Vector2D initial_v, Ball ball, Function2D course) {

        return null;
    }

    public Vector2D gravitational_force(Ball ball, Function2D height){
        Vector2D gradient = height.gradient(ball.getLocation());
        double x = -1 * ball.getMass() * grav_constant * gradient.getX();
        double y = -1 * ball.getMass() * grav_constant * gradient.getY();
        return new Vector2D(x, y);
    }

    public Vector2D friction_force(Ball ball, Vector2D initial_v) {
        double frictionF = -(fric_coefficient * ball.getMass() * grav_constant / initial_v.length());
        return initial_v.multiply(frictionF);
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
