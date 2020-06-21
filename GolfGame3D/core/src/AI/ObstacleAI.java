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
        PuttingCourse h = new PuttingCourse("2 + sin(x) - 0.5cos(y)", new Vector2D(0,0), new Vector2D(10,10),
                new Ball(new Vector2D(0,0), 3, (float)0.5), 0.05, 4, 4);
        PhysicsEngine e = new EulerSolver();
        e.set_fric_coefficient(0.05);
        PuttingSimulator p = new PuttingSimulator(h, e);


        AI o = new ObstacleAI();
        //o = new StraighGreedy();
        long startTime = System.currentTimeMillis();
        Vector2D shot = o.calculate_turn(h, 500);
        System.out.println(System.currentTimeMillis() - startTime);

        System.out.println(shot);
        p.take_shot(shot);
        System.out.println(h.getBall().getLocation().add(h.getFlag().multiply(-1)).length());
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
    private int maxShots = 50;// maxshots need to be EVEN
    private double[] distanceArr = new double[maxShots + 1];
    private Vector2D[] velocityArr =  new Vector2D[maxShots + 1];

    @Override
    public Vector2D calculate_turn(PuttingCourse course, int steps) {
        shotcount = 0;
        initLoc = course.getBall().getLocation().clone();
        makePuttingSim(course);

        Vector2D tempHoleInOne = sG.calculate_turn(course, steps);
        //System.out.println(tempHoleInOne);


        double angle = 45;

        for (int i = 0; i < 2; i++) {
            tempHoleInOne = bestAngle(tempHoleInOne, angle, course);
            angle /= 10;
            System.out.println("------------------------------------");
        }

        double initFactor = tempHoleInOne.length()/4;


        return tempHoleInOne;

        /*if(checkTree(course, steps)){


        }*/
    }

    private Vector2D bestPower(Vector2D initVector, double initFactor, PuttingCourse course){
        Vector2D faster = initVector.multiply(initFactor);
        Vector2D slower = initVector.multiply(1/initFactor);

        p.take_shot(faster);
        Vector2D Fdistance = course.getBall().getLocation().add(course.getFlag().multiply(-1));
        course.getBall().setLocation(initLoc.clone());

        p.take_shot(slower);
        Vector2D Sdistance = course.getBall().getLocation().add(course.getFlag().multiply(-1));
        course.getBall().setLocation(initLoc.clone());

        return null;
    }

    private Vector2D bestAngle(Vector2D initVector, double maxDegree, PuttingCourse course){
        shotcount = 0;

        p.take_shot(initVector);
        Vector2D distance = course.getBall().getLocation().add(course.getFlag().multiply(-1));
        course.getBall().setLocation(initLoc.clone());

        if (distance.length() <= course.getTolerance()){
            return initVector;
        }

        distanceArr[maxShots/2] = distance.length();
        velocityArr[maxShots/2] = initVector.clone();

        int closestIndex = maxShots/2;
        double angle = maxDegree/(.5*maxShots);
        Vector2D tempHoleInOne = initVector.turn(maxDegree);
        double angleCount = maxDegree;

        //miss kunnen we links en rechts gewoon berekenen en dan bij de beste inzoomen - nu nog niet
        //Hoe gaan we te hard/te zacht handelen - nu nog niet.
        //eerst 45 graden links en rechts.
        while (distance.length() > course.getTolerance() && shotcount <= maxShots){
            if (shotcount == maxShots/2){
                tempHoleInOne = tempHoleInOne.turn(-angle);
                System.out.println("a: " + angleCount);
                System.out.println(distanceArr[shotcount]);
                System.out.println(velocityArr[shotcount]);
                angleCount -= angle;
                shotcount++;
                continue;
            }


            p.take_shot(tempHoleInOne);
            distance = course.getBall().getLocation().add(course.getFlag().multiply(-1));
            System.out.println("a: " + angleCount);
            System.out.println(distance.length());
            System.out.println(tempHoleInOne);

            course.getBall().setLocation(initLoc.clone());
            if (distance.length() <= course.getTolerance()){
                return tempHoleInOne;
            }
            if (distance.length() < distanceArr[closestIndex]){
                closestIndex = shotcount;
            }
            distanceArr[shotcount] = distance.length();
            velocityArr[shotcount] = tempHoleInOne.clone();

            angleCount -= angle;
            tempHoleInOne = tempHoleInOne.turn(-angle);
            shotcount++;
        }

        return velocityArr[closestIndex];
    }

    private void makePuttingSim(PuttingCourse course){
        if (pAssigned){
            return;
        }
        PhysicsEngine e = new EulerSolver();
        e.set_fric_coefficient(course.getFrictionCoefficient());
        p = new PuttingSimulator(course, e);
        pAssigned = true;
    }

    /*public boolean checkTree(PuttingCourse course, int steps){

        Vector2D holeInOne = sG.calculate_turn(course, steps);

        return false;
    }*/
}
