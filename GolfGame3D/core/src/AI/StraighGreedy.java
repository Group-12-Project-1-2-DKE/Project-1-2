package AI;

import Course.PuttingCourse;
import Objects.Ball;
import Physics.Function2D;
import Physics.Vector2D;

public class StraighGreedy implements AI{
    public static void main(String[] args) {
        PuttingCourse h = new PuttingCourse("1", new Vector2D(2,-3), new Vector2D(10,5),
                new Ball(new Vector2D(2,-3), 3, (float)0.5), 0.05, 4, 4);
        AI s = new StraighGreedy();
        //s.calculate_turn(h);
    }

    /**
     * Calculates the approximate initial velocity to calculate a hole-in-one.
     * Algorithm is greedy, so probably inaccurate.
     * @param course
     * @return approximate initial velocity vector
     */
    @Override
    public Vector2D calculate_turn(PuttingCourse course, double[] heights, double step_size) {
        double grav_constant = 9.81;
        Ball ball = course.getBall();
        Vector2D begin = ball.getLocation();
        Vector2D end = course.getFlag();
        Vector2D direction = end.add(begin.multiply(-1));
        double factor = step_size/direction.length();
        Vector2D scaled_direction = direction.multiply(factor);
        Vector2D Ffric = direction.multiply(-ball.getMass() * grav_constant * course.getFrictionCoefficient());

        Vector2D returnvec = new Vector2D(direction.getX(), direction.getY());
        for (int i = 0; i < heights.length - 1; i++){
            double h = heights[i];
            double h2 = heights[i+1];
            double difference = h2 - h;
            Vector2D gradient = new Vector2D(difference/scaled_direction.getX(), difference/scaled_direction.getY());
            Vector2D Fgrav = gradient.multiply(-ball.getMass() * grav_constant);
            Vector2D Fresist = Ffric.add(Fgrav);

            if (Fresist.getX() * scaled_direction.getX() < 0){
                returnvec.setX(returnvec.getX() - Fresist.getX());
            }
            if (Fresist.getY() * scaled_direction.getY() < 0){
                returnvec.setY(returnvec.getY() - Fresist.getY());
            }
        }
        return returnvec;
    }
}
