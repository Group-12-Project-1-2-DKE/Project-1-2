package AI;

import Course.PuttingCourse;
import Objects.Ball;
import Physics.*;

public class StraighGreedy implements AI{
    public static void main(String[] args) {
        PuttingCourse h = new PuttingCourse("1", new Vector2D(0,0), new Vector2D(7,9),
                new Ball(new Vector2D(0,0), 1, (float)0.5), 0.1, 4, 4);
        AI s = new StraighGreedy();
        double[] heights = {1, 1, 1, 1, 1, 1, 1, 1, 1};
        double step_size = Math.sqrt(2);
        heights = new double[]{1, 1, 1};
        step_size = Math.sqrt(140)/2;
        int steps = heights.length - 1;
        Vector2D shot = s.calculate_turn(h, heights, step_size);
        System.out.println("shot: " + shot);

        RungeKuttaSolver r = new RungeKuttaSolver();
        EulerSolver e = new EulerSolver();
        r.set_fric_coefficient(h.getFrictionCoefficient());
        r.set_grav_constant(9.81);
        e.set_fric_coefficient(h.getFrictionCoefficient());
        e.set_grav_constant(9.81);

        //e.set_step_size(0.01);
        //e.set_fric_coefficient(0.1);

        PuttingSimulator p = new PuttingSimulator(h, e);
        //shot = new Vector2D(3, 2);
        p.take_shot(shot);

        System.out.println("total Ffric: " + e.stuff);
        System.out.println("average Ffric: " + e.stuff.getX()/e.count + " " + e.stuff.getY()/e.count + " " + e.stuff.length()/e.count);
    }

    /**
     * Calculates the approximate initial velocity to calculate a hole-in-one.
     * Algorithm is greedy, so probably inaccurate.
     * @param course
     * @return approximate initial velocity vector
     */
    @Override
    public Vector2D calculate_turn(PuttingCourse course, double[] heights, double step_size) {
        int steps = heights.length - 1;
        double grav_constant = 9.81;
        Ball ball = course.getBall();
        Vector2D begin = ball.getLocation();
        Vector2D end = course.getFlag();
        Vector2D direction = end.add(begin.multiply(-1));
        double factor = step_size/direction.length();
        Vector2D scaled_direction = direction.multiply(factor);
        double Ffric_length = -ball.getMass() * grav_constant * course.getFrictionCoefficient();
        Vector2D Ffric = scaled_direction.multiply(Ffric_length/scaled_direction.length());
        System.out.println("Ffric: " + Ffric + " " + Ffric.length());//TODO: Make better method to come up with Ffric, cuz it too big
        //TODO: We also need to figure out if we should be adding the velocities or so something else with them

        Vector2D returnvec = new Vector2D(direction.getX(), direction.getY());
        returnvec = new Vector2D(0, 0);

        for (int i = 0; i < heights.length - 1; i++){
            double h = heights[i];
            double h2 = heights[i+1];
            double difference = h2 - h;
            Vector2D gradient = new Vector2D(difference/scaled_direction.getX(), difference/scaled_direction.getY());
            if (scaled_direction.getY() == 0){
                gradient.setY(0);
            }
            if (scaled_direction.getX() == 0){
                gradient.setX(0);
            }
            Vector2D Fgrav = gradient.multiply(-ball.getMass() * grav_constant);
            Vector2D Fresist = Ffric.add(Fgrav);
            System.out.println("Fresist: " + Fresist);

            Vector2D sub_initial_v = new Vector2D(Math.sqrt((-2*scaled_direction.getX()*Fresist.getX())/ball.getMass()),
                    Math.sqrt((-2*scaled_direction.getY()*Fresist.getY())/ball.getMass()));//step_size should maybe be scaled_direction.getX/Y()
            //sub_initial_v = sub_initial_v.multiply(1/(double)steps);
            //sub_initial_v = sub_initial_v.multiply(1/(i+1.));

            System.out.println("subV0: " + sub_initial_v);

            returnvec = returnvec.add(sub_initial_v);

            /*if (Fresist.getX() * scaled_direction.getX() < 0){
                returnvec.setX(returnvec.getX() - Fresist.getX());
            }
            if (Fresist.getY() * scaled_direction.getY() < 0){
                returnvec.setY(returnvec.getY() - Fresist.getY());
            }
             */
        }
        return returnvec;
    }
}
