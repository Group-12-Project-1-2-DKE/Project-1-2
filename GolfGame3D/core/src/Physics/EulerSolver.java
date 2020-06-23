package Physics;
import Objects.Wall;
import Objects.*;
import com.mygdx.game.GolfGameMaze;
import com.mygdx.game.Variables;
import java.lang.Math;

public class EulerSolver implements PhysicsEngine{
    private double step_size = 0.1;
    private double fric_coefficient = 0.1; //Typical 0.065<=mu<=0.196
    private double grav_constant = Variables.gravity;
    private double max_error = 0.1;
    //private int[][] terrainInfo;
    //double f;

    public Vector2D calculateShot(Vector2D initial_v, Ball ball, Function2D course){
        //findFrictionCoefficient(ball);
        Vector2D acc = calculate_acc(course, initial_v, ball).multiply(step_size);

        Vector2D final_v = initial_v.add(acc);//acc * step_size);
        ball.setVelocity(final_v);
        ball.setLocation(ball.getLocation().add(final_v.multiply(step_size)));

        if (ball.getLocation().getX() > Variables.upperBound.getX() || ball.getLocation().getX() < Variables.lowerBound.getX()) {
            final_v.setX(- 1 * final_v.getX()) ;
        }

        if (ball.getLocation().getY() > Variables.upperBound.getY() || ball.getLocation().getY() < Variables.lowerBound.getY()) {
            final_v.setY(- 1 * final_v.getY());
        }

        if (final_v.length() < max_error && /*acc.length()*/initial_v.add(final_v.multiply(-1)).length() < max_error) {
            //System.out.println(final_v.toString() + acc.toString());
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
        //this.f = f;
    }

    public void set_grav_constant(double g){
        grav_constant = g;
    }

    @Override
    public void set_max_error(double e) {
        max_error = e;
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

    public Vector2D calculate_acc(Vector2D Fres, Ball ball) {
        double x_acc = Fres.getX() / Math.pow(ball.getMass(), 2);
        double y_acc = Fres.getY() / Math.pow(ball.getMass(), 2);
        Vector2D acc = new Vector2D(x_acc, y_acc);
        return acc;
    }
    public Vector2D calculate_acc(Function2D height, Vector2D initial_v, Ball ball){
        Vector2D gradient = height.gradient(ball.getLocation());
        return new Vector2D(
                -grav_constant * gradient.getX() - fric_coefficient * grav_constant * initial_v.getX() / initial_v.length(),
                -grav_constant * gradient.getY() - fric_coefficient * grav_constant * initial_v.getY() / initial_v.length()
        );
    }

    public  Vector2D tree_collision(Ball ball, TreeObstacle tree, Vector2D final_v) {
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
        ball.setLocation(new Vector2D(ball.getLocation().getX() +0.2f,ball.getLocation().getY()-0.2f));
        if (x_edge(ball, wall, final_v)) {
            final_v.setY(final_v.getY()*-1);
        }
        if (y_edge(ball, wall, final_v)) {
            final_v.setX(final_v.getX()*-1);
        }
        System.out.println(final_v.toString());
        return final_v;
    }

    public void wall_collision(Ball ball){
        ball.getVelocity().setX(ball.getVelocity().getX()*-1);
        ball.getVelocity().setY(ball.getVelocity().getY()*-1);
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

    public void setTerrainInfo(int[][] t){
        //this.terrainInfo = t;
    }

   /* public void findFrictionCoefficient(Ball ball){
       int info =  terrainInfo[ (int)((ball.getLocation().getX() +Variables.lowerBound.getX())*(terrainInfo.length / (Variables.upperBound.getX() - Variables.lowerBound.getX())))][(int)((ball.getLocation().getY() + (int)Variables.lowerBound.getY()) * (terrainInfo.length /(Variables.upperBound.getY() - Variables.lowerBound.getY())))];
       fric_coefficient = f;
       set_fric_coefficient(fric_coefficient + (5 - info) * (0.2 - fric_coefficient)/4);


    }*/
}