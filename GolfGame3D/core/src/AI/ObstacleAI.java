package AI;

import Course.PuttingCourse;
import Objects.Ball;
import Objects.TreeObstacle;
import Physics.EulerSolver;
import Physics.PhysicsEngine;
import Physics.PuttingSimulator;
import Physics.Vector2D;
import AI.StraighGreedy;
import com.mygdx.game.Variables;

public class ObstacleAI implements AI{


    public static void main(String[] args) {
        Vector2D begin = new Vector2D(-10, -10);
        PuttingCourse h = new PuttingCourse("1"/*"2 + sin(x) - 0.5cos(y)"*/, begin.clone(), new Vector2D(10,10),
                new Ball(begin.clone(), 3), 0.05, 4, 0.1);
        PhysicsEngine e = new EulerSolver();
        e.set_fric_coefficient(h.getFrictionCoefficient());
        PuttingSimulator p = new PuttingSimulator(h, e);
        //Miss nog dat als hij tenminste langs het ding komt dat het als hole in one telt. Met maxVelocity?

        AI s = new StraighGreedy();
        AI o = new ObstacleAI();
        o.setTreePositionX(new float[]{0, 1, 1.2f, -0.5f, 3, 6.7f, 2.5f, -2.5f, -1.4f, 0});
        o.setTreePositionZ(new float[]{-5.5f, 0, -2.6f, -1.2f, -2, 0.6f, 2.5f, 3.6f, 4.8f, 0});

        //o = new StraighGreedy();
        long startTime = System.currentTimeMillis();
        Vector2D shot = o.calculate_turn(h, 500);
        System.out.println(System.currentTimeMillis() - startTime);

        System.out.println(shot);
        ((ObstacleAI)o).takeShot(shot, h);
        System.out.println(h.getBall().getLocation());
        System.out.println(h.getBall().getLocation().add(h.getFlag().multiply(-1)).length());

        h.getBall().setLocation(begin.clone());

        startTime = System.currentTimeMillis();
        shot = s.calculate_turn(h, 500);
        System.out.println(System.currentTimeMillis() - startTime);

        System.out.println(shot);
        ((ObstacleAI)o).takeShot(shot, h);
        System.out.println(h.getBall().getLocation());
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
    private int maxShots = 100;// maxshots need to be EVEN
    private double[] distanceArr = new double[maxShots + 1];
    private Vector2D[] velocityArr =  new Vector2D[maxShots + 1];
    private float[] treePositionX;
    private float[] treePositionZ;
    private final TreeObstacle obstacle = new TreeObstacle();
    private int[][] terrainInfo;

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
            //System.out.println(tempHoleInOne);
            angle /= 10;
//            System.out.println("------------------------------------");
        }

        double initFactor = 1;//tempHoleInOne.length()/4;//ff optimale factor berekenen

        tempHoleInOne = bestPower(tempHoleInOne, initFactor, course);

        System.out.println(tempHoleInOne);
        return tempHoleInOne;

        /*if(checkTree(course, steps)){


        }*/
    }

    private Vector2D bestPower(Vector2D initVector, double initFactor, PuttingCourse course){
        Vector2D firstVector = initVector;
        Vector2D faster;
        Vector2D slower;
        takeShot(initVector, course);
        Vector2D initDistance = course.getBall().getLocation().add(course.getFlag().multiply(-1));
        course.getBall().setLocation(initLoc.clone());

        for (int i = 0; i < 10; i++) {
            if (initDistance.length() <= course.getTolerance()){
                return initVector;
            }//if other 2 ifs not removed, move this out of for loop

            faster = initVector.add(firstVector.multiply(initFactor));
            slower = initVector.add(firstVector.multiply(-initFactor));

            takeShot(faster, course);
            Vector2D Fdistance = course.getBall().getLocation().add(course.getFlag().multiply(-1));
            course.getBall().setLocation(initLoc.clone());
            if (Fdistance.length() <= course.getTolerance()){
                return faster;
            }//maybe not necessary?

            takeShot(slower, course);
            Vector2D Sdistance = course.getBall().getLocation().add(course.getFlag().multiply(-1));
            course.getBall().setLocation(initLoc.clone());
            if (Sdistance.length() <= course.getTolerance()){
                return slower;
            }//maybe not necessary?

            /*System.out.println(initFactor);
            System.out.println("s: " + slower + ", " + Sdistance.length());
            System.out.println("i: " + initVector + ", " + initDistance.length());
            System.out.println("f: " + faster + ", " + Fdistance.length());*/

            double closest = Math.min(initDistance.length(), Math.min(Fdistance.length(), Sdistance.length()));

            if (closest == Sdistance.length()) {
                //System.out.println("slower: " + Sdistance.length());
                initVector = slower;
                initDistance = Sdistance;
            } else if (closest == Fdistance.length()) {
                //System.out.println("faster: " + Fdistance.length());
                initVector = faster;
                initDistance = Fdistance;
            } else {
                //System.out.println("same: " + initDistance.length());
                initFactor /= 2;
            }
        }
        return initVector;
    }

    private Vector2D bestAngle(Vector2D initVector, double maxDegree, PuttingCourse course){
        shotcount = 0;

        takeShot(initVector, course);
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
//                System.out.println("a: " + angleCount);
//                System.out.println(distanceArr[shotcount]);
//                System.out.println(velocityArr[shotcount]);
                angleCount -= angle;
                shotcount++;
                continue;
            }


            takeShot(tempHoleInOne, course);
            distance = course.getBall().getLocation().add(course.getFlag().multiply(-1));
//            System.out.println("a: " + angleCount);
//            System.out.println(distance.length());
//            System.out.println(tempHoleInOne);

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
        e.setTerrainInfo(terrainInfo);
        p = new PuttingSimulator(course, e);
        pAssigned = true;
    }

    public void setTreePositionX(float[] treePositionX) {
        this.treePositionX = treePositionX;
    }

    public void setTreePositionZ(float[] treePositionZ) {
        this.treePositionZ = treePositionZ;
    }

    public void setTerrainInfo(int[][] t){
        this.terrainInfo = t;
    }

    public void takeShot(Vector2D initialVelocity, PuttingCourse course){
        course.getBall().hit();
        Vector2D next = initialVelocity.clone();
        while (next != null){
            for (int i = 0; i < treePositionX.length; i++){
                if (collides(i, course)){
                    p.getEngine().tree_collision(course.getBall(), obstacle, next);
                }
            }
            next = p.take_shotSlowly(next);
        }
    }

    public boolean collides(int i, PuttingCourse course) {
        if (euclideanDistance((float) course.getBall().getLocation().getX(),
                (float) course.getBall().getLocation().getY(), i) < 0.5) {
            obstacle.setLocation(new Vector2D(treePositionX[i], treePositionZ[i]));
            return true;
        }
        return false;
    }

    public float euclideanDistance(float posX, float posZ, int i) {
        float treeX = treePositionX[i];
        float treeZ = treePositionZ[i];
        return (float) Math.sqrt(Math.pow((posX - treeX), 2) + Math.pow((posZ - treeZ), 2));
    }

    /*public boolean checkTree(PuttingCourse course, int steps){

        Vector2D holeInOne = sG.calculate_turn(course, steps);

        return false;
    }*/
}
