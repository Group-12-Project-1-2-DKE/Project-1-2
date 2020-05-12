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
        Vector2D Fresist = new Vector2D(0,0);

        Vector2D returnvec = new Vector2D(direction.getX(), direction.getY());
        for (int i = 0; i < heights.length - 1; i++) {
            double h = heights[i];
            double h2 = heights[i + 1];
            double difference = h2 - h;
            Vector2D gradient = new Vector2D(difference / scaled_direction.getX(), difference / scaled_direction.getY());
            Vector2D Fgrav = gradient.multiply(-ball.getMass() * grav_constant);
            Fresist = Ffric.add(Fgrav);
        }
        Vector2D acc = Fresist.multiply((-1/ball.getMass())*(factor));
        double accX = acc.getX();
        double accY = acc.getY();
        if(direction.getY()/direction.getX() > acc.getY()/acc.getX()){
            acc.setX(acc.getY()*direction.getX()/direction.getY());
            accX -= acc.getX();
            accX *= 2;
        }
        else if(direction.getY()/direction.getX() < acc.getY()/acc.getX()){
            acc.setY(acc.getX()*direction.getY()/direction.getX());
            accY -= acc.getY();
            accY *= 2;
        }

        returnvec = acc.multiply((2*direction.length()));
        // returnvec = returnvec.sqrt();

        if(accX == acc.getX()){
            returnvec.setY(returnvec.getY()+acc.getY());
        }
        else if(accY == acc.getY()){
            returnvec.setX(returnvec.getX()+acc.getX());
        }

        return returnvec;
            /*
            if (acc.getX() * scaled_direction.getX() < 0){
                returnvec.setX(returnvec.getX() - acc.getX());
            }
            if (acc.getY() * scaled_direction.getY() < 0){
                returnvec.setY(returnvec.getY() - acc.getY());
            }
             */


    }
}
