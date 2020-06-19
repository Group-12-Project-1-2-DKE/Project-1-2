package AI;

import Course.PuttingCourse;
import Physics.EulerSolver;
import Physics.PhysicsEngine;
import Physics.PuttingSimulator;
import Physics.Vector2D;
import AI.StraighGreedy;

public class ObstacleAI implements AI{

    public static void main(String[] args) {
        Vector2D test = new Vector2D(1, 0);
        System.out.println(test.turn(90));
    }

    /**
     * Calculates the approximate initial velocity for a hole-in-one when taking trees into account
     * Algorithm should be fairly accurate, but there are some instances (A too dense forest) where
     * it will not make the hole-in-one.
     * @param course
     * @param steps
     * @return initial vector for a (hopefully) hole-in-one
     */

    private StraighGreedy sG = new StraighGreedy();
    private PuttingSimulator p;
    private boolean pAssigned = false;
    private Vector2D initLoc;
    private int shotcount = 0;
    private int maxShots = 20;
    private double[] distanceArr = new double[shotcount];
    private Vector2D[] velocityArr =  new Vector2D[shotcount];

    @Override
    public Vector2D calculate_turn(PuttingCourse course, int steps) {
        shotcount = 0;
        initLoc = course.getBall().getLocation().clone();
        makePuttingSim(course);
        Vector2D tempHoleInOne = sG.calculate_turn(course, steps);
        p.take_shot(tempHoleInOne);
        Vector2D distance = course.getBall().getLocation().add(course.getFlag().multiply(-1));
        course.getBall().setLocation(initLoc);
        if (distance.length() <= course.getTolerance()){
            return tempHoleInOne;
        }
        shotcount++;
        distanceArr[0] = distance.length();
        velocityArr[0] = tempHoleInOne.clone();

        int closestIndex = 0;

        //Hoe gaan we te hard/te zacht handelen
        //eerst 45 graden links en rechts.
        while (distance.length() > course.getTolerance() && shotcount < shotcount){



        }

        /*if(checkTree(course, steps)){


        }*/
        return null;
    }

    private void makePuttingSim(PuttingCourse course){
        if (pAssigned){
            return;
        }
        PhysicsEngine e = new EulerSolver();
        p = new PuttingSimulator(course, e);
        pAssigned = true;
    }

    /*public boolean checkTree(PuttingCourse course, int steps){

        Vector2D holeInOne = sG.calculate_turn(course, steps);

        return false;
    }*/
}
