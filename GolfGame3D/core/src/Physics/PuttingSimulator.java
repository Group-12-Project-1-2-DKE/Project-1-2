package Physics;

import com.mygdx.game.GolfGame;

import Course.PuttingCourse;
import Objects.*;

public class PuttingSimulator{
    PuttingCourse course;
    PhysicsEngine engine;
    int shot_counter = 0;
    private GolfGame game;

    //Main method for testing
   public static void main(String[] args) {
        Ball ball = new Ball(new Vector2D(4, 0), 1, 1);
        PuttingCourse course = new PuttingCourse("0.02*x^2 + 0.02*y^2", new Vector2D(4, 0), new Vector2D(0, 0),
                ball, 0.1, 5, 4);
        RungeKuttaSolver r = new RungeKuttaSolver();
        EulerSolver e = new EulerSolver();
        System.out.println(r.gravitational_force(ball, course));
        System.out.println(e.gravitational_force(ball, course));
        //Nu shit met friction force testen.

        e.set_step_size(0.01);
        e.set_fric_coefficient(course.getFrictionCoefficient());
        PuttingSimulator p = new PuttingSimulator(course, e);
        /*try{
            long start = System.currentTimeMillis();
            p.take_shot(new Vector2D(2, 0));
            System.out.println(System.currentTimeMillis() - start);
        } catch (StackOverflowError s){
            System.out.println(s);
        }
         */
    }

    public PuttingSimulator(PuttingCourse course, PhysicsEngine engine){
        this.course = course;
        this.engine = engine;
        game = new GolfGame();
    }

    public void setBallPosition(Vector2D vector){
        course.getBall().setLocation(vector);
    }

    public Vector2D get_ball_position(){
        return course.getBall().getLocation();
    }

    public void take_shot(Vector2D initial_ball_velocity){
        shot_counter++;
        Vector2D next_velocity = initial_ball_velocity;
        course.getBall().hit();
        while (course.getBall().isHit()){
            next_velocity = engine.calculateShot(next_velocity, course.getBall(), course);
           //GolfGame.ball.transform.setTranslation((float)next_velocity.getX(),(float)course.evaluate(new Vector2D(next_velocity.getX(),next_velocity.getY())),
                   //(float)next_velocity.getY() + 2.5f);
            updateBall((float)next_velocity.getX(),(float)next_velocity.getY());

        }
        System.out.println("Final ball location: " + course.getBall().getLocation());
    }

    public void updateBall(float x, float y){
        float z = (float) course.evaluate(new Vector2D(x,y));
        GolfGame.ball.transform.setTranslation(x,(float)-(z / course.getBall().getDiameter() /2), y + 1);
    }
}
