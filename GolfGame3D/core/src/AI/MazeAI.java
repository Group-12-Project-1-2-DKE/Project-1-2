package AI;

import Course.PuttingCourse;
import Objects.Ball;
import Physics.*;
import com.mygdx.game.Variables;

public class MazeAI{

    /**
     * Calculates the initial velocity to make a hole-in-one.
     *
     * @param course
     * @param steps
     * @return initial velocity vector
     */
    public Vector2D calculate_turn(PuttingCourse course, int steps, Vector2D end) {
        double grav_constant = Variables.gravity;
        Ball ball = course.getBall();
        Vector2D begin = ball.getLocation();//startlocation of the ball
        Vector2D direction = end.add(begin.multiply(-1));
        double step_size = direction.length()/steps;
        double factor = step_size/direction.length();
        Vector2D scaled_direction = direction.multiply(factor);
        Vector2D Ffric = direction.multiply(-ball.getMass() * grav_constant * course.getFrictionCoefficient());
        Ffric = Ffric.multiply(1/(direction.length()*steps));

        Vector2D returnvec = new Vector2D(0, 0);
        for (int i = 0; i<steps; i++) {
            Vector2D sub_v0 = new Vector2D(
                    -Math.signum(Ffric.getX()) * Math.sqrt(Math.abs(-2 * scaled_direction.getX() * Ffric.getX() / course.getBall().getMass())),
                    -Math.signum(Ffric.getY()) * Math.sqrt(Math.abs(-2 * scaled_direction.getY() * Ffric.getY() / course.getBall().getMass()))
            );
            if (sub_v0.getX() * direction.getX() < 0) {
                sub_v0.setX(0);
            }
            if (sub_v0.getY() * direction.getY() < 0) {
                sub_v0.setY(0);
            }
            returnvec = returnvec.add(sub_v0);
        }
        return returnvec;
    }
}