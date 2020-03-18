package Physics;

import Objects.*;

public class EulerSolver implements PhysicsEngine{
    private double step_size = 0.001;
    private double fric_coefficient = 0.1; //Typical 0.065<=mu<=0.196
    private double grav_constant = 9.81;

    public EulerSolver(){

    }

    public Vector2D calculateShot(Vector2D initial_v, Ball ball, Function2D course){
        Vector2D gForce = gravitational_force(ball, course);
        Vector2D fForce = friction_force(ball, initial_v);
        Vector2D Fres = gForce.add(fForce);
        Vector2D acc = calculate_acc(Fres, ball);

        Vector2D final_v = new Vector2D(0, 0);                                  //!<
        if (gForce.length() == 0 && (fForce.length()/Math.pow(ball.getMass(), 2)) >= initial_v.length()){
            final_v.setX(0);
            final_v.setY(0);
        } else {
            final_v = initial_v.add(acc.multiply(step_size));//acc * step_size);
        }
        ball.setLocation(ball.getLocation().add(final_v.multiply(step_size)));
        
        //calculateShot(final_v, ball, course);
        if ((int)(final_v.length() * 1000) == 0 && (int)(acc.length() * 1000) == 0) {
            ball.putAtRest();
            System.out.println("l: " + ball.getLocation());
            System.out.println("v: " + initial_v);
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

    public Vector2D gravitational_force(Ball ball, Function2D height){
        Vector2D gradient = height.gradient(ball.getLocation());
        double x = -1 * ball.getMass() * grav_constant * gradient.getX();
        double y = -1 * ball.getMass() * grav_constant * gradient.getY();
        return new Vector2D(x, y);
    }

    public Vector2D friction_force(Ball ball, Vector2D initial_v) {
        double frictionF = (fric_coefficient * ball.getMass() * grav_constant * initial_v.length()) / Math.sqrt(Math.pow(initial_v.getX(), 2) + Math.pow(initial_v.getY(),2));
        double x_fforce = -(initial_v.getX()/Math.sqrt(frictionF/initial_v.length()));
        double y_fforce = -(initial_v.getY()/Math.sqrt(frictionF/initial_v.length()));
        Vector2D fForce = new Vector2D(x_fforce, y_fforce);
        return fForce;
    }

    public Vector2D calculate_acc(Vector2D Fres, Ball ball) {
        double x_acc = Fres.getX() / Math.pow(ball.getMass(), 2);
        double y_acc = Fres.getY() / Math.pow(ball.getMass(), 2);
        Vector2D acc = new Vector2D(x_acc, y_acc);
        return acc;
    }
}
/*
We describe the position of a ball by its coordinates 𝑝 = (𝑝𝑥, 𝑝𝑦) = (𝑥, 𝑦), with velocity 𝑣 = 𝑝̇ = (𝑣𝑥,𝑣𝑦) = (𝑥̇,𝑦̇) and acceleration 𝑎 = 𝑣̇ = 𝑝̈ = (𝑎𝑥,𝑎𝑦) = (𝑥̈,𝑦̈).

The gravitational force due to the slope is given by 𝐺 = (−𝑚𝑔h,𝑥 (𝑥, 𝑦), −𝑚𝑔h,𝑦 (𝑥, 𝑦)) ,
where 𝑚 is the mass of the ball, and 𝑔 = 9.81ms−2 the acceleration due to gravity.

The force due to friction of a moving object is:
𝐻 = −𝜇𝑚𝑔 𝑣⁄‖𝑣‖
where ‖𝑣‖ = √𝑣2 + 𝑣2 and 𝜇 is the coefficient of friction. 𝑥𝑦 

*/
