package AI;

import Course.PuttingCourse;
import Objects.Ball;
import Physics.*;

import java.util.Arrays;

public class StraighGreedy implements AI{
    public static void main(String[] args) {
        PuttingCourse h = new PuttingCourse("0.5*x^2 + 0.1*y", new Vector2D(0,0), new Vector2D(10,5),
                new Ball(new Vector2D(0,0), 1, (float)0.5), 0.1, 4, 4);
        StraighGreedy s = new StraighGreedy();

        int steps = 10;
        double[] heights = s.getHeights(h, h.getBall().getLocation(), h.getFlag(), steps);

        System.out.println(Arrays.toString(heights));

    }

    /**
     * Calculates the approximate initial velocity to calculate a hole-in-one.
     * Algorithm is greedy, so probably inaccurate.
     * @param course
     * @return approximate initial velocity vector
     */

    @Override
    public Vector2D calculate_turn(PuttingCourse course, int steps) {
        double grav_constant = 9.81;
        Ball ball = course.getBall();
        Vector2D begin = ball.getLocation();
        Vector2D end = course.getFlag();
        Vector2D direction = end.add(begin.multiply(-1));
        double step_size = direction.length()/steps;
        Vector2D scaled_direction = direction.multiply(1/(double)steps);
        Vector2D Ffric = direction.multiply(-ball.getMass() * grav_constant * course.getFrictionCoefficient());
        Vector2D Fresist = new Vector2D(0,0);
        Vector2D Ftotal = new Vector2D(0,0);
        Vector2D returnvec = new Vector2D(direction.getX(), direction.getY());
        double[] heights = getHeights(course, begin, end, steps);

        for (int i = 0; i < heights.length - 1; i++) {
            double h = heights[i];
            double h2 = heights[i + 1];
            double difference = h2 - h;
            Vector2D gradient = new Vector2D(difference / scaled_direction.getX(), difference / scaled_direction.getY());
            Vector2D Fgrav = gradient.multiply(-ball.getMass() * grav_constant);
            Fresist = Ffric.add(Fgrav);
            Ftotal = Ftotal.add(Fresist);
        }
        Vector2D acc = Ftotal.multiply((-1/ball.getMass())*(1/(double)steps));
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

    /**
     * method to get the heights to provide in calculate_turn method in ai
     * @return an array of heights of the map
     */
    private double[] getHeights(Function2D course, Vector2D begin, Vector2D end, int steps) {
        double[] heights = new double[steps + 1];
        Vector2D difference = end.add(begin.multiply(-1));
        Vector2D step = difference.multiply(1/(double)steps);

        Vector2D current_step = begin.clone();
        for (int i = 0; i < heights.length; i++){
            heights[i] = course.evaluate(current_step);
            current_step = current_step.add(step);
        }
        return heights;
    }
}
