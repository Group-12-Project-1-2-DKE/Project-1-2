package Physics;

import com.mygdx.game.GolfGame;

import Course.PuttingCourse;
import Objects.*;

import static com.mygdx.game.GolfGame.ball;

public class PuttingSimulator{
    PuttingCourse course;
    PhysicsEngine engine;
    int shot_counter = 0;
    private GolfGame game;

    //Main method for testing
   public static void main(String[] args) {
        Ball ball = new Ball(new Vector2D(4, 0), 1, 1);
        PuttingCourse course = new PuttingCourse("2",//"0.02*x^2 + 0.02*y^2",
                new Vector2D(4, 0), new Vector2D(0, 0),
                ball, 0.1, 5, 4);
        RungeKuttaSolver r = new RungeKuttaSolver();
        EulerSolver e = new EulerSolver();
        System.out.println(r.gravitational_force(ball, course));
        Vector2D gForce = e.gravitational_force(ball, course);
        //Nu shit met friction force testen.
        Vector2D test = new Vector2D(1, 1);
        System.out.println(r.friction_force(ball, test));
        Vector2D fForce = e.friction_force(ball, test);
        System.out.println(fForce);
        Vector2D Fres = gForce.add(fForce);

        System.out.println(r.calculate_acc(course, test, ball));
        System.out.println(e.calculate_acc(Fres, ball));

        System.out.println(r.calculateShot(test, ball, course));
        System.out.println(e.calculateShot(test, ball, course));

        //e.set_step_size(0.0001);
        e.set_fric_coefficient(course.getFrictionCoefficient());
        PuttingSimulator p = new PuttingSimulator(course, e);
        long start = System.currentTimeMillis();
        p.take_shot(new Vector2D(2, 0));
        System.out.println(System.currentTimeMillis() - start);

        //r.set_step_size(0.0001);
        start = System.currentTimeMillis();
        r.set_fric_coefficient(course.getFrictionCoefficient());
        p = new PuttingSimulator(course, r);
        p.take_shot(new Vector2D(2, 0));
        System.out.println(System.currentTimeMillis() - start);
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
        int cnt = 0;

        shot_counter++;
        Vector2D next_velocity = initial_ball_velocity;
        course.getBall().hit();
        while (course.getBall().isHit() && cnt <= 50){
            cnt++;
            if (cnt <= 10){
                System.out.println(cnt);
                System.out.println(course.getBall().getLocation());
                System.out.println(next_velocity);
            }

            next_velocity = engine.calculateShot(next_velocity, course.getBall(), course);
           //GolfGame.ball.transform.setTranslation((float)next_velocity.getX(),(float)course.evaluate(new Vector2D(next_velocity.getX(),next_velocity.getY())),
                   //(float)next_velocity.getY() + 2.5f);
            //updateBall((float)next_velocity.getX(),(float)next_velocity.getY());

        }
        System.out.println("Final ball location: " + course.getBall().getLocation());
    }

    public void updateBall(float x, float y){
        float z = (float) course.evaluate(new Vector2D(x,y));
        ball.transform.setTranslation(x,(float)-(z / course.getBall().getDiameter() /2), y + 1);
    }
}
