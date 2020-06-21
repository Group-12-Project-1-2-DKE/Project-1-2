package Physics;

import Objects.Ball;
import Objects.TreeObstacle;
import Objects.Wall;
import com.mygdx.game.GolfGameMaze;
import com.mygdx.game.Variables;

public class RungeKuttaSolver implements PhysicsEngine{
    private double step_size = 0.1;
    private double fric_coefficient = 0.1; //Typical 0.065<=mu<=0.196
    private double grav_constant = 9.81;
    private double max_error = 0.1;

    @Override
    public Vector2D calculateShot(Vector2D initial_v, Ball ball, Function2D course) {
        Vector2D k1 = calculate_acc(course, initial_v, ball).multiply(step_size);
        Vector2D k2 = calculate_acc(course, initial_v.add(k1.multiply(0.5)), ball).multiply(step_size);
        Vector2D k3 = calculate_acc(course, initial_v.add(k2.multiply(0.5)), ball).multiply(step_size);
        Vector2D k4 = calculate_acc(course, initial_v.add(k3), ball).multiply(step_size);

        Vector2D k = k1.add(k2.multiply(2)).add(k3.multiply(2)).add(k4).multiply(1/6.);
        Vector2D final_v = initial_v.add(k);
        ball.setVelocity(final_v);

        ball.setLocation(ball.getLocation().add(final_v.multiply(step_size)));

        if (ball.getLocation().getX() < Variables.lowerBound.getX() || ball.getLocation().getX() > Variables.upperBound.getX()) {
            final_v.setX(- 1 * final_v.getX()) ;
        }

        if (ball.getLocation().getY() > Variables.upperBound.getY() || ball.getLocation().getY() < Variables.lowerBound.getY()) {
            final_v.setY(- 1 * final_v.getY());
        }

        if (final_v.length() < max_error && /*k.length()*/initial_v.add(final_v.multiply(-1)).length() < max_error) {
            ball.putAtRest();
            return new Vector2D(0, 0);
        }
        return final_v;
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

    public Vector2D calculate_acc(Function2D height, Vector2D initial_v, Ball ball){
        Vector2D gradient = height.gradient(ball.getLocation());
        return new Vector2D(
                -grav_constant * gradient.getX() - fric_coefficient * grav_constant * initial_v.getX() / initial_v.length(),
                -grav_constant * gradient.getY() - fric_coefficient * grav_constant * initial_v.getY() / initial_v.length()
        );
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

    @Override
    public void set_max_error(double e) {
        max_error = e;
    }

    public static Vector2D tree_collision(Ball ball, TreeObstacle tree, Vector2D final_v) {
        System.out.println(final_v.toString());
        if (ball.getLocation().getX() - tree.getLocation().getX() == 0) {
            System.out.println("1/5");
            final_v.setY(final_v.getY() * -1);
        }
        double rc = (ball.getLocation().getY() - tree.getLocation().getY())/(ball.getLocation().getX() - tree.getLocation().getX());
        if (rc > Math.tan(0.375 * Math.PI) || rc <= Math.tan(0.625 * Math.PI)) {
            System.out.println("1/5");
            final_v.setY(final_v.getY() * -1);
        } else if (Math.tan(0.125 * Math.PI) < rc && rc <= Math.tan(0.375 * Math.PI)) {
            System.out.println("2/6");
            double temp = final_v.getX();
            final_v.setX(final_v.getY());
            final_v.setY(temp);
            if ((final_v.getX() < 0 && final_v.getY() < 0) || (final_v.getX() > 0 && final_v.getY() > 0) || final_v.getX() == 0 || final_v.getY() == 0) {
                final_v.setX(final_v.getX() * -1);
                final_v.setY(final_v.getY() * -1);
            }
        } else if (Math.tan(0.875 * Math.PI) < rc && rc <= Math.tan(0.125 * Math.PI)) {
            System.out.println("3/7");
            final_v.setX(final_v.getX() * -1);
        } else if (Math.tan(0.625 * Math.PI) < rc && rc <= Math.tan(0.875 * Math.PI)) {
            System.out.println("4/8");
            double temp = final_v.getX();
            final_v.setX(final_v.getY());
            final_v.setY(temp);
            if((final_v.getX() < 0 && final_v.getY() > 0) || (final_v.getX() > 0 && final_v.getY() < 0)) {
                final_v.setX(final_v.getX() * -1);
                final_v.setY(final_v.getY() * -1);
            }
        }
        return final_v;
    }

    public Vector2D rock_collision(Ball ball, Wall wall, Vector2D final_v) {
        if (x_edge(ball, wall, final_v)) {
            final_v.setY(final_v.getY()*-1);
        }
        if (y_edge(ball, wall, final_v)) {
            final_v.setX(final_v.getX()*-1);
        }
        System.out.println(final_v.toString());
        return final_v;
    }

    public boolean x_edge(Ball ball, Wall wall, Vector2D final_v) {
        float x = (float) ball.getLocation().getX();
        float y = (float) (ball.getLocation().getY() - final_v.getY() * step_size);
        if (GolfGameMaze.collision(x, y)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean y_edge(Ball ball, Wall wall, Vector2D final_v) {
        float x = (float) (ball.getLocation().getX() - final_v.getX() * step_size);
        float y = (float) ball.getLocation().getY();
        if (GolfGameMaze.collision(x, y)) {
            return true;
        } else {
            return false;
        }
    }

}
