public class EulerSolver implements PhysicsEngine{
    private double step_size = 0.001;

    public EulerSolver(){

    }

    public void calculateShot(Vector2D initial_v, Ball ball){
        if (initial_v.getX() == 0 && initial_v.getY() == 0) {
            private boolean atRest = true;
            private boolean isHit = false;
        } else {
            Vector2D gForce = new Vector2d();
            gForce = gravitational_force(ball, height);
            Vector2D fForce = new Vector2d();
            fForce = friction_force(ball, initial_v);
            Vector2D Fres = new Vector2D();
            Fres = gForce.add(fForce);
            Vector2D acc = new Vector2D()
            acc = calculate_acc(Fres, ball);
            if (gForce = 0 && (fForce.length()/(ball.getMass())^2) !< initial_v.length()){
                initial_v.x = 0;
                initial_v.y = 0;
            } else {
                initial_v.add(acc * step_size)
            }
            calculateShot(final_v, ball);
        }
    }

    public void set_step_size(double h){
        step_size = h;
    }

    public Vector2D gravitational_force(Ball ball, Function2D height){
        Vector2D gradient = height.gradient(ball.getLocation());
        double x = -1 * ball.getMass() * 9,81 * gradient.getX();
        double y = -1 * ball.getMass() * 9,81 * gradient.getY();
        return new Vector2D(x, y);
    }

    public Vector2D friction_force(Ball ball, Vector2D initial_v) {
        double frictionF = (coefficient * ball.getMass() * 9,81 * initial_v.length()) / sqrt(initial_v.getX()^2 + (initial_v.getY())^2);
        double x_fforce = -(initial_v.getX()/sqrt(frictionF/initial_v.length()));
        double y_fforce = -(initial_v.getY()/sqrt(frictionF/initial_v.length()));
        Vector2D fForce = new Vector2D(x_fforce, y_fforce);
        return fForce;
    }

    public Vector2D calculate_acc(Vector2D Fres, Ball ball) {
        double x_acc = Fres.getX() / (ball.getMass())^2;
        double y_acc = Fres.getY() / (ball.getMass())^2;
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

The equations of motion are
𝑝̈ = 𝑎 = 𝐹⁄𝑚
where 𝐹 = 𝐹(𝑝, 𝑝̇) = 𝐹(𝑝, 𝑣) = 𝐺 + 𝐻 is the total force applied.
These forces give rise to the complete differential equation describing the motion:
𝑥̈ =−𝑔h (𝑥,𝑦)−𝜇𝑔𝑥̇⁄√𝑥̇2 +𝑦̇2; 𝑦̈ =−𝑔h (𝑥,𝑦)−𝜇𝑔𝑦̇⁄√𝑥̇2 +𝑦̇2.
*/
