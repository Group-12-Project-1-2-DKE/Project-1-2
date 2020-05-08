package Physics;

import Objects.*;

public class VerletSolver implements PhysicsEngine{
    private double step_size = 0.1;
    private double fric_coefficient = 0.1; //Typically 0.065<=mu<=0.196
    private double grav_constant = 9.81;
    private double initial_acc = 0;

    public VerletSolver(){

    }

    public Vector2D calculateShot(Vector2D initial_v, Ball ball, Function2D course){
        Vector2D acc = calculate_acc(course, initial_v, ball);

        //newPosition+=timeStep*(velocity+acceleration*timeStep*0.5);
        ball.setLocation(ball.getLocation().add(initial_v.add(initial_acc.multiply(step_size).multiply(0.5)).multiply(0.5)));

        //v+= 0.5*timeStep*(newA+acceleration)
        Vector2D final_v = new Vector2D(0, 0);
        final_v = initial_v.add((initial_acc.add(acc).multiply(step_size).multiply(0.5));//v+= 0.5*timeStep*(newA+acceleration)
        initial_acc = acc;

        if (ball.getLocation().getX() > 900 || ball.getLocation().getX() < 0) {
            final_v.setX(- 1 * final_v.getX()) ;
        }

        if (ball.getLocation().getY() > 700 || ball.getLocation().getY() < 0) {
            final_v.setY(- 1 * final_v.getY());
        }

        //calculateShot(final_v, ball, course);
        if ((int)(final_v.length() * 10) == 0 && (int)(initial_acc.length() * 10) == 0) {
            ball.putAtRest();
            return new Vector2D(0, 0);

        }
        return final_v;
    }

    public void set_step_size(double h){
        step_size = h;
    }

    public void set_fric_coefficient(double f){
        fric_coefficient = f;
    }

    public void set_grav_constant(double g){
        grav_constant = g;
    }

    public Vector2D calculate_acc(Function2D height, Vector2D initial_v, Ball ball){
        Vector2D gradient = height.gradient(ball.getLocation());
        return new Vector2D(
                -grav_constant * gradient.getX() - fric_coefficient * grav_constant * initial_v.getX() / initial_v.length(),
                -grav_constant * gradient.getY() - fric_coefficient * grav_constant * initial_v.getY() / initial_v.length()
        );
    }

}