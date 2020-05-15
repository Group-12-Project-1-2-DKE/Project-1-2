package AI;

import Course.PuttingCourse;
import Objects.Ball;
import Physics.*;

import java.util.Arrays;

public class StraighGreedy implements AI{
    public static void main(String[] args) {
        PuttingCourse h = new PuttingCourse("0.02*x^2+0.02*y^2", new Vector2D(58,94), new Vector2D(37,9),
                new Ball(new Vector2D(0,0), 1.3, (float)0.5), 0.1, 4, 4);
        StraighGreedy s = new StraighGreedy();
        h.getBall().setLocation(h.getStart());

        int steps = 500;
        //double[] heights = s.getHeights(h, h.getBall().getLocation(), h.getFlag(), steps);
        //System.out.println(Arrays.toString(heights));
        //Vector2D[] gradients = s.getGradients(h, h.getBall().getLocation(), h.getFlag(), steps);
        //System.out.println(Arrays.toString(gradients));

        PhysicsEngine r = new RungeKuttaSolver();
        r.set_fric_coefficient(h.getFrictionCoefficient());
        r.set_grav_constant(9.81);

        PuttingSimulator p = new PuttingSimulator(h, r);

        Vector2D shot = s.calculate_turn(h, steps);
        System.out.println(shot);

        p.take_shot(shot);

        shot = s.calculate_turn(h, steps);
        System.out.println(shot);

        p.take_shot(shot);//test this shit

        h.getBall().setLocation(h.getStart());

        //shot = s.calculate_turn(h, steps);
        //System.out.println(shot);

        //p.take_shot(shot);
    }

    /**
     * Calculates the approximate initial velocity to calculate a hole-in-one.
     * Algorithm is greedy, so probably inaccurate.
     * @param course
     * @param steps
     * @return initial velocity for (hopefully) a hole in one
     */
    @Override
    public Vector2D calculate_turn(PuttingCourse course, int steps){
        double grav_constant = 9.81;
        Ball ball = course.getBall();
        Vector2D begin = ball.getLocation();//startlocation of the ball
        Vector2D end = course.getFlag();//location of the flag
        Vector2D direction = end.add(begin.multiply(-1));
        double step_size = direction.length()/steps;
        double factor = step_size/direction.length();
        Vector2D scaled_direction = direction.multiply(factor);
        Vector2D Ffric = direction.multiply(-ball.getMass() * grav_constant * course.getFrictionCoefficient());

        Ffric = Ffric.multiply(1/(direction.length()*steps));

        Vector2D[] gradients = getGradients(course, begin, end, steps);
        //double[] heights = getHeights(course, begin, end, steps);
        Vector2D returnvec = new Vector2D(0, 0);
        for (int i = 0; i < gradients.length - 1; i++){
            //double h = heights[i];
            //double h2 = heights[i+1];
            //double difference = h2 - h;
            Vector2D gradient = gradients[i];//new Vector2D(difference/scaled_direction.getX(), difference/scaled_direction.getY());
            Vector2D Fgrav = gradient.multiply(-ball.getMass() * grav_constant);

            Vector2D Fresist = Ffric.add(Fgrav.multiply(1/(double)steps));

            //the velocity calculated for one step. Eventually gets added to returnvec
            Vector2D sub_v0 = new Vector2D(
                    -Math.signum(Fresist.getX())*Math.sqrt(Math.abs(-2*scaled_direction.getX()*Fresist.getX()/course.getBall().getMass())),
                    -Math.signum(Fresist.getY())*Math.sqrt(Math.abs(-2*scaled_direction.getY()*Fresist.getY()/course.getBall().getMass()))
            );

            //check if sub_v0 is in the same direction as direction. if it's opposite, make it 0
            if (sub_v0.getX() * direction.getX() < 0){
                sub_v0.setX(0);
            }
            if (sub_v0.getY() * direction.getY() < 0){
                sub_v0.setY(0);
            }

            returnvec = returnvec.add(sub_v0);
        }
        return returnvec;
    }

    /**
     * Calculates the approximate initial velocity to calculate a hole-in-one.
     * Algorithm is greedy, so probably inaccurate.
     * @param course
     * @return approximate initial velocity vector
     */
    public Vector2D test(PuttingCourse course, int steps) {
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

    /**
     * method to get the gradients to provide in calculate_turn method in ai
     * @return an array of gradients between begin vector and end vector
     */
    private Vector2D[] getGradients(Function2D course, Vector2D begin, Vector2D end, int steps) {
        Vector2D[] gradients = new Vector2D[steps + 1];
        Vector2D difference = end.add(begin.multiply(-1));
        Vector2D step = difference.multiply(1/(double)steps);

        Vector2D current_step = begin.clone();
        for (int i = 0; i < gradients.length; i++){
            gradients[i] = course.gradient(current_step);
            current_step = current_step.add(step);
        }
        return gradients;
    }
}
