package AI;

import Course.PuttingCourse;
import Physics.Function2D;
import Physics.Vector2D;

public interface AI {
    /**
     * Calculates the initial velocity to make a hole-in-one.
     * @param course
     * @return initial velocity vector
     */
    Vector2D calculate_turn(PuttingCourse course, int steps);
}
