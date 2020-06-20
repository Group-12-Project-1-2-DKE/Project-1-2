package AI;

import Course.PuttingCourse;
import Objects.Ball;
import Physics.EulerSolver;
import Physics.PhysicsEngine;
import Physics.PuttingSimulator;
import Physics.Vector2D;
import AI.StraighGreedy;

public class ObstacleAI implements AI{

    public static void main(String[] args) {
        PuttingCourse h = new PuttingCourse("1", new Vector2D(0,0), new Vector2D(10,0),
                new Ball(new Vector2D(0,0), 3, (float)0.5), 0.05, 4, 4);
        ObstacleAI o = new ObstacleAI();
        Vector2D shot = o.calculate_turn(h, 500);
        System.out.println(shot);
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
    private int maxShots = 20;// maxshots need to be EVEN
    private double[] distanceArr = new double[maxShots + 1];
    private Vector2D[] velocityArr =  new Vector2D[maxShots + 1];

    @Override
    public Vector2D calculate_turn(PuttingCourse course, int steps) {
        shotcount = 0;
        initLoc = course.getBall().getLocation().clone();
        makePuttingSim(course);
        Vector2D tempHoleInOne = sG.calculate_turn(course, steps);
        p.take_shot(tempHoleInOne);
        Vector2D distance = course.getBall().getLocation().add(course.getFlag().multiply(-1));
        course.getBall().setLocation(initLoc.clone());
        if (distance.length() <= course.getTolerance()){
            return tempHoleInOne;
        }
        distanceArr[maxShots/2+1] = distance.length();
        velocityArr[maxShots/2+1] = tempHoleInOne.clone();

        int closestIndex = maxShots/2+1;
        double angle = 45/(.5*maxShots);
        tempHoleInOne = tempHoleInOne.turn(45);

        //miss kunnen we links en rechts gewoon berekenen en dan bij de beste inzoomen - nu nog niet
        //Hoe gaan we te hard/te zacht handelen - nu nog niet.
        //eerst 45 graden links en rechts.
        while (distance.length() > course.getTolerance() && shotcount < maxShots){
            if (shotcount == maxShots/2+1){
                tempHoleInOne = tempHoleInOne.turn(-angle);
                shotcount++;
                continue;
            }

            p.take_shot(tempHoleInOne);
            distance = course.getBall().getLocation().add(course.getFlag().multiply(-1));
            course.getBall().setLocation(initLoc.clone());
            if (distance.length() <= course.getTolerance()){
                return tempHoleInOne;
            }
            if (distance.length() < distanceArr[closestIndex]){
                closestIndex = shotcount;
            }
            distanceArr[shotcount] = distance.length();
            velocityArr[shotcount] = tempHoleInOne.clone();

            tempHoleInOne = tempHoleInOne.turn(-angle);
            shotcount++;
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
