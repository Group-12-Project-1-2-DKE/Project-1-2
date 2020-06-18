package AI;

import Course.PuttingCourse;
import Physics.Vector2D;
import AI.StraighGreedy;

public class ObstacleAI implements AI{

    /**
     * Calculates the approximate initial velocity for a hole-in-one when taking trees into account
     * Algorithm should be fairly accurate, but there are some instances (A too dense forest) where
     * it will not make the hole-in-one.
     * @param course
     * @param steps
     * @return initial vector for a (hopefully) hole-in-one
     */

    StraighGreedy sG = new StraighGreedy();
    @Override
    public Vector2D calculate_turn(PuttingCourse course, int steps) {



        if(checkTree(course, steps)){


        }
        return null;
    }

    public boolean checkTree(PuttingCourse course, int steps){

        Vector2D holeInOne = sG.calculate_turn(course, steps);

        return false;
    }
}
