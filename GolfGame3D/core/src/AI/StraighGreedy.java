package AI;

import Course.PuttingCourse;
import Objects.Ball;
import Physics.*;
import com.mygdx.game.Variables;

import java.util.Arrays;

public class StraighGreedy implements AI{
    /**
     * Calculates the approximate initial velocity to calculate a hole-in-one.
     * Algorithm is greedy, so probably inaccurate.
     * @param course
     * @param steps
     * @return initial Vector for (hopefully) a hole in one
     */
    @Override
    public Vector2D calculate_turn(PuttingCourse course, int steps){
        double grav_constant = Variables.gravity;
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
        Vector2D returnvec = new Vector2D(0, 0);
        for (int i = 0; i < gradients.length - 1; i++){
            Vector2D gradient = gradients[i];
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

    @Override
    public void setTreePositionX(float[] treePositionX) {
        //can stay empty
    }

    @Override
    public void setTreePositionZ(float[] treePositionZ) {
        //can stay empty
    }

    public void setTerrainInfo(int[][] t){
        //can stay empty
    }
}
