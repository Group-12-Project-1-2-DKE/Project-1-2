package Physics;

import Objects.*;
import com.mygdx.game.GolfGameMaze;
import com.mygdx.game.Variables;

public class VerletSolver implements PhysicsEngine{
    private double step_size = 0.1;
    private double fric_coefficient = 0.1; //Typically 0.065<=mu<=0.196
    private double grav_constant = 9.81;
    private double max_error = 0.1;
    private Vector2D initial_acc = new Vector2D(0,0);
    private int[][] terrainInfo;
    private double f;

    public VerletSolver(){

    }

    public Vector2D calculateShot(Vector2D initial_v, Ball ball, Function2D course){
        findFrictionCoefficient(ball);
        Vector2D acc = calculate_acc(course, initial_v, ball);

        ball.setLocation(ball.getLocation().add(initial_v.add(initial_acc.multiply(step_size).multiply(0.5)).multiply(step_size)));

        Vector2D final_v = initial_v.add((initial_acc.add(acc).multiply(step_size).multiply(0.5)));
        ball.setVelocity(final_v);
        initial_acc = acc;

        if (ball.getLocation().getX() > Variables.upperBound.getX() || ball.getLocation().getX() < Variables.lowerBound.getX()) {
            final_v.setX(- 1 * final_v.getX()) ;
        }

        if (ball.getLocation().getY() > Variables.upperBound.getY() || ball.getLocation().getY() < Variables.lowerBound.getY()) {
            final_v.setY(- 1 * final_v.getY());
        }

        if (final_v.length() < max_error && initial_v.add(final_v.multiply(-1)).length() < max_error){
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
        this.f = f;
    }

    public void set_grav_constant(double g){
        grav_constant = g;
    }

    public void set_max_error(double error) {
        max_error = error;
    }

    public Vector2D calculate_acc(Function2D height, Vector2D initial_v, Ball ball){
        Vector2D gradient = height.gradient(ball.getLocation());
        return new Vector2D(
                -grav_constant * gradient.getX() - fric_coefficient * grav_constant * initial_v.getX() / initial_v.length(),
                -grav_constant * gradient.getY() - fric_coefficient * grav_constant * initial_v.getY() / initial_v.length()
        );
    }

    public Vector2D tree_collision(Ball ball, TreeObstacle tree, Vector2D final_v) {
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

    public void setTerrainInfo(int[][] info){
        this.terrainInfo = info;
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

    public void findFrictionCoefficient(Ball ball){
        int info =  terrainInfo[(int)ball.getLocation().getX()][(int)ball.getLocation().getY()];
        fric_coefficient = f;
        set_fric_coefficient(fric_coefficient + (5 - info) * (0.2 - fric_coefficient)/4);

    }

}